package org.ASUX.language.antlr4;

/**
 * This empty-interface is a placeholder, with the __BIG__ assumption that its better that using java.lang.Object as the return value of {@link GenericCmdANTLR4Parser#parseYamlCommandLine(String)}.
 * The Command parsers for YAML, AWS, etc.. will have classes implementing this interface.
 */
public interface CmdLineArgs {


    //=================================================================================
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //=================================================================================

    /**
     * Given the original object of this class, copy these attributes to the 2nd object of this class.  Classes implementing this interface will know what instance-variables to copy.
     * @param _orig a NotNull reference
     * @param _copy a NotNull reference
     */
    public void copyBasicFlags( final CmdLineArgs _orig, final CmdLineArgs _copy );

}
