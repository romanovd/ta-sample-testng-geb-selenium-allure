@echo off
del log\main.log 
del log\*.txt
call mvn clean
cls
call mvn test -DxmlFileName=testng 
call mvn site
start firefox file:///%~dp0/target/site/allure-maven-plugin/index.html
