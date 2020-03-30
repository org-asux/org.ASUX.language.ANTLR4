package org.ASUX.language.antlr4;

// https://github.com/antlr/antlr4/blob/master/runtime/Java/src/org/antlr/v4/runtime/BailErrorStrategy.java
// https://stackoverflow.com/questions/38855673/how-to-get-antlr-4-to-exit-upon-first-lexing-or-parsing-error
/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;

/**
 * This implementation of {@link ANTLRErrorStrategy} responds to syntax errors
 * by immediately canceling the parse operation with a
 * {@link ParseCancellationException}. The implementation ensures that the
 * {@link ParserRuleContext#exception} field is set for all parse tree nodes
 * that were not completed prior to encountering the error.
 *
 * <p>
 * This error strategy is useful in the following scenarios.</p>
 *
 * <ul>
 * <li><strong>Two-stage parsing:</strong> This error strategy allows the first
 * stage of two-stage parsing to immediately terminate if an error is
 * encountered, and immediately fall back to the second stage. In addition to
 * avoiding wasted work by attempting to recover from errors here, the empty
 * implementation of {@link BailErrorStrategy#sync} improves the performance of
 * the first stage.</li>
 * <li><strong>Silent validation:</strong> When syntax errors are not being
 * reported or logged, and the parse result is simply ignored if errors occur,
 * the {@link BailErrorStrategy} avoids wasting work on recovering from errors
 * when the result will be ignored either way.</li>
 * </ul>
 *
 * <p>
 * {@code myparser.setErrorHandler(new BailErrorStrategy());}</p>
 *
 * @see Parser#setErrorHandler(ANTLRErrorStrategy)
 */
public class BailErrorStrategy extends DefaultErrorStrategy {
    final String CLASSNAME = BailErrorStrategy.class.getName();

    /** Instead of recovering from exception {@code e}, re-throw it wrapped
     *  in a {@link ParseCancellationException} so it is not caught by the
     *  rule function catches.  Use {@link Exception#getCause()} to get the
     *  original {@link RecognitionException}.
     */
    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        final String HDR = CLASSNAME + ":recover(Parser recognizer, RecognitionException e): ";
        final YAMLANTLR4ParserUtils util = new YAMLANTLR4ParserUtils( false );

        //----------------------
        System.err.println(HDR + "recognizer.getContext() = " );
        ArrayList<String> sss = util.toStrings(recognizer.getContext());
        sss.forEach( (s) -> System.out.println("\t\t"+s) );

        e.printStackTrace( System.err );

        //----------------------
        for (ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent()) {
            context.exception = e;
        }

        throw new ParseCancellationException(e);
    }

    /** Make sure we don't attempt to recover inline; if the parser
     *  successfully recovers, it won't throw an exception.
     */
    @Override
    public Token recoverInline(Parser recognizer)
            throws RecognitionException
    {
        final String HDR = CLASSNAME + ":recoverInline(Parser recognizer):  ";
        final YAMLANTLR4ParserUtils util = new YAMLANTLR4ParserUtils( false );

        InputMismatchException e = new InputMismatchException(recognizer);
        ParserRuleContext topmost = recognizer.getContext();
        while ( topmost != null ) {
            topmost.exception = e;topmost = topmost;
            topmost = topmost.getParent(); // keep going up.
        }

        //----------------------
        System.err.println();
        System.err.println( HDR + "recognizer.getContext() = " );
        ArrayList<String> sss = util.toStrings(recognizer.getContext());
        sss.forEach( (s) -> System.out.println("\t\t"+s) );
        System.err.println(HDR + "topmost.getContext() = " );
        sss = util.toStrings(topmost);
        System.err.println(HDR + "topmost-Context = " );

        e.printStackTrace( System.err );

        //----------------------
        throw new ParseCancellationException(e);
    }

    /** Make sure we don't attempt to recover from problems in subrules. */
    @Override
    public void sync(Parser recognizer) { }
}

//EOF