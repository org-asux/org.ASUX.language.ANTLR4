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

echo '\
( chdir ${JAVAPKG_SRCFLDR} ; \rm -rf *Parser*.java *Lexer.java *.interp )'
#___ pushd ${JAVAPKG_SRCFLDR}
#___ foreach PartialFileName (  Parser.interp ParserBaseVisitor.java Lexer.interp Parser.java ParserListener.java Lexer.java ParserBaseListener.java ParserVisitor.java )
### Decided that .. instead of HARD-coding the list of files (as in the 'foreach' line above) .. we'll leverage .gitignore which exists to ENSURE these above "auto-gen" files are NOT placed into SCCM.
foreach FileName ( ${IGNORED_FILES} )
	set FilePATH="${JAVAPKG_SRCFLDR}/${FileName}"
	ls -lad ${FilePATH}
	#___ echo -n ' continue ?>>'; set ANS="$<"
	rm -f ${FilePATH}
end
#___ popd 

echo '\
( chdir ./src/main/java ; \rm -rf ${GRAMMERNAMEPREFIX}Parser.tokens ${GRAMMERNAMEPREFIX}Lexer.tokens )'
( chdir ./src/main/java ; \rm -rf ${GRAMMERNAMEPREFIX}Parser.tokens ${GRAMMERNAMEPREFIX}Lexer.tokens )

echo '\
( chdir ${PROJMVNINSTALLFOLDER} ; \rm -rf [0-9]* maven-metadata-local.xml )'
( chdir ${PROJMVNINSTALLFOLDER} ; \rm -rf [0-9]* maven-metadata-local.xml )
echo ''
mvn clean
echo ''; echo ''

### EoScript
