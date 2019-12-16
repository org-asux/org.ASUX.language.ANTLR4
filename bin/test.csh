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
	if ( "${HOMEFLDR}" != "bin" && "${HOMEFLDR}" != "./bin" ) then
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

if ( $#argv <= 1 ) then
    echo "Usage:	$0 <GrammarClassName> <startRule>  <user-input.filename>"	>>& /dev/stderr
    echo "	$0 ${JAVAPKGNAME}.YAMLANTLR4     yaml_commands  src/test/resources/user-input-YAML_commands.txt"	>>& /dev/stderr
    echo "  NOTE\!\!	2nd argument is the class's full package-name (__withOUT__ the Parser/Lexer suffix)"	>>& /dev/stderr
    echo "  OPTIONAL:  [-v]  [--verbose]  [-tokens]"	>>& /dev/stderr
    echo ''								>>& /dev/stderr
    exit 1
endif

###----------------------------
if ( "$1" == "--verbose" || "$1" == "-v" ) echo "ANTLR4_COMPLETEJAR = ${ANTLR4_COMPLETEJAR}"

if ( ! -e ${ANTLR4_COMPLETEJAR} ) then
	echo "Step #1: download from https://www.antlr.org/download.html" >>& /dev/stderr
	echo "		antlr-4.x.x-complete.jar"			>>& /dev/stderr
	echo '' 							>>& /dev/stderr
	echo " .. press enter to continue >"; set ANS="$<"		>>& /dev/stderr
	exit 2
endif

###----------------------------

echo -n ".. did you remember to run ./clean.csh? >> "; set ANS="$<"
echo -n ".. did you remember to run ./compile.csh? >> "; set ANS="$<"

###============================================
###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
###============================================

###----------------------------
if ( ! -e "${BUILDROOT}/classes/${JAVAPKG_ASPATH}/${COMPILEDJAVAFILE}" ) then
	echo "Missing COMPILED java classes @ ${BUILDROOT}/classes/${JAVAPKG_ASPATH}/${COMPILEDJAVAFILE}"	>>& /dev/stderr
	exit 5
endif

###----------------------------
if ( "$1" == "--verbose" || "$1" == "-v" ) then
	set VERBOSE=1
	shift
endif

###============================================
###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
###============================================

setenv CLASSPATH "${CLASSPATH}:${ANTLR4_COMPLETEJAR}:${BUILDROOT}/classes"
if ( $?VERBOSE) echo "CLASSPATH = [${CLASSPATH}]"			>>& /dev/stderr
if ( $?VERBOSE) echo ''							>>& /dev/stderr

###============================================
###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
###============================================

#___	chdir ${BUILDROOT}/classes
#___	if ( $?VERBOSE) ls ${JAVAPKG_ASPATH}/*				>>& /dev/stderr
#___ chdir src/main/java
if ( $?VERBOSE) pwd							>>& /dev/stderr

###----------------------------

echo "about to run cmd-line test .."; sleep 2

### whether the RIGHT LEXER Tokens are detected
if ( $?VERBOSE) echo '\
java  org.antlr.v4.gui.TestRig "$1" "$2" -tokens < "$3"'				>>& /dev/stderr
java  org.antlr.v4.gui.TestRig "$1" "$2" -tokens < "$3" ### <GrammarName> <startRuleName> -tokens

### ASCII-based Tree output by PARSER
if ( $?VERBOSE) echo '\
java  org.antlr.v4.gui.TestRig "$1" "$2" -tree -ps /tmp/antlr4.ps < "$3"'				>>& /dev/stderr
java  org.antlr.v4.gui.TestRig "$1" "$2" -tree -ps /tmp/antlr4.ps < "$3" ### <GrammarName> <startRuleName> -tokens

# ### PDF-based Tree output by PARSER
# if ( $?VERBOSE) echo '\
# java  org.antlr.v4.gui.TestRig "$1" "$2" -ps /tmp/antlr4.ps < "$3"'				>>& /dev/stderr
# java  org.antlr.v4.gui.TestRig "$1" "$2" -ps /tmp/antlr4.ps < "$3" ### <GrammarName> <startRuleName> -tokens

### Other org.antlr.v4.gui.TestRig  cmdline options: [-gui] [-ps file.ps] [-trace] [-diagnostics] [-SLL] 

### EoScript
