package de.fhg.iais.roberta.transformers.arduino;

import static de.fhg.iais.roberta.transformer.Jaxb2ConfigurationAstHelper.blocks2NewConfiguration;

import java.util.ArrayList;
import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.BlockSet;
import de.fhg.iais.roberta.blockly.generated.Instance;
import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.factory.BlocklyDropdownFactory;

/**
 * JAXB to brick configuration. Client should provide a tree of jaxb objects. Generates a BrickConfiguration object.
 */
public class Jaxb2ArduinoConfigurationTransformer {
    BlocklyDropdownFactory factory;

    public Jaxb2ArduinoConfigurationTransformer(BlocklyDropdownFactory factory) {
        this.factory = factory;
    }

    public ConfigurationAst transform(BlockSet blockSet) {
        List<Instance> instances = blockSet.getInstance();
        List<List<Block>> blocks = new ArrayList<>();
        for ( int i = 0; i < instances.size(); i++ ) {
            blocks.add(instances.get(i).getBlock());
        }
        return blocks2NewConfiguration(blocks, this.factory);
    }

}
