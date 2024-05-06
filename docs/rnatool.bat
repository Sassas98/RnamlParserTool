@echo off
setlocal

set FILENAME=C:\Users\15mim\OneDrive\Documenti\GitHub\RnaParserLibrary\target\RnamlParserTool-1.0.0

if "%1"=="equals" (
    java -jar %FILENAME%.jar equals %2 %3
) else if "%1"=="-e" (
    java -jar %FILENAME%.jar equals %2 %3
) else if "%1"=="all" (
    java -jar %FILENAME%.jar all %2 %3 %4
) else if "%1"=="-a" (
    java -jar %FILENAME%.jar all %2 %3 %4
) else (
    java -jar %FILENAME%.jar %1 %2
)

endlocal
