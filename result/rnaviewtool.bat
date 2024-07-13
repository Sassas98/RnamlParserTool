@echo off
setlocal

if "%~1"=="" (
    echo Uso: %~nx0 [file] [estensione]
    exit /b
)

if "%~2"=="" (
    for %%F in (*.pdb) do (
        rnaview "%%~F"
        rnatool "%%~F.xml" "%%~nF.%~1"
    )
    del *.out
    del *.ps
    del *.pdb_new
    del *.pdb_tmp.pdb
) else (
    rnaview "%~1"
    rnatool "%~1.xml" "%~1.%~2"
    del *.out
    del *.ps
    del *.pdb_new
    del *.pdb_tmp.pdb
)

endlocal
