parser grammar YAMLANTLR4Parser ;

// ==================================

options { tokenVocab= YAMLANTLR4Lexer; }

// Imported Grammar Files
	// See how @ https://www.antlr.org/api/maven-plugin/latest/examples/import.html
	// place your import grammars in the directory: src/main/antlr4/imports

// ==================================

/* WARNING: Use the following @header.. ONLY if this .g4 file is at the TOP of the <sourceDirectory> as defined within POM.XML re: antlr4 plugin
	//	@header {
	//		package org.ASUX.language;
	//	}
  In case of this .g4 file, this .g4 file is under src/main/java/org/asux/language
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

/* ==================================
	PARSER RULES (all LOWERcase)
   ================================== */

yaml_commands : yaml_command ( SEMICOLON  NEWLINE*  yaml_command )* EOF ;

yaml_command: ( yaml_command_read | yaml_command_list | yaml_command_delete | yaml_command_replace | yaml_command_insert | yaml_command_macro | yaml_command_table | yaml_command_batch )  ;

yaml_command_read:	    optionals   YAML optionals YAML_READ	optionals   regularexpression	(PROJECT projectionpath)? optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)  optionals;
yaml_command_list:	    optionals   YAML optionals YAML_LIST 	optionals   regularexpression               optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;
yaml_command_delete:	optionals   YAML optionals YAML_DELETE  optionals   regularexpression               optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;
yaml_command_replace:	optionals   YAML optionals YAML_REPLACE optionals   regularexpression newcontent	optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;
yaml_command_insert:	optionals   YAML optionals YAML_INSERT  optionals   regularexpression newcontent	optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;
yaml_command_table: 	optionals   YAML optionals YAML_TABLE   optionals   regularexpression columnslist   optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;
yaml_command_macro:	    optionals   YAML optionals YAML_MACRO   optionals   macroProperties         		optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;
yaml_command_batch:	    optionals   YAML optionals YAML_BATCH	optionals   batchFilePath                   optionals INPUT_FROM inputSrc=(HYPHEN|FILEPATH) OUTPUT_TO outputSink=(HYPHEN|FILEPATH)   optionals;

// ==================================

optionals  : ( ( DELIMITER_OPT  delimiter+=delimiter_text ) | ( YAMLLIBRARY_OPT  yamlImplementation+=YAMLLIBRARY_LIST ) | VERBOSE | SHOWSTATS | yamlQuoteChar=QUOTEYAMLCONTENT )* ;

// ==================================

delimiter_text : delimiter_char | SINGLEQUOTEDTEXT | DOUBLEQUOTEDTEXT ;
delimiter_char : COMMA | AT | SLASH | PERCENT | UNDERSCORE | PERIOD ;
// !!!!!! Warning.  Do NOT add 'SEMICOLON' to the list of delimiters.
// !!!!!! Warning.  Do NOT add 'COLON' to the list of delimiters.. .. until you can figure out how JSON and this can CO-EXIST


any_quoted_text : SINGLEQUOTEDTEXT | DOUBLEQUOTEDTEXT | SINGLEDOUBLEQUOTEDTEXT | DOUBLESINGLEQUOTEDTEXT ;

//=================================================================================
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//=================================================================================

// WARNING: Do NOT move 'regularexpression' or 'projectionpath' to 'PARSER' section
// ATTENTION: an XML-path or a YAML-Path can look like a file-path with a leading '/' .. .. hence the use of FILEPATH

regularexpression: FILEPATH | any_quoted_text | SIMPLEWORD | ( COMMA | ANYWORD | INDEX_EXPR | NONQUOTEDTEXT )+        // see notes below !!!!!!!!!!!!!!!!!!
    {   if ( $ctx.getText().startsWith("--") ) {
            // !!!!!!!!!!!!!!!!!!!!!!!!!! Yeah !!!!!!!!!!!!!!!!!! This code works.  Try providing '--XYZ' instead of a RegExp!
            System.err.println( $ctx.getText() +" command-line option is unknown" );
            final RecognitionException re = new RecognitionException( this, this.getInputStream(), $ctx );
            notifyErrorListeners( getCurrentToken(), "This command-line option is unknown", re );
        }
    };
    // we need both 'BRACKETS' and "NUMBERS" in the definition as "[1]" will be parsed as 3 separate tokens as: BRACKETS NUMBER BRACKETS

batchFilePath : FILEPATH_ATPREFIX | FILEPATH | any_quoted_text;

//=================================================================================
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//=================================================================================

newcontent : (  FILEPATH_ATPREFIX | any_quoted_text | IN_MEMORY_LABEL  ) ; // inlinejson

macroProperties : INLINEPROPERTIES | FILEPATH_ATPREFIX ;

columnslist : SINGLEQUOTEDTEXT | DOUBLEQUOTEDTEXT | ANYWORD ;
            // Attention: Ensure the list of EXPLICIT single-chars above, matches the list for Parser's delimiter_char


projectionpath: any_quoted_text | SIMPLEWORD | ANYWORD;

// ==================================

inlinejson     :  JSONBEGIN  inlinejsonelem  ( COMMA inlinejsonelem ) *   JSONEND ;
inlinejsonelem :	( SIMPLEWORD | any_quoted_text )	COLON	( SIMPLEWORD | any_quoted_text | inlinejson ) ;

//EOF
