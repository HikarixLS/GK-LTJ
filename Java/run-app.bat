@echo off
cd /d "%~dp0"

REM Set JAVA_HOME to directory containing javac
for /f "tokens=*" %%i in ('where javac 2^>NUL') do (
    set "JAVA_BIN=%%i"
    goto :found_javac
)

echo Looking for JDK in common locations...
for %%d in (
    "C:\Program Files\Java\jdk*"
    "C:\Program Files\Eclipse Adoptium\jdk*"
    "C:\Program Files\OpenJDK\jdk*"
    "C:\Program Files\Microsoft\jdk*"
    "C:\openjdk\jdk*"
) do (
    if exist "%%d\bin\javac.exe" (
        set "JAVA_HOME=%%d"
        goto :found_java_home
    )
)

echo JDK not found, trying to use Java 24 directly...
set "PATH=%PATH%;C:\Program Files\Common Files\Oracle\Java\javapath"

:found_javac
for %%i in ("%JAVA_BIN%") do set "JAVA_HOME=%%~dpi.."
goto :found_java_home

:found_java_home
echo Using JAVA_HOME: %JAVA_HOME%
mvnw.cmd spring-boot:run
pause
