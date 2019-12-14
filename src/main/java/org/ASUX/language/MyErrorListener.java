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

import java.util.regex.*;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.antlr.v4.runtime.*;      // https://www.antlr.org/api/Java/org/antlr/v4/runtime/package-summary.html
import org.antlr.v4.runtime.tree.*; 
import org.antlr.v4.runtime.misc.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/UnbufferedCharStream.html

import static org.junit.Assert.*;

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

    // private String _symbol = "";
    // private StringWriter _stream;
    // public String getSymbol()       { return _symbol; } 
    // public StringWriter getStream() { return _stream; } 

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    // public MyErrorListener(StringWriter stream) {
    //     this._stream = stream;
    // }

    //==============================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //==============================================================================

    /** Standard template code to use with any Language/Lexer/Parser */
    @Override    
	public void syntaxError( Recognizer<?, ?> recognizer,
							Object offendingSymbol,
							int line,
							int charPositionInLine,
							String msg,
							RecognitionException e)
	{
        final String HDR = HDR0 + ".testYAMLReadCommand(): ";

        System.out.println( HDR + msg );
        // this._stream.write( msg );
        // this._stream.write( System.getProperty("line.separator") );            

        if ( offendingSymbol.getClass().getName() == "org.antlr.v4.runtime.CommonToken" ) {            
            CommonToken token = (CommonToken) offendingSymbol;
            // this._symbol = token.getText();            
            System.out.println( HDR + "token = "+ token.getText() );
        }                
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
