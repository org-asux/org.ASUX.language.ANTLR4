#!/bin/tcsh -f

source ./common.csh-source

###============================================
###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
###============================================

echo '\
( chdir ${JAVAPKG_SRCFLDR} ; \rm -rf *Parser*.java *Lexer.java *.interp )'
#___ pushd ${JAVAPKG_SRCFLDR}
#___ foreach PartialFileName (  Parser.interp ParserBaseVisitor.java Lexer.interp Parser.java ParserListener.java Lexer.java ParserBaseListener.java ParserVisitor.java )
### Decided that .. instead of HARD-coding the list of files (as in the 'foreach' line above) .. we'll leverage .gitignore which exists to ENSURE these above "auto-gen" files are NOT placed into SCCM.
foreach FileName ( `cat "${JAVAPKG_SRCFLDR}/.gitignore" ` )
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
