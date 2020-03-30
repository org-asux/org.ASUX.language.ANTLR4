package org.ASUX.language.antlr4;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

public class MyYAMLANTLR4Lexer extends YAMLANTLR4Lexer {
    public boolean verbose;

    // !!!!!!!!!!!! ATTENTION !!!!!!!!!!!!!  These two rows below are "noted" in Lexer.g4, which automatically creates these in DefaultLexer.java
    // private String lastTokenString;
    // private int lastTokenID;

    public MyYAMLANTLR4Lexer(final boolean _verbose, final CharStream input) {
        super(input);
        this.verbose = _verbose;
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        e.printStackTrace();
    }

    @Override
    public void recover(RecognitionException re) {
        re.printStackTrace();
        super.recover(re);
    }

    @Override
    public void emit(Token token) {
        super.emit(token);

        // !!!!!!!!!!!! ATTENTION.   _input.LA(-1) will also return Parser Token-IDs, which this Lexer has No idea about.
        // if ( this.verbose ) System.out.print( "\t"+ this.lastTokenID +"("+ _input.LA(-1) +")>>"+ token.getType() +":"+ token.getText() );
        if ( this.verbose ) System.out.print( "\t"+ this.lastTokenID +">>"+ token.getType() +":"+ token.getText() );

        // Now let's set these instance variables, for use in above print(), when this method is invoked the next time.
        this.lastTokenID = token.getType();
        this.lastTokenString = token.getText();
    }

} // class