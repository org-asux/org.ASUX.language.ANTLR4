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

package org.ASUX.language.antlr4;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.*;      // https://www.antlr.org/api/Java/org/antlr/v4/runtime/package-summary.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CommonTokenStream.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/Token.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTree.html
import org.antlr.v4.runtime.tree.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html


public class YAMLANTLR4ParserUtils {

    private static final String HDR0 = YAMLANTLR4ParserUtils.class.getName();
    public static final String CLASSNAME = HDR0;

    public boolean verbose = true;

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    public YAMLANTLR4ParserUtils( final boolean _verbose ) {
        this.verbose = _verbose;
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

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
    public String getRegExpAsString( final YAMLANTLR4Parser.RegularexpressionContext _regExpCtx) throws RuntimeException {
        final String HDR = HDR0 + ".getRegExpAsString(): ";
        //  Possibility #1: FILEPATH (LEXER Token)
        final org.antlr.v4.runtime.tree.TerminalNode fp = _regExpCtx.FILEPATH(); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
        if ( fp != null ) return fp.getText();
        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree

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
            // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
        }

        //  Possibility #3: NONQUOTEDTEXT (LEXER Token)
        final org.antlr.v4.runtime.tree.TerminalNode nqt = _regExpCtx.NONQUOTEDTEXT(); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
        if ( nqt != null ) return nqt.getText();
        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree

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
     * This method is a generic way to get the Stringified Lexer-token.</p>
     * <p>See <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html">https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html</a></p>
     * <p>See also {@link #toStrings(List)}</p>
     * @param _prc NotNull instance obtained from top-level context object.
     * @return NotNull ArrayList&lt;String&gt;, _EVEN IF_ something went wrong (in which case, you'll get a RuntimeException)
     * @throws RuntimeException typically this should lead to a bug-report
     */
    public ArrayList<String> toStrings( final ParserRuleContext _prc ) throws RuntimeException {
        final String HDR = HDR0 + ".toStrings(): ";
        final ArrayList<String> s = new ArrayList<>();
        for ( int ix =0;  ix < _prc.getChildCount(); ix ++ ) {
            final ParseTree ptObj = _prc.getChild( ix );    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTree.html
            s.add( ptObj.getText() );
        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
    }
        return s;
    }

    //==============================================================================

    /**
     * <code>class RegularexpressionContext extends ParserRuleContext</code><br>
     * <code>class OptionalsContext extends ParserRuleContext</code><br>
     * <p>Many of my ASUX-YAML Parser constructs have heirarchy of parser-tokens (before we get to the Lexer-Tokens).  Almost of my parser-constructs match just one Lexer-token, just that we do Not know which one.<br>
     * This method is a generic way to get the Stringified form of a List-of-Lexer-tokens.</p>
     * <p>See also {@link #toStrings(ParserRuleContext)}</p>
     * @param _prc NotNull instance obtained from top-level context object.
     * @return NotNull ArrayList&lt;String&gt;, _EVEN IF_ something went wrong (in which case, you'll get a RuntimeException)
     * @throws RuntimeException typically this should lead to a bug-report
     */
    public <T extends ParserRuleContext> ArrayList<String> toStrings( final java.util.List<T> _prc ) throws RuntimeException {
        final ArrayList<String> s = new ArrayList<>();
        for ( T ix: _prc ) {
            s.addAll( this.toStrings( ix ) );
        }
        return s;
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
     * For parsers that allow OPTIONAL-Cmdline 'arguments' a.k.a. 'OPTION' (example: --verbose) and for parsers that SHOULD be forgiving if user provides MULTIPLE instances of the same 'argument'..<br>
     * this method will return the 'last' a.k.a. the RIGHTMOST occurence of this 'OPTION'.<br>
     * See <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html">https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html</a>
     * @param <T> subclasses of https://www.antlr.org/api/Java/org/antlr/v4/runtime/Token.html
     * @param _tokenList NotNull
     * @return NotNull String, _EVEN IF_ something went wrong (in which case, you'll get a NoSuchElementException or a RuntimeException)
     * @throws java.util.NoSuchElementException if the _tokenList does Not contain any elements (is empty)
     */
    public <T extends Token> String getEffectiveTokenOption( final java.util.List<T> _tokenList ) throws java.util.NoSuchElementException {
        final String HDR = HDR0 + ".getEffectiveTokenOption<"+ _tokenList.getClass().getName() +">("+ _tokenList +"): ";
        if ( this.verbose )  _tokenList.forEach( tkn -> System.out.println(tkn.getText()) );

        if ( _tokenList.isEmpty() )
            throw new java.util.NoSuchElementException( "The method's sole-argument passed.. is an empty java.util.List object" );
        final int lastElemIndex = _tokenList.size() - 1;
        final T token = _tokenList.get( lastElemIndex ); // https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html

        if ( this.verbose ) System.out.println( HDR + "The EFFECTIVE option =["+ token.getText() +"]" );
        return token.getText();
        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
     * For parsers that allow OPTIONAL-Cmdline 'arguments' a.k.a. 'OPTION' (example: --verbose) and for parsers that SHOULD be forgiving if user provides MULTIPLE instances of the same 'argument'..<br>
     * this method will return the 'last' a.k.a. the RIGHTMOST occurence of this 'OPTION'.<br>
     * See <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html">https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html</a>
     * @param <T> Every Parser "token" will have a context, that will be sub-class of https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
     * @param _prcList NotNull
     * @return NotNull String, _EVEN IF_ something went wrong (in which case, you'll get a NoSuchElementException or a RuntimeException)
     * @throws java.util.NoSuchElementException if the _prcList does Not contain any elements (is empty)
     */
    public <T extends org.antlr.v4.runtime.ParserRuleContext>
        String getEffectiveOption( final java.util.List<T> _prcList ) throws java.util.NoSuchElementException
    {
        // ATTENTION: THe "HDR" variable is defined _AFTER_ the input parameter is force-casted to the 'parent-class' org.antlr.v4.runtime.ParserRuleContext
        @SuppressWarnings("unchecked")
        final java.util.List<org.antlr.v4.runtime.ParserRuleContext> prcList = (java.util.List<org.antlr.v4.runtime.ParserRuleContext>) _prcList;

        final String HDR = HDR0 + ".getEffectiveOption<"+ prcList.getClass().getName() +">:count="+ prcList.size() +"("+ prcList +"): ";

        if ( this.verbose ) prcList.forEach( item -> System.out.println( HDR + "user inputted the option:- "+ this.toStrings(item) ) );
        // does NOT WORK: prcList.forEach( d -> System.out.println(d.getText()) );

        // if ( this.verbose ) prcList.forEach( prc -> { final ArrayList<String> sss = this.toStrings( prc ); sss.forEach( System.err::println); } );
        // if ( this.verbose ) prcList.forEach( prc -> { this.toStrings( prc ).forEach ( ii -> System.out.print( ii +" " )); System.out.println(); } );

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
        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree

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
