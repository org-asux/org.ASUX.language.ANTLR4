package org.ASUX.language.antlr4;

// Copied from: https://github.com/antlr/antlr4/blob/master/doc/resources/CaseChangingCharStream.java
// Was: package org.antlr.v4.runtime;

import org.antlr.v4.runtime.*;      // https://www.antlr.org/api/Java/org/antlr/v4/runtime/package-summary.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/CommonTokenStream.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/Token.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/ParserRuleContext.html
                                    // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/ParseTree.html
// import org.antlr.v4.runtime.tree.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/TerminalNode.html
// import org.antlr.v4.runtime.misc.*; // https://www.antlr.org/api/Java/org/antlr/v4/runtime/UnbufferedCharStream.html
import org.antlr.v4.runtime.misc.Interval;

/**
 * This class supports case-insensitive lexing by wrapping an existing
 * <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html">https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html</a> and forcing the lexer to see either upper or
 * lowercase characters. Grammar literals should then be either upper or
 * lower case such as 'BEGIN' or 'begin'. The text of the character
 * stream is unaffected. Example: input 'BeGiN' would match lexer rule
 * 'BEGIN' if constructor parameter upper=true but getText() would return
 * 'BeGiN'.
 */
public class CaseChangingCharStream implements CharStream {

	final CharStream stream;
	final boolean upper;

	/**
	 * Constructs a new CaseChangingCharStream wrapping the given <a href="https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html">https://www.antlr.org/api/Java/org/antlr/v4/runtime/CharStreams.html</a> forcing
	 * all characters to upper case or lower case.
	 * @param stream The stream to wrap.
	 * @param upper If true force each symbol to upper case, otherwise force to lower.
	 */
	public CaseChangingCharStream(CharStream stream, boolean upper) {
		this.stream = stream;
		this.upper = upper;
	}

	/**
     * {@inheritDoc}
	 */
	@Override
	public String getText(Interval interval) {
		return stream.getText(interval);
	}

	/**
     * {@inheritDoc}
	 */
	@Override
	public void consume() {
		stream.consume();
	}

	/**
     * {@inheritDoc}
	 */
	@Override
	public int LA(int i) {
		int c = stream.LA(i);
		if (c <= 0) {
			return c;
		}
		if (upper) {
			return Character.toUpperCase(c);
		}
		return Character.toLowerCase(c);
	}

	@Override
	public int mark() {
		return stream.mark();
	}

	@Override
	public void release(int marker) {
		stream.release(marker);
	}

	@Override
	public int index() {
		return stream.index();
	}

	/**
     * {@inheritDoc}
	 */
	@Override
	public void seek(int index) {
		stream.seek(index);
	}

	@Override
	public int size() {
		return stream.size();
	}

	/**
     * {@inheritDoc}
	 */
	@Override
	public String getSourceName() {
		return stream.getSourceName();
	}
}

//EOF
