#!/bin/tcsh -f

set HOMEFLDR="$0:h"
if ( "${HOMEFLDR}" == "$0" ) then
	### TCSH Quirk:  this means '$0' is purely a simple file name with NO path-prefix (not even './')
	set HOMEFLDR=""
endif
#__  echo "HOMEFLDR=[${HOMEFLDR}]"

if ( "${HOMEFLDR}" == "." || "${HOMEFLDR}" == "" ) then
	chdir ..  ### Go to parent folder which should contain the 'bin' subfolder containing these .csh scripts.
else
	if ( "${HOMEFLDR}" != "test" && "${HOMEFLDR}" != "./test" ) then
		echo "Please run this command from the project Home folder only" >& /dev/stderr
		exit 2
	else
		### good.  This command is being run from the Project's home folder.
	endif
endif

set HOMEFLDR="$cwd"

source $HOMEFLDR/bin/common.csh-source

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
mvn compiler:testCompile test
mvn compiler:testCompile test

###----------------------------
if ( $?VERBOSE) ls -lad ${BUILDROOT}/surefire-reports/.
echo "cat ${BUILDROOT}/surefire-reports/${JAVAPKGNAME}.TestYAMLANTLR4.txt"

### EoScript
