package org.wickedsource.diffparser.unified;

import org.junit.Test;
import org.wickedsource.diffparser.api.DiffParser;
import org.wickedsource.diffparser.api.UnifiedDiffParser;
import org.wickedsource.diffparser.api.model.Diff;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class GitDiffTest
{
    private final Logger log = Logger.getLogger( this.toString() );

    @Test
    public void testParse() throws Exception
    {
        // given
        DiffParser parser = new UnifiedDiffParser();
        InputStream in = getClass().getResourceAsStream("git.diff");

        // when
        List<Diff> diffs = parser.parse(in);

        log.info("Diffs: " + diffs.toString());
    }
}
