@echo off
cls

if not "%OPENJDK11_HOME%" == "" (
 	set  JAVA=%OPENJDK11_HOME%
) else (
 	if not "%OPENJDK_HOME%" == "" (
 		set  JAVA=%OPENJDK_HOME%
   	) else (
   		set JAVA=%JAVA_HOME%
   	)
)

set PATH=%JAVA_HOME%\bin;%PATH%

rem Checks if the user want's to debug the program on the console
for %%z in (%*) do (
	if "%%z"=="-debug" set EXTRA_JVM_ARGUMENTS=-Dl4j.lvl=DEBUG
	if "%%z"=="-info" set EXTRA_JVM_ARGUMENTS=-Dl4j.lvl=INFO
	if "%%z"=="-trace" set EXTRA_JVM_ARGUMENTS=-Dl4j.lvl=TRACE
	if "%%z"=="-error" set EXTRA_JVM_ARGUMENTS=-Dl4j.lvl=ERROR
	if "%%z"=="-warn" set EXTRA_JVM_ARGUMENTS=-Dl4j.lvl=WARN
)

set EXTRA_JVM_ARGUMENTS=%EXTRA_JVM_ARGUMENTS% -Dl4j.logDir=%1

call java %EXTRA_JVM_ARGUMENTS% -jar %~dp0.\VZD-Client\VZD-Client.jar %*