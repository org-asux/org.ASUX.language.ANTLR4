lexer grammar YAMLANTLR4Lexer ;

// ==================================

// Imported Grammar Files
	// See how @ https://www.antlr.org/api/maven-plugin/latest/examples/import.html
	// place your import grammars in the directory: src/main/antlr4/imports

// ==================================

/* WARNING: Use the following @header.. ONLY if this .g4 file is at the TOP of the <sourceDirectory> as defined within POM.XML re: antlr4 plugin
	//	@header {
	//		package org.ASUX.language;
	//	}
  In case of this .g4 file, this .g4 file is under src/main/java/org/asux/language/antlr4
  So, antlr4 automatically detects/deduces the java-package name from the folder path.

  So.. what would happen if the above @header was UN-commented?
  Well .. you will end up having 2 'package declarations' in the generated java files.   JAVAC will barf.
*/

// ==================================

/*
 BSD 3-Clause License
 
 Copyright (c) 2019, Udaybhaskar Sarma Seetamraju
 All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 
 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 
 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.
 
 * Neither the name of the copyright holder nor the names of its
 contributors may be used to endorse or promote products derived from
 this software without specific prior written permission.
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/* ===================================================
	LEXER RULES (Token-names are all UPPERcase).
	WARNING!  Order is important!
   =================================================== */

// NOTE: A parser grammar canNOT reference literal in LEXER, but it can reference the name of the tokens!

// ==================================

// So that these make into DefaultLexer.java;  These do a better job than _input.LA() which return PARSER's Token-IDs too.
@members {
    String  lastTokenString;
    int     lastTokenID;
    // Attention: These are set within My-own-Lexer java-class' emit(Token) method.
    // Attention: These are needed, because "_input.LA(-1)" will return parser TokenIDs, which any Lexer has NO access to!
    {
        // any other initialization code here
    }
}
//options    {
//           }


YAML:  'yaml' | 'Yaml' | 'YAML' ;
AWS:  'aws' | 'Aws' | 'AWS' ;

YAML_READ: 'read' | 'Read' | 'READ' ;
YAML_LIST: 'list' | 'List' | 'LIST' ;
YAML_DELETE: 'delete' | 'Delete' | 'DELETE' ;
YAML_REPLACE: 'replace' | 'Replace' | 'REPLACE' ;
YAML_INSERT: 'insert' | 'Insert' | 'INSERT' ;
YAML_MACRO: 'macro' | 'Macro' | 'MACRO' | 'macroyaml' | 'macroYAML' | 'MacroYAML' | 'MACROYAML' ;
YAML_TABLE: 'table' | 'Table' | 'TABLE' ;
YAML_BATCH: 'batch' | 'Batch' | 'BATCH' ;

YAML_COMMAND :	YAML_READ | YAML_LIST | YAML_DELETE | YAML_REPLACE | YAML_INSERT | YAML_MACRO | YAML_TABLE ;

// ==================================

VERBOSE     : '--verbose' | '-v' ;
SHOWSTATS   : '--showStats' | '--showstats' ;

PROJECT     : '--project' | '-p';

DELIMITER_OPT   : '--delimiter' | '-d' ;
YAMLLIBRARY_OPT : '--yamllibrary' | '-yaml' ;
YAMLLIBRARY_LIST: 'SnakeYAML' | 'snakeyaml' | 'esotericsoftware' | 'NodeImpl' | 'nodeimpl' | 'CollectionsImpl' ;
// to-do: i18n enhancements needed to the above.

QUOTEYAMLCONTENT : '--single-quote'	{ setText("'"); } // see http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--Single-Quote'	{ setText("'"); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--SINGLE-QUOTE'	{ setText("'"); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--double-quote'	{ setText("\""); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--Double-Quote'	{ setText("\""); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--DOUBLE-QUOTE'	{ setText("\""); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--no-quote'  	{ setText(""); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--No-Quote'  	{ setText(""); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	| '--NO-QUOTE'  	{ setText(""); }	// http://sds.sourceforge.net/src/antlr/doc/lexer.html#_bb14
	;

