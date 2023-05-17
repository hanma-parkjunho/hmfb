@echo off

if not defined JAVA_HOME (
  echo Error: JAVA_HOME is not set.
  goto :eof
)

set JAVA_HOME=%JAVA_HOME:"=%

if not exist "%JAVA_HOME%"\bin\java.exe (
  echo Error: JAVA_HOME is incorrectly set: %JAVA_HOME%
  echo Expected to find java.exe here: %JAVA_HOME%\bin\java.exe
  goto :eof
)

if [%1] == [] (
	echo "you should pass 1 argument which set bootJar path."
	echo "Usage: server_start.bat /app/hmfb/hmfb-web.jar [enter]"
	goto :eof
)

set JAVA=%JAVA_HOME%\bin\java
set SPRING_PROFILE=local
set DB_TYPE=mysql
set INSTALL_PATH=C:\hmfb\git\hmfb

set BOOT_JAR_PATH=%1

%JAVA% -Dspring.profiles.active=%SPRING_PROFILE% -DdbType=%DB_TYPE% -DinstallPath=%INSTALL_PATH% -XX:+HeapDumpOnOutOfMemoryError -jar %BOOT_JAR_PATH%


