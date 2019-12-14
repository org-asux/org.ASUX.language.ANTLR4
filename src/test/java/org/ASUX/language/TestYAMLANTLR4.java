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

package org.ASUX.language;

import java.util.ArrayList;
import java.util.regex.*;

import org.antlr.v4.runtime.*;      // https://www.antlr.org/api/Java/org/antlr/v4/runtime/package-summary.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CommonTokenStream.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/Token.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTree.html
import org.antlr.v4.runtime.tree.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
import org.antlr.v4.runtime.misc.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/UnbufferedCharStream.html

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test; // for Test Annotation

public class TestYAMLANTLR4 {

    private static final String HDR0 = TestYAMLANTLR4.class.getName();
    public static final String CLASSNAME = HDR0;
    private static final String TEST_INPUT_YAML_COMMANDS = "src/test/resources/user-input-YAML_commands.txt";

    public boolean verbose = true;

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    public static class MyParser extends YAMLANTLR4Parser {
        private static final String HDR0 = TestYAMLANTLR4.class.getName();

        /**
         * Super class has no default constructor
         * @param input see {@link org.ASUX.language.YAMLANTLR4Parser}
        */
        public MyParser( TokenStream input ) {
            super(input);
        }

        /** Always called by generated parsers upon entry to a rule. */
        @Override
        public void enterRule( ParserRuleContext localctx, int state, int ruleIndex ) {
            final String HDR = HDR0 + ".enterRule(): ";
            super.enterRule( localctx, state, ruleIndex );
            System.out.println( HDR + "rule-index="+ ruleIndex + " localCtx="+ localctx );
        }
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    @Test
    public void testYAMLCommands() {
        final String HDR = HDR0 + ".testYAMLCommands():--------------------- ";

        try {
            // final String yamlCmdStr = "yaml read '/reg/\"exp\"/g' < @/file/name > path.to/file";
            // CharStream inputStream = CharStreams.fromString( yamlCmdStr );   // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html
            // DEPRECATED: ANTLRInputStream inputStream = new ANTLRInputStream( yamlCmdStr ); 
            CharStream inputStream = CharStreams.fromFileName( TEST_INPUT_YAML_COMMANDS ); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html
// yaml read '/reg/"exp"/g' --verbose --delimiter "/" --showStats -v < @/file/name > path.to/file ; 
// yaml list '/2reg/expresssion' --delimiter "JUNKSTR" -d "/" --yamllibrary SnakeYAML --single-quote -i @/file/name --output 

            YAMLANTLR4Lexer mylexer = new YAMLANTLR4Lexer( inputStream );
            CommonTokenStream commonTokenStream = new CommonTokenStream( mylexer );

            if ( this.verbose ) System.out.println( HDR + "about to parse" );
            YAMLANTLR4Parser parser = new YAMLANTLR4Parser( commonTokenStream );

            //==============================================================================
            // // NOTE: The "typical" listener should be defined _BEFORE_ invoking 'parser.yaml_command()'
            // parser.addParseListener( new YAMLANTLR4ParserBaseListener() ); // This Listener is automatically generated by ANTLR4
            // // Error Listener
            // parser.addErrorListener( new MyErrorListener() );  // This is __MY OWN__  Java-class to listen to errors.
            //     // void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
            //     // Upon syntax error, notify any interested parties.

            //==============================================================================
            // Start parsing
            // YAMLANTLR4Parser.Yaml_commandContext context = parser.yaml_command();  // if the grammer/scenario restricted user-input to JUST 1 command ONLY.
            final java.util.List<YAMLANTLR4Parser.Yaml_commandContext> cmdsCtx = parser.yaml_commands().yaml_command();

            //==============================================================================
            // The preferred method of getting a tree pattern.
            // final ParseTreePattern treep = parser.compileParseTreePattern(String pattern, int patternRuleIndex);
            // if ( this.verbose ) System.out.println( HDR + "Parsed the 'parser-TREE'" );

            //==============================================================================
            // // NOTE: Visitor can ONLY be invoked AFTER the "Tree" is build by the 'parser.yaml_command()'
            // if ( this.verbose ) System.out.println( HDR + "about to engage VISITOR class to walk thru the parsed 'parser-TREE'" );
            // // VISITOR PATTERN.  Will trigger invocation of YAMLANTLR4ParserVisitor.visitContent()
            // MyYAMLParserVisitor visitor =  new MyYAMLParserVisitor();
            // // YAMLANTLR4ParserBaseVisitor<String> visitor =  new YAMLANTLR4ParserBaseVisitor<String>();
            // visitor.visit( context );

            //==============================================================================
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //==============================================================================

            // TESTING
            int ii = 0;

            for (YAMLANTLR4Parser.Yaml_commandContext eachCmdCtx: cmdsCtx ) {

                final YAMLANTLR4Parser.Yaml_command_readContext readCtx = eachCmdCtx.yaml_command_read();
                if ( readCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml READ command detected!" );
                    if ( this.verbose ) System.out.println( HDR + "about to run LEXER's ASSERT-checks" );
                    assertTrue( YAMLANTLR4Lexer.YAML          == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.YAML_READ     == commonTokenStream.get( ii++ ).getType() );
                    final int regExpPos = ii;
                    // assertTrue( YAMLANTLR4Lexer.REG ULAR EXP RESSION == commonTokenStream.get( ii++ ).getType() ); // no longer have such a token.  Instead it's a parser element.
                    assertTrue( YAMLANTLR4Lexer.SINGLEDOUBLEQUOTEDTEXT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.VERBOSE == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.DELIMITER_OPT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.DOUBLEQUOTEDTEXT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.SHOWSTATS == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.VERBOSE == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.INPUT_FROM    == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.FILEPATH      == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.OUTPUT_TO     == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.FILEPATH      == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.SEMICOLON     == commonTokenStream.get( ii++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.NEWLINE    == commonTokenStream.get( ii++ ).getType() ); // Grammer/Lexer rules updated to IGNORE all WhiteSpace, incl. NEWLINE
                    // if ( this.verbose ) System.out.println( HDR + "readCtx ="+ readCtx ); // USELESS DEBUG STATEMENT.  You'll see something like: [33 26]
                    final YAMLANTLR4Parser.RegularexpressionContext regExpCtx = readCtx.regularexpression();
                    final String regExpStr = commonTokenStream.get( regExpPos ).getText();
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's REGEXPstring(from Lexer) =["+ regExpStr +"]"+ regExpCtx );
                    // final String s = this.getRegExpAsString( regExpCtx );
                    final ArrayList<String> sss = this.toStrings( regExpCtx );
                    // assertFalse( sss.isEmpty() );
                    assertTrue( sss.size() == 1 );
                    final String s = sss.get( 0 );
                    // if ( this.verbose ) System.out.println( HDR + "Read-YAML's REGEXPstring(from Parser) =["+ s +"] " );
                    assertTrue( s.equals( regExpStr ) );
                    assertTrue( s.equals( "'/reg/\"exp\"/g'" ) );

                    final YAMLANTLR4Parser.OptionalsContext optionalsCtx = readCtx.optionals();
                    final ArrayList<String> sss22 = this.toStrings( optionalsCtx );
                    assertTrue( sss22.size() == 5 );
                    // if ( this.verbose ) System.out.println( HDR + "Read-YAML's OPTIONALs =["+ sss22 +"]" );
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's OPTIONALs:- " );
                    sss22.forEach(System.out::println);
                    
                    final java.util.List<YAMLANTLR4Parser.Any_quoted_textContext> delims = optionalsCtx.delimiter;
                    if ( this.verbose ) System.out.print( HDR + "Read-YAML's Delimiters:- " );
                    // does NOT WORK: delims.forEach( d -> System.out.println(d.getText()) );
                    delims.forEach( delim -> { final ArrayList<String> sss33 = this.toStrings( delim ); sss33.forEach( System.err::println); } );
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's Effective-Delimiter = ["+ this.getEffectiveOption( optionalsCtx.delimiter ) +"]" );

                    try {
                        final java.util.List<Token> yamlImps = optionalsCtx.yamlImplementation;
                        // final java.util.List<TerminalNode> yamlImps = optionalsCtx.YAMLLIBRARY_OPT();
                        if ( ! yamlImps.isEmpty() && this.verbose ) System.out.print( HDR + "Read-YAML's yamlImplementation:- " );
                        yamlImps.forEach( tk -> System.out.println(tk.getText()) );
                        if ( this.verbose ) System.out.println( HDR + "Read-YAML's Effective-YAML-Implementation = ["+ this.getEffectiveTokenOption( optionalsCtx.yamlImplementation ) +"]" );
                    } catch ( java.util.NoSuchElementException e ) {
                        if ( e.getMessage().equals("The method's sole-argument passed.. is an empty java.util.List object") )
                            System.out.println( HDR + "Accurately verified code-exception-logic - that Read-CMd has NO YAML-Implementation cmdline options" );
                        else
                            throw e;
                    }

                    final java.util.List<TerminalNode> showStatsOpt = optionalsCtx.SHOWSTATS();
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's show-Statistics = ["+ ( showStatsOpt.size() > 0 ) +"]" );
                    assertTrue( showStatsOpt.size() > 0 );

                    final java.util.List<TerminalNode> verboseOpt = optionalsCtx.VERBOSE();
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's verbose-Count = ["+ verboseOpt.size() +"]" );
                    assertTrue( verboseOpt.size() == 2 );

                    final String inputStr  = commonTokenStream.get( regExpPos + 2 ).getText();
                    final String outputStr = commonTokenStream.get( regExpPos + 4 ).getText();
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's InputSOURCE =["+ inputStr +"] OutputSink=["+ outputStr +"]" );

                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }

                final YAMLANTLR4Parser.Yaml_command_listContext listCtx = eachCmdCtx.yaml_command_list();
                if ( listCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml LIST command detected!" );
                    assertTrue( ii == 13 );  // this YAML_LIST command .. follows the 8 tokens for YAML_READ
                    if ( this.verbose ) System.out.println( HDR + "about to run LEXER's ASSERT-checks" );
                    assertTrue( YAMLANTLR4Lexer.YAML          == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.YAML_LIST     == commonTokenStream.get( ii++ ).getType() );
                    final int regExpPos = ii;
                    assertTrue( YAMLANTLR4Lexer.SINGLEQUOTEDTEXT == commonTokenStream.get( ii++ ).getType() ); // Note the difference in TOKEN-name here vs. the one 9 lines above.
                    assertTrue( YAMLANTLR4Lexer.DELIMITER_OPT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.DOUBLEQUOTEDTEXT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.DELIMITER_OPT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.DOUBLEQUOTEDTEXT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.YAMLLIBRARY_OPT  == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.YAMLLIBRARY_LIST == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.QUOTEYAMLCONTENT == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.INPUT_FROM    == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.FILEPATH      == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.OUTPUT_TO     == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.FILEPATH      == commonTokenStream.get( ii++ ).getType() );
                    assertTrue( YAMLANTLR4Lexer.SEMICOLON     == commonTokenStream.get( ii++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.NEWLINE    == commonTokenStream.get( ii++ ).getType() ); // Grammer/Lexer rules updated to IGNORE all WhiteSpace, incl. NEWLINE
                    // if ( this.verbose ) System.out.println( HDR + "listCtx ="+ listCtx ); // USELESS DEBUG STATEMENT.  You'll see something like: [34 26]
                    final YAMLANTLR4Parser.RegularexpressionContext regExpCtx = listCtx.regularexpression();
                    final String regExpStr = commonTokenStream.get( regExpPos ).getText();
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's REGEXPstring(from Lexer) =["+ regExpStr +"] "+ regExpCtx );
                    final String s = this.getRegExpAsString( regExpCtx );
                    // if ( this.verbose ) System.out.println( HDR + "LIST-YAML's REGEXPstring(from Parser) =["+ s +"] " );
                    assertTrue( s.equals( regExpStr ) );
                    assertTrue( s.equals( "'/2reg/expresssion'" ) );

                    final YAMLANTLR4Parser.OptionalsContext optionalsCtx = listCtx.optionals();
                    final ArrayList<String> sss22 = this.toStrings( optionalsCtx );
                    assertTrue( sss22.size() == 7 );
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's OPTIONALs:- " );
                    sss22.forEach(System.out::println);

                    // --delimiter 'JunkString' -d ","
                    // Unlike YamlQUoteChar (below), the PARSER-grammer __Collects__ every instance of the '--delimiter' option entered by User.
                    // No reason to have such a COMPLEX grammer.  This code is to demonstrate.  Do not make things this complex.
                    final java.util.List<YAMLANTLR4Parser.Any_quoted_textContext> delims = optionalsCtx.delimiter;
                    assertTrue( delims.size() == 2 );
                    if ( this.verbose ) System.out.print( HDR + "LIST-YAML's Delimiters:- " );
                    // does NOT WORK: delims.forEach( d -> System.out.println(d.getText()) );
                    delims.forEach( delim -> { final ArrayList<String> sss33 = this.toStrings( delim ); sss33.forEach( System.err::println); } );
                    // if ( this.verbose ) System.out.println( HDR + "LIST-YAML's the EFFECTIVE-delim =["+ this.toStrings( delims.get( delims.size()-1 ) ).get(0) +"]" );
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's Effective-Delimiter = ["+ this.getEffectiveOption( optionalsCtx.delimiter ) +"]" );

                    // --yamllibrary SnakeYAML --yamllibrary NodeImpl
                    // Unlike YamlQUoteChar (below), the PARSER-grammer __Collects__ every instance of the '--delimiter' option entered by User.
                    // No reason to have such a COMPLEX grammer.  This code is to demonstrate.  Do not make things this complex.
                    final java.util.List<Token> yamlImps = optionalsCtx.yamlImplementation;
                    assertTrue( yamlImps.size() == 1 );
                    // final java.util.List<TerminalNode> yamlImps = optionalsCtx.YAMLLIBRARY_OPT();
                    if ( ! yamlImps.isEmpty() && this.verbose ) System.out.print( HDR + "LIST-YAML's yamlImplementation:- " );
                    yamlImps.forEach( tk -> System.out.println(tk.getText()) );
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's Effective-YAML-Implementation = ["+ this.getEffectiveTokenOption( optionalsCtx.yamlImplementation ) +"]" );

                    final Token yamlQuoteChar = optionalsCtx.yamlQuoteChar;
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's Effective-QuotingChar = ["+ yamlQuoteChar.getText() +"]" );
                    assertTrue( "'".equals( yamlQuoteChar.getText() ) );

                    final java.util.List<TerminalNode> showStatsOpt = optionalsCtx.SHOWSTATS();
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's show-Statistics = ["+ ( showStatsOpt.size() > 0 ) +"]" );
                    assertTrue( showStatsOpt.size() == 0 );

                    final java.util.List<TerminalNode> verboseOpt = optionalsCtx.VERBOSE();
                    if ( this.verbose ) System.out.println( HDR + "List-YAML's verbose-Count = ["+ verboseOpt.size() +"]" );
                    assertTrue( verboseOpt.size() == 0 );

                    final String inputStr  = commonTokenStream.get( regExpPos + 2 ).getText();
                    final String outputStr = commonTokenStream.get( regExpPos + 4 ).getText();
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's InputSOURCE =["+ inputStr +"] OutputSink=["+ outputStr +"]" );

                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }

                final YAMLANTLR4Parser.Yaml_command_tableContext tblCtx = eachCmdCtx.yaml_command_table();
                if ( tblCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml TABLE command detected!" );
                    // assertTrue( ii == 23 ); // this YAML_TABLE command .. follows the 8 tokens for YAML_READ + 8 for YAML_LIST
                    // if ( this.verbose ) System.out.println( HDR + "about to run LEXER's ASSERT-checks" );
                    // assertTrue( YAMLANTLR4Lexer.YAML          == commonTokenStream.get( ii++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.YAML_TABLE    == commonTokenStream.get( ii++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.SEMICOLON     == commonTokenStream.get( ii++ ).getType() );
                    // // assertTrue( YAMLANTLR4Lexer.NEWLINE    == commonTokenStream.get( ii++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.EOF           == commonTokenStream.get( ii++ ).getType() );
                    if ( this.verbose ) System.out.println( HDR + "tblCtx ="+ tblCtx );

                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }

                final YAMLANTLR4Parser.Yaml_command_deleteContext  deleteCtx = eachCmdCtx.yaml_command_delete();
                if ( deleteCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml DELETE command detected!" );
                    
                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }
                final YAMLANTLR4Parser.Yaml_command_replaceContext replaceCtx = eachCmdCtx.yaml_command_replace();
                if ( replaceCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml REPLACE command detected!" );
                    
                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }
                final YAMLANTLR4Parser.Yaml_command_insertContext  insertCtx = eachCmdCtx.yaml_command_insert();
                if ( insertCtx != null )  {
                    if ( this.verbose ) System.out.println( HDR + " yaml INSERT command detected!" );
                    
                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }
                final YAMLANTLR4Parser.Yaml_command_macroContext   macroCtx = eachCmdCtx.yaml_command_macro();
                if ( macroCtx != null )  {
                    if ( this.verbose ) System.out.println( HDR + " yaml MACRO command detected!" );

                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }

            } // for loop

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println( HDR + e.getMessage() );
        }

    } // testYAMLReadCommand()

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    // * @param _lexerToken NotNull reference to the LEXER TOKEN (so that the method can return the text of that token)
    /**
     * A RegExp for YAML-Commands is defined in the parser as: (FILEPATH | any_quoted_text | NONQUOTEDTEXT).  We do Not know (nor do we care to know) which one of the 3 was parsed.<br>
     * This method will transparently check each of the 3 possibilities .. and retun the String that got identified as 'regularexpression' Parser-Token.
     * @param _regExpCtx NotNull reference obtained by invoking 'singleYAMLCmdCtx.regularexpression()'
     * @return NotNull or throws exception
     * @throws RuntimeException if something wierd happens that code-writer did not anticipate (bug-alert)
     */
    private String getRegExpAsString( final YAMLANTLR4Parser.RegularexpressionContext _regExpCtx) throws RuntimeException {
        //  Possibility #1: FILEPATH (LEXER Token)
        final org.antlr.v4.runtime.tree.TerminalNode fp = _regExpCtx.FILEPATH(); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
        if ( fp != null ) return fp.getText();

        //  Possibility #2: any_quoted_text (Parser Token)
        final YAMLANTLR4Parser.Any_quoted_textContext aqtCtx = _regExpCtx.any_quoted_text();
        if ( aqtCtx != null ) {
            final org.antlr.v4.runtime.tree.TerminalNode sqt = aqtCtx.SINGLEQUOTEDTEXT();  // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
            if ( sqt != null ) return sqt.getText();
            final TerminalNode dqt = aqtCtx.DOUBLEQUOTEDTEXT();
            if ( dqt != null ) return dqt.getText();
            final TerminalNode sdqt = aqtCtx.SINGLEDOUBLEQUOTEDTEXT();
            if ( sdqt != null ) return sdqt.getText();
            final TerminalNode dsqt = aqtCtx.DOUBLESINGLEQUOTEDTEXT();
            if ( dsqt != null ) return dsqt.getText();
        }

        //  Possibility #3: NONQUOTEDTEXT (LEXER Token)
        final org.antlr.v4.runtime.tree.TerminalNode nqt = _regExpCtx.NONQUOTEDTEXT(); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
        if ( nqt != null ) return nqt.getText();

        // UNEXPECTED.  Bug Report please!
        throw new RuntimeException( "Bug Report. Clearly the .g4 PARSER-language definition has been updated, and this method is NOT in sync! "+ _regExpCtx );
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
     * <code>class RegularexpressionContext extends ParserRuleContext</code><br>
     * <code>class OptionalsContext extends ParserRuleContext</code><br>
     * Many of my ASUX-YAML Parser constructs have heirarchy of parser-tokens (before we get to the Lexer-Tokens).  ALmost of my parser-constructs match just one Lexer-token, just that we do Not know which one.<br>
     * This method is a generic way to get the Lexer-token.<br>
     * @param _prc NotNull instance obtained from top-level context object.
     * @return NotNull ArrayList<String>, _EVEN IF_ something went wrong (in which case, you'll get a RuntimeException)
     * @throws RuntimeException typically this should lead to a bug-report
     * @see https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
     */
    public ArrayList<String> toStrings( final ParserRuleContext _prc ) throws RuntimeException {
        final String HDR = HDR0 + ".getEffectiveOption(): ";
        final ArrayList<String> s = new ArrayList<>();
        for ( int ix =0;  ix < _prc.getChildCount(); ix ++ ) {
            final ParseTree ptObj = _prc.getChild( ix );    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTree.html
            s.add( ptObj.getText() );
        }
        return s;
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
     * For parsers that allow OPTIONAL-Cmdline 'arguments' a.k.a. 'OPTION' (example: --verbose) and for parsers that SHOULD be forgiving if user provides MULTIPLE instances of the same 'argument'..<br>
     * this method will return the 'last' a.k.a. the RIGHTMOST occurence of this 'OPTION'.
     * @param _tokenList NotNull
     * @return NotNull String, _EVEN IF_ something went wrong (in which case, you'll get a NoSuchElementException or a RuntimeException)
     * @throws java.util.NoSuchElementException if the _tokenList does Not contain any elements (is empty)
     * @see https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
     */
    public <T extends Token> String getEffectiveTokenOption( final java.util.List<T> _tokenList ) throws java.util.NoSuchElementException {
        final String HDR = HDR0 + ".getEffectiveOption<"+ _tokenList.getClass().getName() +">("+ _tokenList +"): ";
        if ( this.verbose )  _tokenList.forEach( tkn -> System.out.println(tkn.getText()) );

        if ( _tokenList.isEmpty() )
            throw new java.util.NoSuchElementException( "The method's sole-argument passed.. is an empty java.util.List object" );
        final int lastElemIndex = _tokenList.size() - 1;
        final T token = _tokenList.get( lastElemIndex ); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html

        if ( this.verbose ) System.out.println( HDR + "The EFFECTIVE option =["+ token.getText() +"]" );
        return token.getText();
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
     * For parsers that allow OPTIONAL-Cmdline 'arguments' a.k.a. 'OPTION' (example: --verbose) and for parsers that SHOULD be forgiving if user provides MULTIPLE instances of the same 'argument'..<br>
     * this method will return the 'last' a.k.a. the RIGHTMOST occurence of this 'OPTION'.
     * @param _prcList NotNull
     * @return NotNull String, _EVEN IF_ something went wrong (in which case, you'll get a NoSuchElementException or a RuntimeException)
     * @throws java.util.NoSuchElementException if the _prcList does Not contain any elements (is empty)
     * @see https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
     */
    public <T> String getEffectiveOption( final java.util.List<T> _prcList ) throws java.util.NoSuchElementException {
        @SuppressWarnings("unchecked")
        final java.util.List<org.antlr.v4.runtime.ParserRuleContext> prcList = (java.util.List<org.antlr.v4.runtime.ParserRuleContext>) _prcList;
        final String HDR = HDR0 + ".getEffectiveOption<"+ prcList.getClass().getName() +">("+ prcList +")count:"+ prcList.size() +": ";
        if ( this.verbose ) prcList.forEach( item -> System.out.println( HDR + "input is :- "+ this.toStrings(item) ) );
        // does NOT WORK: delims.forEach( d -> System.out.println(d.getText()) );

        // if ( this.verbose ) prcList.forEach( prc -> { final ArrayList<String> sss = this.toStrings( prc ); sss.forEach( System.err::println); } );
        if ( this.verbose ) prcList.forEach( prc -> { this.toStrings( prc ).forEach ( ii -> System.out.print( ii +" " )); System.out.println(); } );

        if ( prcList.isEmpty() )
            throw new java.util.NoSuchElementException( "The method's sole-argument passed.. is an empty java.util.List object" );
        final int lastElemIndex = prcList.size() - 1;
        final ParserRuleContext lastPrc = prcList.get( lastElemIndex ); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html

        // 'lastPrc' should ideally be representing a SINGLETON value.
        // But, per the 'precise definition' of that class, it is capable of having a sequence of values.
        // so, we need to 'loop' thru those values too!

        // final ArrayList<String> sss = this.toStrings(  lastPrc );
        // if ( this.verbose ) System.out.println( HDR + "LIST-YAML's the EFFECTIVE-delim =["+ this.toStrings( delims.get( delims.size()-1 ) ).get(0) +"]" );
        // if ( sss.isEmpty() )
        //     throw new java.util.NoSuchElementException( "Bug Report alert.  The method's sole-argument passed.. is an empty java.util.List object (Internal Detail: toStrings() returned empty.)" );
        // final String s = sss.get( sss.size() - 1 ); // repeat what we did above with prcList
        if ( lastPrc.getChildCount() <= 0 )
            throw new java.util.NoSuchElementException( "Bug Report alert.  The method's sole-argument passed.. is an empty java.util.List object (Internal Detail: toStrings() returned empty.)" );
        final String s = lastPrc.getChild( lastPrc.getChildCount() - 1 ).getText();

        if ( this.verbose ) System.out.println( HDR + "The EFFECTIVE option =["+ s +"]" );
        return s;
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    public static void main(String[] a) {

        // Note: To run this test.. see ${PROJHOME}/bin/test.csh
        // https://www.antlr.org/api/JavaTool/index.html?org/antlr/v4/gui/TestRig.html

        // final String HDR = CLASSNAME + ".main(): ";
        // try {
        //     throw new Exception( "main() is NOT to be used for Testing purposes." );
        // } catch(Exception e) {
        //     e.printStackTrace( System.err );
        //     System.out.println( HDR + e.getMessage() );
        // }
    } // main()

}

//EOF
