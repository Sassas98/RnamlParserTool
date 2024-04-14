@echo off
setlocal

set FILENAME=RnamlParserTool-1.0.0

if "%1"=="equals" (
    java -jar %FILENAME%.jar equals %2 %3
) else if "%1"=="-e" (
    java -jar %FILENAME%.jar equals %2 %3
) else (
    java -jar %FILENAME%.jar %1 %2
)

endlocal