INPUT_FROM: '-i' | '--inputfile' | '<' ;
OUTPUT_TO: '-o' | '--outputfile' | '>' ;

RANDOM_OPTION: '--' ANYWORD;

// ==================================

WHITESPACE :	(' ' | '\t' | NEWLINE )+ -> skip ; // !!!!! ATTENTION !!!!! this must precede defn for NEWLINE
NEWLINE :	( '\r' '\n' | '\r' | '\n' )+ ;

NUMBER : DIGIT+ ( [.,] DIGIT+ )* ;

fragment SINGLEQUOTE :		'\'' ;
fragment DOUBLEQUOTE :		'"' ;

fragment DIGIT : [0-9] ;
fragment UPPERCASE: [A-Z];
fragment LOWERCASE: [a-z];
fragment UNICODEDIGIT : [\p{N}] ; // ATTENTION: This automatically includes 'DIGIT'
fragment UNICODECHAR: [\p{L}] ;	// ATTENTION: This automatically includes UPPERCASE and LOWERCASE

fragment CHAR_NONQUOTE_NONWS :	~['"\t\n\r] ; // Not a quote, Not a whitespace character.  Of course Blank can be WITHIN a quoted-string!

fragment FILEPATHCHAR : ( UNICODECHAR | UNICODEDIGIT | UPPERCASE | LOWERCASE | AT | PERCENT | UNDERSCORE | [_.%()-] ) ;

// ==================================

// The following expressions after COL_ON are called "Semantic Predicates"
//  See Antlr mega tutorial @ 16. Solving Ambiguities with Semantic Predicates
UNDERSCORE: {lastTokenID ==  DELIMITER_OPT}?   '_' ;
PERIOD    : {lastTokenID ==  DELIMITER_OPT}?   '.' ;
COMMA	  : {lastTokenID ==  DELIMITER_OPT}?   ',' ;           // Comma can be within a REGEXP. So ..
AT        : {lastTokenID ==  DELIMITER_OPT}?   '@' ;
PERCENT   : {lastTokenID ==  DELIMITER_OPT}?   '%' ;
SLASH     : {lastTokenID ==  DELIMITER_OPT}?   '/' ;
COLON	  : {_input.LA(-1) !=  YAML_COMMAND}?   ':' ;           // Parser need this for JSON
HYPHEN    : {lastTokenID ==  INPUT_FROM || lastTokenID ==  OUTPUT_TO}?   '-' ;   // parser needs this to represent stdin and stdout
            // !!!!!!!!!!!! ATTENTION:  _input.LA(-1) will also return PARSER's Token-IDs.  So, in this case _input.LA(-1) is replaced with lastTokenID
SEMICOLON : ';' ;
JSONBEGIN : '{' ;
JSONEND   : '}' ;
//fragment TAB       : '\t';
//fragment STAR      : '*' ;

BRACKETS : ( '[' | ']' | '(' | ')' );
INDEX_EXPR: BRACKETS NUMBER ('-' NUMBER)? BRACKETS;

// ==================================

// !! ATTENTION !!   The '?' Meta-char after a '+' implies NON-Greedy matching.
SINGLEQUOTEDTEXT : SINGLEQUOTE CHAR_NONQUOTE_NONWS+? SINGLEQUOTE ;
// !! ATTENTION !!   The '?' Meta-char after a '+' implies NON-Greedy matching.
DOUBLEQUOTEDTEXT : DOUBLEQUOTE CHAR_NONQUOTE_NONWS+? DOUBLEQUOTE ;
// !! ATTENTION !!   The '?' Meta-char after a '+' implies NON-Greedy matching.

SINGLEDOUBLEQUOTEDTEXT : SINGLEQUOTE NONQUOTEDTEXT ( DOUBLEQUOTEDTEXT | DOUBLESINGLEQUOTEDTEXT ) NONQUOTEDTEXT SINGLEQUOTE ;	// EXAMPLE:  'My name is "xyz", while my mom calls me "ABC".. '
DOUBLESINGLEQUOTEDTEXT : DOUBLEQUOTE NONQUOTEDTEXT ( SINGLEQUOTEDTEXT | SINGLEDOUBLEQUOTEDTEXT ) NONQUOTEDTEXT DOUBLEQUOTE ;	// EXAMPLE:  "My name is 'XYZ', while my mom calls me 'abc'.. "

// ==================================
// !!!! WARNING !!!! 'FILEPATH' must follow 'SINGLEQUOTEDTEXT' & 'DOUBLEQUOTEDTEXT'
// !!!! WARNING. 'FILEPATH' does NOT allow a SIMPLE-file-name (which will be considered 'ANYWORD' or 'SINGLEQUOTEDTEXT')

FILEPATH : '/'? FILEPATHCHAR+ ( '/' FILEPATHCHAR+ )+ ;

FILEPATH_ATPREFIX : '@' FILEPATH
                |   '@' '/'? FILEPATHCHAR+ ;

// ==================================

// !!!! ATTENTION !!!! 'ANYWORD' should be among the LAST TOKEN definitions !!

// NOT the same as 'FILEPATHCHAR' .. as it's missing '()='
ANYWORD :	( UNICODECHAR | UNICODEDIGIT | UPPERCASE | LOWERCASE | [_.,@%/:+-] ) +  ;
// ANYWORD :	( UNICODECHAR | UNICODEDIGIT | UPPERCASE | LOWERCASE | [_.,:;<>@%+{}\[\]-] ) +  ; 

// ==================================
// !!!! WARNING !!!! Lexer-definition for 'SIMPLEWORD' should follow 'ANYWORD', as 'SIMPLEWORD' matches almost everything.
// So, added SEMANTIC-PREDICATE, so that SIMPLEWORD matches __ONLY IF__ the preceding token is NOT a 'YAML_COMMAND'
// Ideally, AVOID semantic-predicates.. unless they canNOT be avoided.
//	  Instead, Choose to move the sematic-pred-LOGIC into the visitor-code and keep this grammer Language-INDEPendent!
// NOTE: In java, '.getType()' is unnecessary for LA() invocation.
// https://www.antlr.org/api/Java/org/antlr/v4/runtime/UnbufferedCharStream.html
// .LA() gives you just the token type, while .LT() gives you the entire token
// Dont want a REGEXP-parser-token to match this Lexer defn.   Hence the Semantic Predicate condition.
SIMPLEWORD : {_input.LA(-1) !=  YAML_COMMAND}?
	( UNICODECHAR | UNICODEDIGIT | UPPERCASE | LOWERCASE | [_.-] ) +  ;
// Above will match AlphaNumerics and the EQUIVALENT for Non-English Character-sets (in addition to '_', '-' and periods)
// Typically, this is reserved for Semantic-concepts like 'Variable-names', 'AWS-YAML Keys', ..

INLINEPROPERTIES : {_input.LA(-1) !=  YAML_MACRO}?
      ( SIMPLEWORD '=' SIMPLEWORD )
    | ( SIMPLEWORD  '=' SIMPLEWORD ';' INLINEPROPERTIES )
    ;
// Dont want a REGEXP-parser-token to match the above Lexer definition.   Hence the Semantic Predicate condition.

// ==================================
// ATTENTION : 'NONQUOTEDTEXT' is among the MOST generic/loose token-definitions.  So, must be last.
// It will allow __ANY__ NON-Alphanumeric string (bounded by white-space)

// !! ATTENTION !!   The '?' Meta-char after a '+' implies NON-Greedy matching.
NONQUOTEDTEXT    : CHAR_NONQUOTE_NONWS+?  ;  // will NOT allow a quote anywhere within this 'NONQUOTEDTEXT'
// !! ATTENTION !!   The '?' Meta-char after a '+' implies NON-Greedy matching.

// ==================================


//EOF
