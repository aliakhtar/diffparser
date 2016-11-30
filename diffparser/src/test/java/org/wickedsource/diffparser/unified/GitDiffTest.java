package org.wickedsource.diffparser.unified;

import org.junit.Test;
import org.wickedsource.diffparser.api.DiffParser;
import org.wickedsource.diffparser.api.UnifiedDiffParser;
import org.wickedsource.diffparser.api.model.Diff;
import org.wickedsource.diffparser.api.model.Hunk;
import org.wickedsource.diffparser.api.model.Line;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.wickedsource.diffparser.api.model.Line.LineType.*;

public class GitDiffTest
{
    private final Logger log = Logger.getLogger( this.toString() );

    @Test
    public void testParse1() throws Exception
    {
        // given
        DiffParser parser = new UnifiedDiffParser();
        InputStream in = getClass().getResourceAsStream("git.diff");

        // when
        List<Diff> diffs = parser.parse(in);

        // then
        assertNotNull(diffs);
        assertEquals(1, diffs.size());

        Diff diff = diffs.get(0);
        assertEquals("a/app/services/contacts_service.rb", diff.getFromFileName());
        assertEquals("b/app/services/contacts_service.rb", diff.getToFileName());
        assertEquals(1, diff.getHunks().size());

        List<String> headerLines = diff.getHeaderLines();
        assertEquals( headerLines.toString(), 2, headerLines.size());

        Hunk hunk1 = diff.getHunks().get(0);
        assertEquals(30, hunk1.getFromFileRange().getLineStart());
        assertEquals(9, hunk1.getFromFileRange().getLineCount());
        assertEquals(30, hunk1.getToFileRange().getLineStart());
        assertEquals(9, hunk1.getToFileRange().getLineCount());

        List<Line> lines = hunk1.getLines();
        assertEquals(10, lines.size());


        assertEquals(FROM, lines.get(3).getLineType());
        assertEquals(TO, lines.get(4).getLineType());

        assertEquals(FROM, lines.get(6).getLineType());
        assertEquals(TO, lines.get(7).getLineType());

        assertEquals(NEUTRAL, lines.get(0).getLineType() );
        assertEquals(NEUTRAL, lines.get(1).getLineType() );
        assertEquals(NEUTRAL, lines.get(2).getLineType() );
        assertEquals(NEUTRAL, lines.get(8).getLineType() );
        assertEquals(NEUTRAL, lines.get(9).getLineType() );
    }

    @Test
    public void testParse2() throws Exception
    {

        // given
        DiffParser parser = new UnifiedDiffParser();
        InputStream in = getClass().getResourceAsStream("git2.diff");

        // when
        List<Diff> diffs = parser.parse(in);

        log.info("Diffs: " + diffs.toString());
    }
}
