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
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.*;
// import static org.junit.Assert.*; // OLD JUnit compatible.

import org.ASUX.language.antlr4.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

//==============================================================================
// https://www.antlr.org/api/Java/org/antlr/v4/runtime/package-summary.html
// https://www.antlr.org/api/Java/org/antlr/v4/runtime/CommonTokenStream.html
// https://www.antlr.org/api/Java/org/antlr/v4/runtime/Token.html
// https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
// https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTree.html
// Known Implementing Classes: ParserRuleContext, RuleContext, RuleContextWithAltNum, TerminalNodeImpl, ErrorNodeImpl, InterpreterRuleContext
// All Known Subinterfaces: ErrorNode, RuleNode, TerminalNode

// https://github.com/antlr/antlr4/blob/master/doc/tree-matching.md

// import org.antlr.v4.runtime.misc.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/UnbufferedCharStream.html
// import org.antlr.v4.runtime.tree.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
// import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;          // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/pattern/ParseTreePattern.html
// import org.antlr.v4.runtime.tree.pattern.ParseTreePatternMatcher;   // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/pattern/ParseTreePatternMatcher.html
// import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;            // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/pattern/ParseTreeMatch.html
// import org.antlr.v4.runtime.tree.xpath.XPath; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/xpath/XPath.html

//==============================================================================
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//==============================================================================

