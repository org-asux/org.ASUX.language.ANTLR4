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
     * Given a different  object of this class, copy it's attributes into this object.  Classes implementing this interface will know what instance-variables to copy.
     * @param _orig a NotNull reference
     */
    public void copyBasicFlags( final CmdLineArgs _orig );

}
