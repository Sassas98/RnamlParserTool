@echo off
setlocal

set FILENAME=%RNATOOL_PATH%\SERNAlign
set RESULT_FONDER=result\

if not exist "%RESULT_FONDER%" (
    mkdir "%RESULT_FONDER%"
)

java -jar %FILENAME%.jar -a %1 %2 -o %RESULT_FONDER%%1.%2.distance.txt

endlocal