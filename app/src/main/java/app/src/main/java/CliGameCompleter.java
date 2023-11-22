package app.src.main.java;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.ParsedLine;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.List;

public class CliGameCompleter implements Completer {
    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        String word = line.word();

        List<String> matchingCommands = new ArrayList<>();

        for (String command : new String[] {
                "/newpuzzle", "/basepuzzle", "/guess", "/viewpuzzle", "/foundwords",

                "/shuffle", "/cleansave", "/advancedsave", "/matrixhints", "/loadpuzzle", "/observestatus", "/help",
                "/exit", "/topscores"

        }) {
            if (command.startsWith(word)) {
                matchingCommands.add(command);
            }
        }

        if (matchingCommands.size() == 1) {
            // If there's only one match, add it to candidates
            candidates.add(new Candidate(AttributedString.stripAnsi(matchingCommands.get(0)), matchingCommands.get(0),
                    null, null, null, null, true));
        }
        // You can add further logic here if you want to handle multiple matches
        // differently.
        // For example, do not add any candidates or handle them in a specific way.
    }
}
