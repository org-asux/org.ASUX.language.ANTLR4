package org.ASUX.language.antlr4;

import java.util.ArrayList;

/**
 * All ANTLR4-based Command-parsers within ASUX family, should implement this interface, so that Batch-commands processing can be independent of the implementation-code for the Command-families like YAML, AWS, .. ..
 */
public interface GenericCmdANTLR4Parser {
    ArrayList<org.ASUX.language.antlr4.CmdLineArgs> parseYamlCommandLine(String _cmdLineStr) throws Exception;
}
