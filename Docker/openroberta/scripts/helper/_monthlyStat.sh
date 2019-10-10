#!/bin/bash

isServerNameValid ${SERVER_NAME}
if [[ "$MONTH" == '' ]]
then
    MONTH=$(($(date +'%m')-1))
    if [[ $MONTH < 1 ]]
    then
        MONTH=12
    fi
fi
echo '******************** '$DATE' ********************'
echo "generating the monthly statistics for month $MONTH"
YEAR=$(date +'%Y')
STATISTICS_DIR=${SERVER_DIR}/${SERVER_NAME}/admin/logging/log-$YEAR
isDirectoryValid $STATISTICS_DIR
REPORT_DIR=${SERVER_DIR}/${SERVER_NAME}/admin/reports-$YEAR
mkdir -p $REPORT_DIR

FILE=''
set ${MONTH}*
for F do
    if [[ "$FILE" != '' ]]
    then
        echo "got more than one file for generating reports. Ignoring file: $FILE"
    fi
    FILE=$F
done
echo "statistics generated from file $FILE are written to directory $STATISTICS_DIR"

$PYTHON $SCRIPT_REPORTING/workflows-monthly.py $STATISTICS_DIR $REPORT_DIR textResults-$MONTH.txt
