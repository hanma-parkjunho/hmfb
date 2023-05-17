@echo off

set /a count=0
for %%a in (%*) do set /a count+=1
echo Number of arguments: %count%

if [%count%] neq [2] (
	echo "you should pass 2 argument which set bootJar path and a port number."
	echo "Usage: server_stop.bat stop 8050 [enter]"
	goto :eof
)

set port=%2
for /f "tokens=5" %%a in ('netstat -aon ^| findstr /i :%port%') do set pid=%%a
if "%pid%"=="" (
    echo No process is currently using port %port%.
) else (
    echo Terminating process with PID %pid%...
    taskkill /f /pid %pid%
)
exit