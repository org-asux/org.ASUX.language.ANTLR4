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

import org.antlr.v4.runtime.tree.*;

/**
 * The 'base' class generated by ANTLR has do-nothing methods.  This class overrides methods, to invoke logic when the appropriate YAML command is detected (fully).
 */
public class MyYAMLParserListener extends YAMLANTLR4ParserBaseListener {

    private static final String HDR0 = MyYAMLParserListener.class.getName();
    public static final String CLASSNAME = HDR0;

    public boolean verbose = false;

    public final StringBuffer tokensSuccessfullyParsedSoFar;
    
    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================
    
    public MyYAMLParserListener( final boolean _verbose ) {
        this.verbose = _verbose;
        this.tokensSuccessfullyParsedSoFar = new StringBuffer();
    }
    
    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
     * <p>For use by {@link MyErrorListener}, so that .. when user-input fails parser/lexer, we can accurately point out to the user.. how far the parsing/lexing was successful, so that the user can easily locate the offending "token"</p> 
     * <p> A good example: instead of double-hyphen '--verbose', if the user mistyped '-verbose', then just putting a pointer at it, wont make it obvious to user.</p>
     * @return String NotNull (at a minimum it will be empty-string)
     */
    public String getTokensSuccessfullyParsedSoFar() {
        return this.tokensSuccessfullyParsedSoFar.toString();
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

	// /**
	//  * {@inheritDoc}
	//  */
    // @Override
    // public void enterEveryRule(ParserRuleContext ctx) {
    //     // !!!!!!!!!!!!!!! ATTENTION !!!!!!!!!!!! The System.out.println() below prints __EMPTY__   __ALWAYS__ (as the parser/lexer rule has NOT executed yet)
    //     // final String HDR = HDR0 + ".ENTEREveryRule(): ";
    //     // final String s = ctx.getText();
    //     // if ( this.verbose ) System.out.println( HDR + " ["+ s +"]" );
    // }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    // /**
	//  * {@inheritDoc}
	//  */
    // @Override
    // public void exitEveryRule(ParserRuleContext ctx) {
    //     // !!!!!!!!!!!!!!! ATTENTION !!!!!!!!!!!! This ONLY fires for PARSER Tokens.
    //     // !!!!!!!!!!!!!!! ATTENTION !!!!!!!!!!!! This is NOT invoked for LEXER-Tokens.
    //     // final String HDR = HDR0 + ".EXXXXXXXXXITEveryRule(): ";
    //     // final String s = ctx.getText();
    //     // if ( this.verbose ) System.out.println( HDR + " ["+ s +"]" );
    // }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
	 * {@inheritDoc}
	 */
    @Override
    public void visitTerminal(TerminalNode node) {
        final String HDR = HDR0 + ".visitTerminal(_______________): ";
        final String s = node.getText();
        // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
        if ( this.verbose ) System.out.println( HDR + " ["+ s +"]" );
        this.tokensSuccessfullyParsedSoFar.append(" ").append(s);
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /**
	 * {@inheritDoc}
     * This method is invoked for the 1st as well as ALL the subsequent CORRECT / UNEXPECTED / ERROR lexer-token or parser-token !!!<br>
     * This method is to allow you to STILL take action on subsequent tokens (for whatever you can salvage)
	 */
    @Override
    public void visitErrorNode(ErrorNode node) {
        // final String HDR = HDR0 + ".visitERRORNode(!!!!!!!!!): ";
        // final String s = node.getText();
        // // see how to call getText() correctly @ https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
        // if ( this.verbose ) System.out.println( HDR + " ["+ s +"]" );
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================



    public static void main(String[] a) {
        // final String HDR = CLASSNAME + ".main(): ";
        // try {
        //     throw new Exception( "main() is NOT to be used for Testing purposes." );
        // } catch(Exception e) {
        //     e.printStackTrace( System.err );
        //     System.err.println( HDR + e.getMessage() );
        // }
    } // main()

}

//EOF
