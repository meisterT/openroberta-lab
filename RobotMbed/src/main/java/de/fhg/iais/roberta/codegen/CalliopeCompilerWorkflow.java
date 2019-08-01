package de.fhg.iais.roberta.codegen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhg.iais.roberta.blockly.generated.BlockSet;
import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.factory.IRobotFactory;
import de.fhg.iais.roberta.inter.mode.action.ILanguage;
import de.fhg.iais.roberta.transformer.Project;
import de.fhg.iais.roberta.transformer.mbed.Jaxb2MbedConfigurationAst;
import de.fhg.iais.roberta.util.Key;
import de.fhg.iais.roberta.util.PluginProperties;
import de.fhg.iais.roberta.util.jaxb.JaxbHelper;
import de.fhg.iais.roberta.visitor.codegen.CalliopeCppVisitor;
import de.fhg.iais.roberta.visitor.collect.MbedUsedHardwareCollectorVisitor;
import de.fhg.iais.roberta.visitor.validate.AbstractProgramValidatorVisitor;
import de.fhg.iais.roberta.visitor.validate.MbedBoardValidatorVisitor;

public class CalliopeCompilerWorkflow extends AbstractCompilerWorkflow {

    private static final Logger LOG = LoggerFactory.getLogger(CalliopeCompilerWorkflow.class);

    private String compiledHex = "";

    public CalliopeCompilerWorkflow(PluginProperties pluginProperties) {
        super(pluginProperties);
    }

    @Override
    public void generateSourceCode(String token, String programName, Project data, ILanguage language) {
        if ( !data.getErrorMessages().isEmpty() ) {
            this.workflowResult = Key.COMPILERWORKFLOW_ERROR_PROGRAM_TRANSFORM_FAILED;
            return;
        }
        final ConfigurationAst configuration = data.getConfigurationAst();
        final AbstractProgramValidatorVisitor programValidator = new MbedBoardValidatorVisitor(configuration);
        programValidator.check(data.getProgramAst().getTree());
        if ( programValidator.getErrorCount() > 0 ) {
            this.workflowResult = Key.COMPILERWORKFLOW_ERROR_PROGRAM_TRANSFORM_FAILED;
        }
        try {
            this.generatedSourceCode = CalliopeCppVisitor.generate(data.getConfigurationAst(), data.getProgramAst().getTree(), true);
            LOG.info("Calliope c++ code generated");
        } catch ( Exception e ) {
            LOG.error("Calliope c++ code generation failed", e);
            this.workflowResult = Key.COMPILERWORKFLOW_ERROR_PROGRAM_GENERATION_FAILED;
        }
    }

    @Override
    public void compileSourceCode(String token, String programName, ILanguage language, Object flagProvider) {
        storeGeneratedProgram(token, programName, ".cpp");
        boolean isRadioUsed;
        if ( flagProvider == null ) {
            isRadioUsed = false;
        } else if ( flagProvider instanceof EnumSet<?> ) {
            EnumSet<?> flags = (EnumSet<?>) flagProvider;
            isRadioUsed = flags.contains(CalliopeCompilerFlag.RADIO_USED);
        } else {
            isRadioUsed = false;
        }
        this.workflowResult = runBuild(token, programName, "generated.main", isRadioUsed);
        if ( this.workflowResult == Key.COMPILERWORKFLOW_SUCCESS ) {
            LOG.info("compile calliope program {} successful", programName);
        } else {
            LOG.error("compile calliope program {} failed with {}", programName, this.workflowResult);
        }
    }

    @Override
    public void generateSourceAndCompile(String token, String programName, Project transformer, ILanguage language) {
        MbedUsedHardwareCollectorVisitor usedHardwareVisitor =
            new MbedUsedHardwareCollectorVisitor(transformer.getProgramAst().getTree(), transformer.getConfigurationAst());
        EnumSet<CalliopeCompilerFlag> compilerFlags =
            usedHardwareVisitor.isRadioUsed() ? EnumSet.of(CalliopeCompilerFlag.RADIO_USED) : EnumSet.noneOf(CalliopeCompilerFlag.class);
        generateSourceCode(token, programName, transformer, language);
        if ( this.workflowResult == Key.COMPILERWORKFLOW_SUCCESS ) {
            compileSourceCode(token, programName, language, compilerFlags);
        }
    }

    @Override
    public ConfigurationAst generateConfiguration(IRobotFactory factory, String blocklyXml) throws Exception {
        BlockSet project = JaxbHelper.xml2BlockSet(blocklyXml);
        Jaxb2MbedConfigurationAst transformer = new Jaxb2MbedConfigurationAst(factory);
        return transformer.transform(project);
    }

    @Override
    public String getCompiledCode() {
        return this.compiledHex;
    }

    /**
     * create command to call the cross compiler and execute the call.
     *
     * @return Key.COMPILERWORKFLOW_SUCCESS or Key.COMPILERWORKFLOW_ERROR_PROGRAM_COMPILE_FAILED
     */
    private Key runBuild(String token, String mainFile, String mainPackage, boolean radioUsed) {
        final String compilerBinDir = this.pluginProperties.getCompilerBinDir();
        final String compilerResourcesDir = this.pluginProperties.getCompilerResourceDir();
        final String tempDir = this.pluginProperties.getTempDir();

        String scriptName = compilerResourcesDir + "../compile." + (SystemUtils.IS_OS_WINDOWS ? "bat" : "sh");
        String bluetooth = radioUsed ? "" : "-b";
        Path pathToSrcFile = Paths.get(tempDir + token + "/" + mainFile);

        String[] executableWithParameters =
            new String[] {
                scriptName,
                compilerBinDir,
                mainFile,
                Paths.get("").resolve(pathToSrcFile).toAbsolutePath().normalize().toString() + "/",
                compilerResourcesDir,
                bluetooth
            };
        boolean success = runCrossCompiler(executableWithParameters);
        if ( success ) {
            try {
                this.compiledHex = FileUtils.readFileToString(new File(pathToSrcFile + "/target/" + mainFile + ".hex"), "UTF-8");
                return Key.COMPILERWORKFLOW_SUCCESS;
            } catch ( IOException e ) {
                LOG.error("compilation of Calliope program successful, but reading the binary failed", e);
                return Key.COMPILERWORKFLOW_ERROR_PROGRAM_COMPILE_FAILED;
            }
        } else {
            return Key.COMPILERWORKFLOW_ERROR_PROGRAM_COMPILE_FAILED;
        }
    }
}
