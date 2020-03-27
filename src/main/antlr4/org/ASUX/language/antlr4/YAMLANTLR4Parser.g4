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

yaml_commands : yaml_command+ EOF ;

yaml_command: ( yaml_command_read | yaml_command_list | yaml_command_delete | yaml_command_replace | yaml_command_insert | yaml_command_macro | yaml_command_table ) SEMICOLON NEWLINE* ;

yaml_command_read:	YAML YAML_READ	  regularexpression	optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_list:	YAML YAML_LIST 	  regularexpression	optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_delete:	YAML YAML_DELETE  regularexpression	optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_replace:	YAML YAML_REPLACE regularexpression newcontent	optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_insert:	YAML YAML_INSERT  regularexpression newcontent	optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_table:	YAML YAML_TABLE	  regularexpression columns	optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_macro:	YAML YAML_MACRO   FILEPATH		optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;
yaml_command_batch:	YAML YAML_BATCH	  FILEPATH		optionals INPUT_FROM inputSrc=FILEPATH OUTPUT_TO outputSink=FILEPATH ;

// ==================================

optionals  : ( ( DELIMITER_OPT  delimiter+=any_quoted_text ) | ( YAMLLIBRARY_OPT  yamlImplementation+=YAMLLIBRARY_LIST ) | VERBOSE | SHOWSTATS | yamlQuoteChar=QUOTEYAMLCONTENT )* ;

newcontent : (  FILEPATH | inlinejson  ) ;

columns    : SIMPLEWORD ( COMMA SIMPLEWORD )* ;

// ==================================

inlinejson     :  JSONBEGIN  inlinejsonelem  ( COMMA inlinejsonelem ) *   JSONBEGIN ;
inlinejsonelem :	( SIMPLEWORD | any_quoted_text )	COLON	( SIMPLEWORD | any_quoted_text | inlinejson ) ;

// ==================================


any_quoted_text : SINGLEQUOTEDTEXT | DOUBLEQUOTEDTEXT | SINGLEDOUBLEQUOTEDTEXT | DOUBLESINGLEQUOTEDTEXT ;
// any_quoted_text : SINGLEQUOTEDTEXT | DOUBLEQUOTEDTEXT ;


// WARNING: Do NOT move 'REGULAREXPRESSION' to 'PARSER' section
// NOTE: an XML-path or a YAML-Path can look like a file-path with a leading '/'
regularexpression: FILEPATH | any_quoted_text | NONQUOTEDTEXT;


//EOF