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
        if (line.words().size() == 1) {
        String word = line.word();

        List<String> matchingCommands = new ArrayList<>();

        for (String command : new String[]{
            "/newpuzzle", "/basepuzzle", "/guess", "/viewpuzzle", "/foundwords",
            "/shuffle", "/cleansave", "/advancedsave", "/loadpuzzle", "/observestatus", "/help", "/exit"
        }) {
            if (command.startsWith(word)) {
                matchingCommands.add(command);
            }
        }

        for (String matchingCommand : matchingCommands) {
            candidates.add(new Candidate(AttributedString.stripAnsi(matchingCommand), matchingCommand, null, null, null, null, true));
        }
        
        }
    }
}
