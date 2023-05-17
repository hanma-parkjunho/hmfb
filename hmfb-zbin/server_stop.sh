ps -ef | grep $1 | grep -v tail | grep -v grep | awk ‘{print $2}’ | xargs kill -9
