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

import org.antlr.v4.runtime.*;      // https://www.antlr.org/api/Java/org/antlr/v4/runtime/package-summary.html
import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.util.List;


//==============================================================================
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//==============================================================================

/** Generic class to help me handle the Errors in Input, for any Language (a.k.a. Lexer or Parser configuration).
 * The parent class is defined at https://www.antlr.org/api/Java/org/antlr/v4/runtime/BaseErrorListener.html
 */
public class MyErrorListener extends BaseErrorListener // org.antlr.v4.runtime.BaseErrorListener
{
    private static final String HDR0 = MyErrorListener.class.getName();
    public static final String CLASSNAME = HDR0;

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================
    
    public boolean verbose = true;

    private final List<ParseTreeListener> listeners;
    private boolean bSyntaxError;
    private String offendingSymbol = null;
    // private StringWriter _stream;
    
    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    public MyErrorListener(   final boolean _verbose
                            , final List<ParseTreeListener> _listeners
                            // , final StringWriter _stream
                            ) {
        this.verbose = _verbose;
        this.listeners = _listeners;
        this.bSyntaxError = false;
        this.offendingSymbol = null;
        //     this._stream = stream;
    }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    public boolean detectedSyntaxError()   { return this.bSyntaxError; } 
    public String getOffendingSymbol()     { return this.offendingSymbol; } 
    // public StringWriter getStream() { return _stream; } 

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /** 
     * {@inheritDoc}
     * Standard template code to use with any Language/Lexer/Parser
     */
    @Override    
	public void syntaxError( Recognizer<?, ?> _recognizer,
							Object _offendingSymbol,
							int _line,
							int _charPositionInLine,
							String _msg,
							RecognitionException _e )
	{
        final String HDR = HDR0 + ".syntaxError(): ";

        this.bSyntaxError = true;

        final String typeOfOffendingSymbol = _offendingSymbol.getClass().getName();
        if ( this.verbose ) System.out.println( HDR + "_offendingSymbol's type = ["+ typeOfOffendingSymbol +"]" );


    // if ( typeOfOffendingSymbol == "org.antlr.v4.runtime.CommonToken" ) {
        if ( _offendingSymbol instanceof CommonToken ) {
            final CommonToken token = (CommonToken) _offendingSymbol;
            // token.getText();      // see https://github.com/antlr/antlr4/blob/master/doc/faq/parse-trees.md#how-do-i-get-the-input-text-for-a-parse-tree-subtree
            final String tokensSuccessfullyParsedSoFar = "<incomplete-code>"; // myParseListener.getTokensSuccessfullyParsedSoFar();
            System.err.println( "Error: Unexpected '"+ token.getText() +"' noted after: "+ tokensSuccessfullyParsedSoFar );
            this.offendingSymbol = token.getText();
        // } else if ( _offendingSymbol instanceof ParserRuleContext ) {
        //     final YAMLANTL4ParserUtils util = new YAMLANTL4ParserUtils( this.verbose );
        //     this.offendingSymbol = util.toString( (ParserRuleContext)_offendingSymbol );
        } else {
            this.offendingSymbol = _offendingSymbol.toString();
            System.err.println( HDR +"Error (internal-failure) !!!!!!!!!!!!!!: incomplete code.  Unable to handle type:"+ typeOfOffendingSymbol );
        }
        
        // For sophisticated scenarios, see TIPs on how to figure out the right content for the "error-message" (to show to the user)
        // https://stackoverflow.com/questions/21613421/how-to-implement-error-handling-in-antlr4/21615751#21615751

        System.err.println( "\t" + _msg );
        // this._stream.write( _msg );
        // this._stream.write( System.getProperty("line.separator") );

    };
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
