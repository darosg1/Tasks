#!/usr/bin/env bash

if ./runcrud.sh; then
   echo "Success in compilation, Tomcat started!"
   open -a Safari http://localhost:8080/crud/v1/task/getTasks
else
   echo "Compilation error!"
   fail
fi