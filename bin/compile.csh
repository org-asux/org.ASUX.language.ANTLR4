#!/bin/tcsh -f

source ./common.csh-source

###============================================
###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
###============================================

if ( "$1" == "--verbose" || "$1" == "-v" ) then
	set VERBOSE=1
	shift
endif

###============================================
###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
###============================================

echo \
mvn clean antlr4:antlr4 compiler:compile compiler:testCompile test
mvn clean antlr4:antlr4 compiler:compile compiler:testCompile test

###----------------------------
if ( $?VERBOSE) ls -lad ${BUILDROOT}/surefire-reports/.
echo "cat ${BUILDROOT}/surefire-reports/${JAVAPKGNAME}.TestYAMLANTLR4.txt"

### EoScript
