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
    public void testParse() throws Exception
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
        validateHunk(hunk1, 30, 9, 30, 9);

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
    public void testDiff2Parse() throws Exception
    {
        // given
        DiffParser parser = new UnifiedDiffParser();
        InputStream in = getClass().getResourceAsStream("git2.diff");

        // when
        List<Diff> diffs = parser.parse(in);

        // then
        assertNotNull(diffs);
        assertEquals(1, diffs.size());

        Diff diff = diffs.get(0);
        assertEquals("a/diffparser/src/main/java/org/wickedsource/diffparser/unified/ParserState.java", diff.getFromFileName());
        assertEquals("b/diffparser/src/main/java/org/wickedsource/diffparser/unified/ParserState.java", diff.getToFileName());
        assertEquals(3, diff.getHunks().size());

        List<String> headerLines = diff.getHeaderLines();
        assertEquals( headerLines.toString(), 2, headerLines.size());

        Hunk hunk1 = diff.getHunks().get(0);
        Hunk hunk2 = diff.getHunks().get(1);
        Hunk hunk3 = diff.getHunks().get(2);

        validateHunk(hunk1, 15, 6, 15, 7);
        validateHunk(hunk2, 88, 11, 89, 13 );
        validateHunk(hunk3, 252, 7, 255, 7 );

        /* Test Hunk 1 lines: */
        List<Line> lines = hunk1.getLines();
        assertEquals(7, lines.size());
        assertEquals(NEUTRAL, lines.get(1).getLineType());
        assertEquals(TO, lines.get(3).getLineType());
        assertEquals(NEUTRAL, lines.get(4).getLineType());

        /* Test Hunk 2 lines: */
        lines = hunk2.getLines();
        assertEquals(14, lines.size());
        assertEquals(TO, lines.get(3).getLineType());
        assertEquals(TO, lines.get(4).getLineType());
        assertEquals(FROM, lines.get(9).getLineType());
        assertEquals(TO, lines.get(10).getLineType());
        assertEquals(NEUTRAL, lines.get(5).getLineType());

        /* Test Hunk 3 lines: */
        lines = hunk3.getLines();
        assertEquals(8, lines.size());
        assertEquals(FROM, lines.get(3).getLineType());
        assertEquals(TO, lines.get(4).getLineType());
        assertEquals(NEUTRAL, lines.get(5).getLineType());
    }

    private void validateHunk(Hunk hunk, int fromLineStart, int fromLineCount, int toLineStart, int toLineCount)
    {
        assertEquals(fromLineStart, hunk.getFromFileRange().getLineStart());
        assertEquals(fromLineCount, hunk.getFromFileRange().getLineCount());
        assertEquals(toLineStart, hunk.getToFileRange().getLineStart());
        assertEquals(toLineCount, hunk.getToFileRange().getLineCount());
    }
}