// @TestMethodOrder(MethodOrderer.Alphanumeric.class)
// @TestMethodOrder(MethodOrderer.Random.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestYAMLANTLR4 {

    private static final String HDR0 = TestYAMLANTLR4.class.getName();
    private static final String TEST_INPUT_YAML_COMMANDS = "src/test/resources/user-input-YAML_commands.txt";
    private static final String TEST_INPUT_DEFECTIVE_YAML_COMMANDS = "src/test/resources/user-defective-YAML_commands.txt";

    public boolean verbose = true;

    // Used by JUnit's Jupiter's "Assumptions".  See AssumeTrue() usage below.
    private static boolean bNoTestFailedSoFar = true;

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    @AfterEach
    void tearDown() {
        System.out.println("________________________________________________________________________________________________________");
    }
    
    @AfterAll
    static void shutdownTesting() {
        // log.info("@AfterAll - executed after all test methods.");
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    // public static class MyParser extends YAMLANTLR4Parser {
    //     private static final String HDR0 = TestYAMLANTLR4.class.getName();

    //     /**
    //      * Super class has no default constructor
    //      * @param input see {@link org.ASUX.language.YAMLANTLR4Parser}
    //     */
    //     public MyParser( TokenStream input ) {
    //         super(input);
    //     }

    //     /** Always called by generated parsers upon entry to a rule. */
    //     @Override
    //     public void enterRule( ParserRuleContext localctx, int state, int ruleIndex ) {
    //         final String HDR = HDR0 + ".enterRule(): ";
    //         super.enterRule( localctx, state, ruleIndex );
    //         System.out.println( HDR + "rule-index="+ ruleIndex + " localCtx="+ localctx );
    //     }
    // }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    @Test @Order(1)
    @DisplayName("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Sunny-Day Scenario: testYAMLCommands @@@@@@@@@@@@@@@@@@@@@@@@@@@@")
    public void testYAMLCommands() throws Exception {
        final String HDR = HDR0 + ".testYAMLCommands():\t";

        // JUnit's Jupiter's Assumptions are used to run tests only if certain conditions are met. 
        assumeTrue( TestYAMLANTLR4.bNoTestFailedSoFar );

        try {
            // final String yamlCmdStr = "yaml read '/reg/\"exp\"/g' < /file/name > path.to/file";
            // CharStream inputStream = CharStreams.fromString( yamlCmdStr );   // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html
            // DEPRECATED: ANTLRInputStream inputStream = new ANTLRInputStream( yamlCmdStr ); 
            final CharStream inputStream = CharStreams.fromFileName( TEST_INPUT_YAML_COMMANDS ); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html
// yaml read '/reg/"exp"/g' --verbose --delimiter "/" --showStats -v < /file/name > path.to/file ;
// yaml list '/2reg/expresssion' --delimiter "JUNKSTR" -d "/" --yamllibrary SnakeYAML --single-quote -i /file/name --output

            //==============================================================================
            // final YAMLANTLR4Lexer defaultLexer = new YAMLANTLR4Lexer( inputStream );
            // Do NOT use the DefaultLexer anymore..
            final MyYAMLANTLR4Lexer myLexer = new MyYAMLANTLR4Lexer( this.verbose,  inputStream );
            final CommonTokenStream commonTokenStream = new CommonTokenStream( myLexer );

            // Looking to print/dump the Stream of tokens already parsed successfully?
            // Its automatically done by MyYAMLANTLR4Lexer.java's emit(Token t) method.  Just set verbose == true.

            // Configure parser and appropriate Listeners and ErrorListeners
            if ( this.verbose ) System.out.println( HDR + "init parser" );
            final YAMLANTLR4Parser defaultParser = new YAMLANTLR4Parser( commonTokenStream );

            //==============================================================================
            // Error Listeners - one each for Lexer and Parser
            // Write your lexer such that a syntax error is impossible!!
            // In ANTLR 4, it is easy to do this by simply adding the following as the last rule of your lexer:
            //      ErrorChar : . ;
            // By doing this, __ALL__ errors are moved from the lexer to the parser.
            // I've NOT implemented this approach.
            // See HOWTO Details @ https://stackoverflow.com/questions/18782388/antlr4-lexer-error-reporting-length-of-offending-characters

            // 1st for Lexical Error handling
            defaultParser.setErrorHandler( new BailErrorStrategy() );
            // With BailErrorStrategy, at the first ___Lexical___ Error, both parser & lexer stop.
            // Example Output seen on error:
            //      line 1:10 no viable alternative at input '--sfakdhasdf'
            //      org.antlr.v4.runtime.misc.ParseCancellationException

            // 2nd for Parser-Errors
            // defaultParser.removeErrorListeners(); // to remove the default listeners for Parser Errors
            final MyErrorListener errorListener = new MyErrorListener( this.verbose, defaultParser.getParseListeners() ); // This is __MY OWN__  Java-class to listen to errors.
// !!!!!!!!!!! Attention !!!!!!!!!! Somehow.. the line-below 'defaultParser.addErrorListener(..)' causes even basic Lexer errors.
            // defaultParser.addErrorListener( errorListener ); // pass in any sub-class of BaseErrorListener
// !!!!!!!!!!! Attention !!!!!!!!!! Somehow.. the above 'defaultParser.addErrorListener(..)' causes even basic Lexer errors.
            //     // void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
            //     // Upon syntax error, notify any interested parties.

            //==============================================================================
            // Start parsing
            if ( this.verbose ) System.out.println( HDR + "about to parse" );
            YAMLANTLR4Parser.Yaml_commandsContext topmostCtx = defaultParser.yaml_commands();  // if the grammer/scenario restricted user-input to JUST 1 command ONLY.
            final java.util.List<YAMLANTLR4Parser.Yaml_commandContext> cmdsCtx = topmostCtx.yaml_command();

            if ( this.verbose ) System.out.println( "\n" ); // because My-OWN-Lexer will be dumping tokens separated by Tabs

            //==============================================================================
            final YAMLANTLR4ParserUtils util = new YAMLANTLR4ParserUtils( this.verbose );

            //==============================================================================
            // !!! ALERT !!! EITHER - OR !!!
            // EITHER use Listener/Visitor classses - OR - use FOR-Loop below.
            // If you invoke 'addParseListener()' or 'visit()' .. the FOR Loop will _ONLY_ see the last line-of-input
            // !!!!!!!!!!!!!!!!!!! ALERT !!!!!!!!!!!!!!!!!!!!!

            // NOTE: The "typical" listener should be defined _BEFORE_ invoking 'defaultParser.yaml_command()'
            // final MyYAMLParserListener myListener = new MyYAMLParserListener( this.verbose );
            // defaultParser.addParseListener( myListener ); // This Listener is automatically generated by ANTLR4

            //-----------------------------
            // // Both Visitor & Listener will be invoked _WHILE_ the "Tree" is build by the 'defaultParser.yaml_command()'
            // if ( this.verbose ) System.out.println( HDR + "about to engage VISITOR class to walk thru the parsed 'parser-TREE'" );
            // // VISITOR PATTERN.  Will trigger invocation of YAMLANTLR4ParserVisitor.visitContent()
            // MyYAMLParserVisitor myVisitor =  new MyYAMLParserVisitor( this.verbose ); // Default: YAMLANTLR4ParserBaseVisitor<String> defaultVisitor =  new YAMLANTLR4ParserBaseVisitor<String>();
            // myVisitor.visit( topmostCtx );

            //==============================================================================
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //==============================================================================

            // for debugging purposes
            int lineNum = 0;
            int tokenNum = 0;

            for (YAMLANTLR4Parser.Yaml_commandContext eachCmdCtx: cmdsCtx ) {
                lineNum ++;

                // JUnit's Jupiter's Assumptions are used to run tests only if certain conditions are met.
                if ( this.verbose ) System.out.println( HDR + " TestYAMLANTLR4.bNoTestFailedSoFar = "+ TestYAMLANTLR4.bNoTestFailedSoFar );
                assumeTrue( TestYAMLANTLR4.bNoTestFailedSoFar );
                // above throws the Exception:-  org.junit.AssumptionViolatedException: got: <false>, expected: is <true>
                if ( this.verbose ) System.out.println( HDR + " errorListener.detectedSyntaxError() = "+ errorListener.detectedSyntaxError() );
                assumeFalse( errorListener.detectedSyntaxError() );
                // above throws the Exception:-  org.junit.AssumptionViolatedException: got: <true>, expected: is <false>
                // if ( TestYAMLANTLR4.bNoTestFailedSoFar || errorListener.detectedSyntaxError() ) {
                //     System.err.println( HDR + "!!!!!!!!!!!!!!!!!!!!!!!!!! SKIPPING this Test method !!!!!!!!!!!!!!!!!!!!!!!!!!" );
                //     break; // !!!!!!!!!!!!! ATTENTION:  Breaking the loop !!!!!!!!!!!!!!!!!
                // }
                // if ( ! TestYAMLANTLR4.bNoTestFailedSoFar ) {
                //     System.err.println( HDR + "!!!!!!!!!!!!!!!!!!!!!!!!!! SKIPPING this Test method !!!!!!!!!!!!!!!!!!!!!!!!!!" );
                //     break; // !!!!!!!!!!!!! ATTENTION:  Breaking the loop !!!!!!!!!!!!!!!!!
                // }
                // if ( errorListener.detectedSyntaxError() ) {
                //     System.err.println( HDR + "................. Ending this Test method (due to ERROR/detectedSyntaxError) ......................" );
                //     break; // !!!!!!!!!!!!! ATTENTION:  Breaking the loop !!!!!!!!!!!!!!!!!
                // }

                final YAMLANTLR4Parser.Yaml_command_readContext readCtx = eachCmdCtx.yaml_command_read();
                if ( readCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml READ command detected!" );
                    if ( this.verbose ) System.out.println( HDR + "about to run LEXER's ASSERT-checks (tokenNum="+ tokenNum +")." );

                    final int regExpPos = ( lineNum == 1 ) ? tokenNum + 2 : tokenNum + 5 ;
                    if ( lineNum == 1 ) { // 1st test-input LINE only
                        assertEquals(YAMLANTLR4Lexer.YAML,              commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.YAML_READ,         commonTokenStream.get(tokenNum++).getType());
                        // assertTrue( YAMLANTLR4Lexer.REG ULAR EXP RESSION == commonTokenStream.get( tokenNum++ ).getType() ); // no longer have such a token.  Instead it's a parser element.
                        assertEquals(YAMLANTLR4Lexer.SINGLEDOUBLEQUOTEDTEXT, commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.PROJECT,           commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.ANYWORD,           commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.VERBOSE,           commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.DELIMITER_OPT,     commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.COMMA,             commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.SHOWSTATS,         commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.VERBOSE,           commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.INPUT_FROM,        commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.FILEPATH,          commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.OUTPUT_TO,         commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.FILEPATH,          commonTokenStream.get(tokenNum++).getType());
                        assertEquals(YAMLANTLR4Lexer.SEMICOLON,         commonTokenStream.get(tokenNum++).getType());
                        // assertTrue( YAMLANTLR4Lexer.NEWLINE    ==    commonTokenStream.get( tokenNum++ ).getType() ); // Grammer/Lexer rules updated to IGNORE all WhiteSpace, incl. NEWLINE
                    }
                    if ( lineNum == 2 ) tokenNum += 14;

                    // if ( this.verbose ) System.out.println( HDR + "readCtx ="+ readCtx ); // USELESS DEBUG STATEMENT.  You'll see something like: [33 26]
                    final YAMLANTLR4Parser.RegularexpressionContext regExpCtx = readCtx.regularexpression();
                    final String regExpStr = commonTokenStream.get( regExpPos ).getText();
                    // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's REGEXPstring(from Lexer) =["+ regExpStr +"]"+ regExpCtx );
                    // final String s = util.getRegExpAsString( regExpCtx );
                    final ArrayList<String> sss = util.toStrings( regExpCtx );
                    // assertFalse( sss.isEmpty() );
                    assertEquals(1, sss.size());
                    final String s = sss.get( 0 );
                    // if ( this.verbose ) System.out.println( HDR + "Read-YAML's REGEXPstring(from Parser) =["+ s +"] " );
                    assertEquals( s, regExpStr );
                    if ( lineNum == 1 ) assertEquals("'/reg/\"exp\"/g'", s); // 1st line in TEST INPUT-FILE
                    if ( lineNum == 2 ) assertEquals("paths,/pet", s);       // 1st line in TEST INPUT-FILE

                    final java.util.List<YAMLANTLR4Parser.OptionalsContext> optionalsCtxSet = readCtx.optionals();
                    final ArrayList<String> sss22 = util.toStrings( optionalsCtxSet );
                    if ( lineNum == 1 ) assertEquals(5, sss22.size());
                    if ( lineNum == 2 ) assertEquals(6, sss22.size());
                    if (this.verbose) {
                        System.out.println(HDR + "\tRead-YAML's OPTIONALs:- ");
                        sss22.forEach(System.out::println);
                    }

                    String delimiterFound = "Undefined";
                    for( YAMLANTLR4Parser.OptionalsContext optionalsCtx : optionalsCtxSet ) {

                        // Here in READYAML, only one delimiter-option is provided.
                        // But, below in LIST-YAML, multiple instance of the '--delimiter' option entered by User.
                        final java.util.List<YAMLANTLR4Parser.Delimiter_textContext> delims = optionalsCtx.delimiter;
                        if ( this.verbose ) System.out.print( HDR + "\tRead-YAML's Delimiters:- " );
                        // does NOT WORK: delims.forEach( d -> System.out.println(d.getText()) );
                        delims.forEach( delim -> { final ArrayList<String> sss33 = util.toStrings( delim ); sss33.forEach( System.err::println); } );
                        try {
                            delimiterFound = util.getEffectiveOption(optionalsCtx.delimiter);
                            if ( this.verbose ) System.out.println( HDR + "Read-YAML's Effective-Delimiter = ["+ delimiterFound +"]" );
                        } catch( java.util.NoSuchElementException e ) {
                            if ( this.verbose ) System.out.println( HDR + "Read-YAML's Effective-Delimiter Not in this.  Perhaps another one in this optionalsCtxSet(SET)." );
                            // continue; // maybe delimiter is defined another 'optionalsCtx' instance within this 'optionalsCtxSet'(SET)
                        }
                    }
                    Assertions.assertEquals(",", delimiterFound );
                    // assertTrue( "\"/\"".equals(delimiterFound) );

                    //=================================================================
// // REF: https://github.com/antlr/antlr4/blob/master/doc/tree-matching.md
// // DID NOT WORK.  I'm not sure about the format of 1st argument and NOT sure of what to put into 2nd argument.
// // My Educated Guess.  2nd parameter (Rule Definition) is MUCH MORE than what is in argument 1.  Basically, argument 1 must be a _FULL_ match of the Rule.
// // The "tags" INSIDE the angle-brackets represent either token or rule references in the associated grammar.
//                     // The preferred method of getting a tree pattern.
//                     // SIGNATURE: ParseTreePattern <variab> = defaultParser.compileParseTreePattern( String pattern, int patternRuleIndex );
//                     final ParseTreePattern ptPatt11 = defaultParser.compileParseTreePattern( "<DELIMITER_OPT> <any_quoted_text>", YAMLANTLR4Parser.RULE_optionals );
//                     // !!! ATTENTION !!! above '<DELIMITER_OPT>' is the actual value of the 1st argument !!!!!!!
//                     final ParseTreeMatch matcher11  = ptPatt11.match( topmostCtx );
//                     final ParseTreeMatch matcher11b = ptPatt11.match( optionalsCtx );
//                     if ( this.verbose ) System.out.println( HDR +"#0(matcher11.succeeded) matcher11="+ matcher11  +" matcher11bbbb="+ matcher11b );
// // aaaarrrrrggggghhhhh!!! Both matcher11 & matcher11bbb fail!
//                     if ( matcher11.succeeded() ) {
//                         final org.antlr.v4.runtime.tree.ParseTree delim11 = matcher11.get( "any_quoted_text" );
//                         // ParseTreeMatch has methods SIMILAR to a java.util.Map interface (for get() and put())
//                         if ( this.verbose ) System.out.println( HDR + "RULE_yaml_commands for <DELIMITER_OPT><any_quoted_text>: "+ delim11.getText() );
//                     }

// // REF: https://github.com/antlr/antlr4/blob/master/doc/tree-matching.md
// // DID NOT WORK.  I'm not sure about the format of 1st argument and NOT sure of what to put into 2nd argument.
// // My Educated Guess.  2nd parameter (Rule Definition) is MUCH MORE than what is in argument 1.  Basically, argument 1 must be a _FULL_ match of the Rule.
// // The "tags" INSIDE the angle-brackets represent either token or rule references in the associated grammar.
//                     final ParseTreePattern ptPatt22 = defaultParser.compileParseTreePattern( "<VERBOSE>", YAMLANTLR4Parser.RULE_optionals );
//                     // !!! ATTENTION !!! above '<VERBOSE>' is the actual value of the 1st argument !!!!!!!
//                     final ParseTreeMatch matcher22  = ptPatt22.match( topmostCtx );   // <<-- RETURN is a SCALAR-value (Unlike 'findAll()' )
// // aaaarrrrrggggghhhhh!!! matcher22 fails!
//                     final org.antlr.v4.runtime.misc.MultiMap<String,ParseTree> mmap = matcher22.getLabels();
//                     final java.util.List< org.antlr.v4.runtime.misc.Pair<String,ParseTree> > lst22 = mmap.getPairs();
//                     if ( this.verbose ) System.out.println( HDR + "#1(getLabels&MultiMap) <VERBOSE> >> has "+ lst22.size() +" elements." );
//                     if ( this.verbose ) lst22.forEach( pair -> System.out.println( HDR + "#0(getLabels&MultiMap) <VERBOSE> >> "+ pair.a +"="+ pair.b ) );

// // REF: https://github.com/antlr/antlr4/blob/master/doc/tree-matching.md
// // DID NOT WORK.  I'm not sure about the format of 1st argument and NOT sure of what to put into 2nd argument.
// // My Educated Guess.  2nd parameter (Rule Definition) is MUCH MORE than what is in argument 1.  Basically, argument 1 must be a _FULL_ match of the Rule.
// // The "tags" INSIDE the angle-brackets represent either token or rule references in the associated grammar.
//                     // final ParseTreePattern ptPatt99 = defaultParser.compileParseTreePattern( "<DELIMITER_OPT> <any_quoted_text>", YAMLANTLR4Parser.RULE_yaml_commands);
//                     // final java.util.List<ParseTreeMatch> matches99 = ptPatt99.findAll( topmostCtx, "//yaml_command/*/optionals" ); // <<-- !!!! RETURN is a List<>
//                     // if ( this.verbose ) matches99.forEach( ptree -> System.out.println( HDR + "#1(matcher) //yaml_command/*/optionals >> "+ ptree ) );

                    //=================================================================
// !!!!!!!!!!!!! ATTENTION !!!!!!!!!!!!!!!!!!!!!
// The XPath will MATCH __ALL__ the YAML commands in the file!!!!!
                    final java.util.Collection<ParseTree> xpathQRes = org.antlr.v4.runtime.tree.xpath.XPath.findAll( topmostCtx, "//yaml_command/*/optionals", defaultParser );
                    if ( this.verbose ) xpathQRes.forEach( ptree -> System.out.println( HDR + "#2(XPath.findAll) //yaml_command/*/optionals >> "+ ptree.getText() ) );
                    // XPATH-EXAMPLE = //'return'   :means: any 'return' literal in tree
                    // XPATH-EXAMPLE = /prog/func/!'myvalue'     :means: any literal other than 'myvalue'.. under func, which is under prog.
                    java.util.Iterator<ParseTree> iterator = xpathQRes.iterator();
                    String usersInput;
                    while ( iterator.hasNext() ) { // see output of this While Loop at the end of this block!!!!!!!!!!!!!!!!!!!!!!!!!
                        usersInput = iterator.next().getText();
                        if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [" + usersInput + "]");
                        if ( usersInput != null && usersInput.trim().length() != 0 ) {
                            assertTrue( "--verbose--delimiter,--showStats-v".equals( usersInput )
                                    || "--verbose--yamllibrarySnakeYAML".equals( usersInput )
                                    || "--verbose--delimiter,".equals( usersInput )
                                    || "--delimiter\"JUNKSTR\"-d\"/\"--yamllibrarySnakeYAML'".equals( usersInput )
                                    ); // <<----------- !!!!!!!!!!!!!!!!  The test !!!!!!!!!!!!
// !!!!!!!!!!!!! Attention: the above asse rtTrue() must address __ALL__ the input lines (even tho' we're iterating over each line.  Reason: XPATH does Not work that way!!!
                        } // if
                    } // while
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [--verbose--delimiter"/"--showStats-v]
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [--delimiter"JUNKSTR"-d"/"--yamllibrarySnakeYAML']
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = []
// org.ASUX.language.TestYAMLANTLR4.testYAMLCommands():	Accurately verified code-exception-logic - that Read-CMd has NO YAML-Implementation cmdline options


                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [" + usersInput + "]");
                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [" + usersInput + "]");
                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [" + usersInput + "]");
                    // assertTrue( "--verbose--delimiter\"/\"--showStats-v".equals( usersInput ) ); // <<----------- !!!!!!!!!!!!!!!!  The test !!!!!!!!!!!!
                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(verbose/delimiter/showStats): usersInput identified as = [" + usersInput + "]");
                    //
                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(delimiter/JUNKSTR/yamllibrarySnakeYAML): usersInput identified as = [" + usersInput + "]");
                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(delimiter/JUNKSTR/yamllibrarySnakeYAML): usersInput identified as = [" + usersInput + "]");
                    // iterator.hasNext();
                    // usersInput = iterator.next().getText();
                    // if (this.verbose)  System.out.println(HDR + "org.antlr.v4.runtime.tree.xpath.XPath.findAll(delimiter/JUNKSTR/yamllibrarySnakeYAML): usersInput identified as = [" + usersInput + "]");
                    //
                    // assertTrue( "--delimiter\"JUNKSTR\"-d\"/\"--yamllibrarySnakeYAML'".equals( usersInput ) ); // <<----------- !!!!!!!!!!!!!!!!  The test !!!!!!!!!!!!

                    //=================================================================
                    try {
                        for( YAMLANTLR4Parser.OptionalsContext optionalsCtx : optionalsCtxSet ) {
                            final java.util.List<Token> yamlImps = optionalsCtx.yamlImplementation;
                            // final java.util.List<TerminalNode> yamlImps = optionalsCtx.YAMLLIBRARY_OPT();
                            if (!yamlImps.isEmpty() && this.verbose)
                                System.out.print(HDR + "Read-YAML's yamlImplementation:- ");
                            yamlImps.forEach(tk -> System.out.println(tk.getText()));
                            // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
                            if (this.verbose)
                                System.out.println(HDR + "Read-YAML's Effective-YAML-Implementation = [" + util.getEffectiveTokenOption(optionalsCtx.yamlImplementation) + "]");
                        } // for loop
                    } catch ( java.util.NoSuchElementException e ) {
                        if ( e.getMessage().equals("The method's sole-argument passed.. is an empty java.util.List object") )
                            System.out.println( HDR + "Accurately verified code-exception-logic - that Read-CMd has NO YAML-Implementation cmdline options" );
                        else
                            fail(); // Test failed.
                    }

                    boolean bShowStatsCmdLineOptionFound = false;
                    int verboseCmdLineOptionCount = 0;
                    for( YAMLANTLR4Parser.OptionalsContext optionalsCtx : optionalsCtxSet ) {
                        final java.util.List<TerminalNode> showStatsOpt = optionalsCtx.SHOWSTATS();
                        bShowStatsCmdLineOptionFound = bShowStatsCmdLineOptionFound || (showStatsOpt.size() > 0);
                        if (this.verbose) System.out.println(HDR + "Read-YAML's show-Statistics = [" + bShowStatsCmdLineOptionFound + "]");

                        final java.util.List<TerminalNode> verboseOpt = optionalsCtx.VERBOSE();
                        verboseCmdLineOptionCount +=  verboseOpt.size();
                        if (this.verbose) System.out.println(HDR + "Read-YAML's verbose-Count = [" + verboseCmdLineOptionCount + "]");
                    }
                    assertEquals(2, verboseCmdLineOptionCount );
                    if ( lineNum == 1 ) assertTrue( bShowStatsCmdLineOptionFound );

                    // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
                    final String inputSrc   = readCtx.inputSrc.getText();   // commonTokenStream.get( regExpPos + ?? ).getText();
                    final String outputSink = readCtx.outputSink.getText(); // commonTokenStream.get( regExpPos + ?? ).getText();
                    if ( this.verbose ) System.out.println( HDR + "Read-YAML's InputSOURCE =["+ inputSrc +"] OutputSink=["+ outputSink +"]" );
                    if ( lineNum == 1 ) assertTrue( "/file/name".equals(inputSrc) );
                    if ( lineNum == 1 ) assertTrue( "path.to/file".equals(outputSink) );
                    if ( lineNum == 2 ) assertTrue( "inputs/nano.yaml".equals(inputSrc) );
                    if ( lineNum == 2 ) assertTrue( "-".equals(outputSink) );

                    if ( this.verbose ) System.out.println( HDR + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! tokenNum = "+ tokenNum );
                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }

                final YAMLANTLR4Parser.Yaml_command_listContext listCtx = eachCmdCtx.yaml_command_list();
                if ( listCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + "yaml LIST command detected! tokenNum = "+ tokenNum );
                    assertEquals(29, tokenNum);  // this YAML_LIST command .. follows the 10 tokens for YAML_READ
                    if ( this.verbose ) System.out.println( HDR + "about to run LEXER's ASSERT-checks" );
                    assertEquals(YAMLANTLR4Lexer.YAML,              commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.YAML_LIST,         commonTokenStream.get(tokenNum++).getType());
                    final int regExpPos = tokenNum;
                    assertEquals(YAMLANTLR4Lexer.NONQUOTEDTEXT,     commonTokenStream.get(tokenNum++).getType()); // Note the difference in TOKEN-name here vs. the one 9 lines above.
                    assertEquals(YAMLANTLR4Lexer.ANYWORD,           commonTokenStream.get(tokenNum++).getType()); // 2nd fragment of the REGULAR-EXPRESSION
                    assertEquals(YAMLANTLR4Lexer.NONQUOTEDTEXT,     commonTokenStream.get(tokenNum++).getType()); // 2nd fragment of the REGULAR-EXPRESSION
                    assertEquals(YAMLANTLR4Lexer.NONQUOTEDTEXT,     commonTokenStream.get(tokenNum++).getType()); // 2nd fragment of the REGULAR-EXPRESSION
                    assertEquals(YAMLANTLR4Lexer.ANYWORD,           commonTokenStream.get(tokenNum++).getType()); // 2nd fragment of the REGULAR-EXPRESSION
                    assertEquals(YAMLANTLR4Lexer.INDEX_EXPR,        commonTokenStream.get(tokenNum++).getType()); // 2nd fragment of the REGULAR-EXPRESSION
                    assertEquals(YAMLANTLR4Lexer.ANYWORD,           commonTokenStream.get(tokenNum++).getType()); // 2nd fragment of the REGULAR-EXPRESSION
                    assertEquals(YAMLANTLR4Lexer.DELIMITER_OPT,     commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.DOUBLEQUOTEDTEXT,  commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.DELIMITER_OPT,     commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.DOUBLEQUOTEDTEXT,  commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.YAMLLIBRARY_OPT,   commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.YAMLLIBRARY_LIST,  commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.QUOTEYAMLCONTENT,  commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.INPUT_FROM,        commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.FILEPATH,          commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.OUTPUT_TO,         commonTokenStream.get(tokenNum++).getType());
                    assertEquals(YAMLANTLR4Lexer.FILEPATH,          commonTokenStream.get(tokenNum++).getType());
                    // assertEquals(YAMLANTLR4Lexer.SEMICOLON,         commonTokenStream.get(tokenNum++).getType());
                    // assertTrue( YAMLANTLR4Lexer.NEWLINE    == commonTokenStream.get( tokenNum++ ).getType() ); // Grammer/Lexer rules updated to IGNORE all WhiteSpace, incl. NEWLINE
                    // if ( this.verbose ) System.out.println( HDR + "listCtx ="+ listCtx ); // USELESS DEBUG STATEMENT.  You'll see something like: [34 26]
                    final YAMLANTLR4Parser.RegularexpressionContext regExpCtx = listCtx.regularexpression();
                    String regExpStr = ""; // concatenate the 6 pieces of the REGULAR-EXPRESSION into a single string
                    for ( int ix = 0; ix < 7; ix ++ ) {
                        regExpStr += commonTokenStream.get( regExpPos + ix ).getText();
                    }
                    // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's REGEXPstring(from Lexer) =["+ regExpStr +"] "+ regExpCtx );
                    String s = ""; // util.getRegExpAsString( regExpCtx ); // I had to keep updating this 'regExpAsString' everytime I changed the Parser definition
                    for( String is: util.toStrings( regExpCtx ) ) {
                        s += is;
                    }
                    // if ( this.verbose ) System.out.println( HDR + "LIST-YAML's REGEXPstring(from Parser) =["+ s +"] " );
                    Assertions.assertEquals(s, regExpStr);
                    Assertions.assertEquals("*,**,[1],schema", s);

                    final java.util.List<YAMLANTLR4Parser.OptionalsContext> optionalsCtxSet = listCtx.optionals();
                    final ArrayList<String> sss22 = util.toStrings( optionalsCtxSet );
                    assertEquals(7, sss22.size());
                    if ( this.verbose ) {
                        System.out.println( HDR + "\tLIST-YAML's OPTIONALs:- " );
                        sss22.forEach(System.out::println);
                    }

                    String effectiveDelimiter = null;
                    int numOfDelimitersFound = 0;
                    String yamlImplFound = null;
                    int numOfYamlLibrariesFound = 0;
                    String yamlQuoteChar = null;

                    for( YAMLANTLR4Parser.OptionalsContext optionalsCtx : optionalsCtxSet ) {

                        // --delimiter 'JunkString' -d ","
                        // Unlike YamlQUoteChar (below), the PARSER-grammer __Collects__ every instance of the '--delimiter' option entered by User.
                        // Flexibility for use means a COMPLEX grammer.
                        final java.util.List<YAMLANTLR4Parser.Delimiter_textContext> delims = optionalsCtx.delimiter;

                        if (this.verbose) {
                            System.out.print(HDR + "\tLIST-YAML's Delimiters:- ");
                            // does NOT WORK: delims.forEach( d -> System.out.println(d.getText()) );
                            delims.forEach(delim -> {
                                final ArrayList<String> sss33 = util.toStrings(delim);
                                sss33.forEach(System.err::println);
                            });
                        }

                        if ( delims.size() > 0 ) {
                            final String ed = util.getEffectiveOption( delims );
                            if (this.verbose) System.out.println(HDR + "LIST-YAML's Delimiter in this instance = [" + ed + "]");
                            numOfDelimitersFound += delims.size();
                            final ArrayList<String> sss33 = util.toStrings(delims);
                            effectiveDelimiter = sss33.get( sss33.size() - 1 );
                        } else {
                            if ( this.verbose ) System.out.println( HDR + "LIST-YAML's Effective-Delimiter Not in this.  Perhaps another one in this optionalsCtxSet(SET)." );
                            continue; // maybe delimiter is defined another 'optionalsCtx' instance within this 'optionalsCtxSet'(SET)
                        }
                        // if ( this.verbose ) System.out.println( HDR + "LIST-YAML's the EFFECTIVE-delim =["+ util.toStrings( delims.get( delims.size()-1 ) ).get(0) +"]" );

                        //------------------------------------------------
                        // --yamllibrary SnakeYAML --yamllibrary NodeImpl
                        // Unlike YamlQUoteChar (below), the PARSER-grammer __Collects__ every instance of the '--yamllibrary' option entered by User.
                        // Flexibility for use means a COMPLEX grammer.
                        final java.util.List<Token> yamlImps = optionalsCtx.yamlImplementation;
                        numOfYamlLibrariesFound += yamlImps.size();
                        // final java.util.List<TerminalNode> yamlImps = optionalsCtx.YAMLLIBRARY_OPT();
                        if (!yamlImps.isEmpty() && this.verbose) {
                            System.out.print(HDR + "LIST-YAML's yamlImplementation:- ");
                            yamlImps.forEach(tk -> System.out.println(tk.getText()));
                        } else {
                            if (this.verbose) System.out.print(HDR + "LIST-YAML's yamlImplementation NOT present in this instance within optionalsCtxSet(SET)");
                        }
                        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
                        try {
                            yamlImplFound = util.getEffectiveTokenOption(optionalsCtx.yamlImplementation);
                            if ( this.verbose ) System.out.println( HDR + "LIST-YAML's Effective-YAML-Implementation = ["+ yamlImplFound +"]" );
                        } catch( java.util.NoSuchElementException e ) {
                            if ( this.verbose ) System.out.println( HDR + "LIST-YAML's Effective-Delimiter Not in this.  Perhaps another one in this optionalsCtxSet(SET)." );
                        }

                        final Token yamlQuoteCharToken = optionalsCtx.yamlQuoteChar;
                        if (this.verbose) System.out.println(HDR + "LIST-YAML's Effective-QuotingChar = [" + yamlQuoteCharToken.getText() + "]");
                        yamlQuoteChar = yamlQuoteCharToken.getText();
                        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree

                        final java.util.List<TerminalNode> showStatsOpt = optionalsCtx.SHOWSTATS();
                        if (this.verbose)  System.out.println(HDR + "LIST-YAML's show-Statistics = [" + (showStatsOpt.size() > 0) + "]");
                        assertEquals(0, showStatsOpt.size());

                        final java.util.List<TerminalNode> verboseOpt = optionalsCtx.VERBOSE();
                        if (this.verbose) System.out.println(HDR + "List-YAML's verbose-Count = [" + verboseOpt.size() + "]");
                        assertEquals(0, verboseOpt.size());

                    } // for loop

                    if (this.verbose) System.out.println(HDR + "LIST-YAML's EFFECTIVE Delimiter = [" + effectiveDelimiter + "]");
                    assertEquals(2, numOfDelimitersFound );
                    assertTrue( "\"/\"".equals( effectiveDelimiter ));
                    if (this.verbose) System.out.println(HDR + "LIST-YAML's numOfYamlLibrariesFound = [" + numOfYamlLibrariesFound + "]");
                    assertEquals(1, numOfYamlLibrariesFound );
                    if (this.verbose) System.out.println(HDR + "LIST-YAML's EFFECTIVE yamlQuoteChar = [" + yamlQuoteChar + "]");
                    assertTrue("'".equals( yamlQuoteChar ) );

                    final String inputSrc   = listCtx.inputSrc.getText();   // commonTokenStream.get( regExpPos + ?? ).getText();
                    final String outputSink = listCtx.outputSink.getText(); // commonTokenStream.get( regExpPos + ?? ).getText();
                    if ( this.verbose ) System.out.println( HDR + "LIST-YAML's InputSOURCE =["+ inputSrc +"] OutputSink=["+ outputSink +"]" );
                    assertTrue( "/file/name".equals(inputSrc) );
                    assertTrue( "path.to/file".equals(outputSink) );

                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                } // if ( listCtx != null )

                final YAMLANTLR4Parser.Yaml_command_tableContext tblCtx = eachCmdCtx.yaml_command_table();
                if ( tblCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + "yaml TABLE command detected!" );
                    // assertTrue( tokenNum == 23 ); // this YAML_TABLE command .. follows the 8 tokens for YAML_READ + 8 for YAML_LIST
                    // if ( this.verbose ) System.out.println( HDR + "about to run LEXER's ASSERT-checks" );
                    // assertTrue( YAMLANTLR4Lexer.YAML          == commonTokenStream.get( tokenNum++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.YAML_TABLE    == commonTokenStream.get( tokenNum++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.SEMICOLON     == commonTokenStream.get( tokenNum++ ).getType() );
                    // // assertTrue( YAMLANTLR4Lexer.NEWLINE    == commonTokenStream.get( tokenNum++ ).getType() );
                    // assertTrue( YAMLANTLR4Lexer.EOF           == commonTokenStream.get( tokenNum++ ).getType() );
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

                if ( this.verbose ) System.out.println( HDR + "!!!!!!!!!!!! oh! oh! oh! oh! __UNKNOWN__ command detected!" );

            } // for loop

            // Uncomment the following assertEquals() ONLY AFTER uncommenting the line above for: 'defaultParser.addParseListener( .. )'
            assertTrue( "".equals(errorListener.getOffendingSymbol()) || "null".equals(errorListener.getOffendingSymbol()) || null == errorListener.getOffendingSymbol()  );

        } catch( AssertionError | Exception e ) { // AssertionError includes sub-class org.opentest4j.AssertionFailedError
            e.printStackTrace( System.err );
            System.err.println( HDR + e.getMessage() );
            TestYAMLANTLR4.bNoTestFailedSoFar = false;
            throw e;
        }

        // defectiveYAMLCommands();

    } // testYAMLReadCommand()

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    @Test @Order(2)
    @DisplayName("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ EXCEPTION-Scenario 1: testDefectiveYAMLCommands @@@@@@@@@@@@@@@@@@@@@@@@@@@@")
    public void testDefectiveYAMLCommands() throws Exception {
    // public void defectiveYAMLCommands() {
        final String HDR = HDR0 + ".testDefectiveYAMLCommands(): ";

        // JUnit's Jupiter's Assumptions are used to run tests only if certain conditions are met. 
        assumeTrue( TestYAMLANTLR4.bNoTestFailedSoFar );

        try {

            // System.out.println( HDR + "about to SLEEP" );
            // Thread.sleep(10000);
            // System.out.println( HDR + "Awake" );

            final CharStream inputStream = CharStreams.fromFileName( TEST_INPUT_DEFECTIVE_YAML_COMMANDS ); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html
// yaml read '/reg/"exp"/g' -verbose --SHOWStats -v --delimiter "JUNKSTR" -d "/" --yamllibrary SnakeYAML --single-quote -i /file/name --output path.to/file ;

            final YAMLANTLR4Lexer defaultLexer = new YAMLANTLR4Lexer( inputStream );
            final CommonTokenStream commonTokenStream = new CommonTokenStream( defaultLexer );

            if ( this.verbose ) System.out.println( HDR + "about to parse" );
            final YAMLANTLR4Parser defaultParser = new YAMLANTLR4Parser( commonTokenStream );
            // final YAMLANTLR4ParserUtils util = new YAMLANTLR4ParserUtils( this.verbose );

            //==============================================================================
            // NOTE: The "typical" listener should be defined _BEFORE_ invoking 'defaultParser.yaml_command()'
            final MyYAMLParserListener myListener = new MyYAMLParserListener( this.verbose );
            defaultParser.addParseListener( myListener ); // This Listener is automatically generated by ANTLR4
            // Error Listener
            final MyErrorListener errorListener = new MyErrorListener( this.verbose, defaultParser.getParseListeners() );
            defaultParser.addErrorListener( errorListener );  // This is __MY OWN__  Java-class to listen to errors.
                // void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
                // Upon syntax error, notify any interested parties.

            //==============================================================================
            // Start parsing
            // YAMLANTLR4Parser.Yaml_commandContext context = defaultParser.yaml_command();  // if the grammer/scenario restricted user-input to JUST 1 command ONLY.
            final java.util.List<YAMLANTLR4Parser.Yaml_commandContext> cmdsCtx = defaultParser.yaml_commands().yaml_command();

            //==============================================================================
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //==============================================================================

            for (YAMLANTLR4Parser.Yaml_commandContext eachCmdCtx: cmdsCtx ) {

                // JUnit's Jupiter's Assumptions are used to run tests only if certain conditions are met. 
                if ( this.verbose ) System.out.println( HDR + " TestYAMLANTLR4.bNoTestFailedSoFar = "+ TestYAMLANTLR4.bNoTestFailedSoFar );
                // assumeTrue( TestYAMLANTLR4.bNoTestFailedSoFar );
                // above throws the Exception:-  org.junit.AssumptionViolatedException: got: <false>, expected: is <true>
                if ( this.verbose ) System.out.println( HDR + " errorListener.detectedSyntaxError() = "+ errorListener.detectedSyntaxError() );
                // assumeFalse( errorListener.detectedSyntaxError() );
                // above throws the Exception:-  org.junit.AssumptionViolatedException: got: <true>, expected: is <false>
                if ( ! TestYAMLANTLR4.bNoTestFailedSoFar ) {
                    System.err.println( HDR + "!!!!!!!!!!!!!!!!!!!!!!!!!! SKIPPING this Test method !!!!!!!!!!!!!!!!!!!!!!!!!!" );
                    break; // !!!!!!!!!!!!! ATTENTION:  Breaking the loop !!!!!!!!!!!!!!!!!
                }
                if ( errorListener.detectedSyntaxError() ) {
                    System.err.println( HDR + "................. Ending this Test method (due to ERROR/detectedSyntaxError) ......................" );
                    break; // !!!!!!!!!!!!! ATTENTION:  Breaking the loop !!!!!!!!!!!!!!!!!
                }

                // since this test is about defective tokens entered by users.. we should NOT be getting here.
                fail(); // test failed

                final YAMLANTLR4Parser.Yaml_command_readContext readCtx = eachCmdCtx.yaml_command_read();
                if ( readCtx != null ) {
                    if ( this.verbose ) System.out.println( HDR + " yaml READ command detected!" );

                    continue; // !!!!!!!!!!!!!!!!! VERY IMPORTANT !!!!!!!!!!!!!!!!  .. .. as we are UNABLE to rely on a SWITCH-statement.
                }

                if ( this.verbose ) System.out.println( HDR + "!!!!!!!!!!!! oh! oh! oh! oh! __UNKNOWN__ command detected!" );

            } // for loop

            // Uncomment the following assertEquals() ONLY AFTER uncommenting the line above for: 'defaultParser.addParseListener( .. )'
            assertEquals( "-verbose", errorListener.getOffendingSymbol() );

        // } catch( java.lang.InterruptedException e ) {
        //     e.printStackTrace( System.err );
        //     System.err.println( HDR + e.getMessage() );
        } catch( AssertionError | Exception e ) { // AssertionError includes sub-class org.opentest4j.AssertionFailedError
            e.printStackTrace( System.err );
            System.err.println( HDR + e.getMessage() );
            TestYAMLANTLR4.bNoTestFailedSoFar = false;
            throw e;
        }

    } // testYAMLReadCommand()

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

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
