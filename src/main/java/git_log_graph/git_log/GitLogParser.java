package git_log_graph.git_log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import git_log_graph.git_log.data.GitCommit;

/**
 * Git raw log parser
 * 
 * <PRE>
 * git log --format=raw > git-`git rev-parse HEAD`.log
 * </PRE>
 * 
 * <PRE>
 * commit 67d20631b0628617413665d447cc1eccc6dcafaa
 * tree cc39f47c2421fcc7bba95c914b1455334ec3b87c
 * parent 85fedfc92c601bb972b6cc9169c9d6d27093b12b
 * author smchan514 <smchan514@github.com> 1631313869 -0400
 * committer smchan514 <smchan514@github.com> 1631313869 -0400
 * 
 *     Fixed backend filtering
 *     
 *     Task #85626: Update List
 * 
 * commit f3ccd7eb811630cdbc84e8f7d40beafdbcd60e44
 * tree 75ac1f3b3bb057e0e4a846986c75295e9bb176f5
 * parent 08ab1c9b2840bdc944fd86062f2366a8ad65194d
 * parent 5f5cf2c283eaf6bb4e1478cf03507dca493c997a
 * author smchan514 <smchan514@github.com> 1631309093 -0500
 * committer smchan514 <smchan514@github.com> 1631309093 -0500
 * 
 *    Merge branch 'feature/65306-display-list' into feature/65306-display-list
 * 
 * </PRE>
 */
public class GitLogParser {

	private static final String HEADER_AUTHOR = "author";
	private static final String HEADER_COMMITTER = "committer";
	private static final String HEADER_PARENT = "parent";
	private static final String HEADER_TREE = "tree";
	private static final String HEADER_COMMIT = "commit";
    private static final String HEADER_GPGSIG = "gpgsig";

	private EnumParserState _parserState = EnumParserState.WAITING_FOR_NEW_COMMIT;
	private GitCommit _currCommit;
	private LinkedList<GitCommit> _list;
	private final SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private int _lineNumber;

	public GitLogParser() {
		// ...
	}

	public List<GitCommit> parseGitLog(String path) throws IOException {
		try (FileReader fr = new FileReader(path)) {
			BufferedReader br = new BufferedReader(fr);
			String line;

			initProcess();
            _lineNumber = 0;
			while ((line = br.readLine()) != null) {
                ++_lineNumber;
				processLine(line);
			}
			endProcess();

			return _list;
		}
	}

	private void initProcess() {
		_list = new LinkedList<GitCommit>();
		_currCommit = null;
	}

	private void endProcess() {
		if (_currCommit != null) {
			_list.add(_currCommit);
			_currCommit = null;
		}
	}

	private void processLine(String line) {
		switch (_parserState) {
		case WAITING_FOR_NEW_COMMIT:
			processLine_WAITING_FOR_NEW_COMMIT(line);
			break;
		case WAITING_FOR_MESSAGE:
			processLine_WAITING_FOR_MESSAGE(line);
			break;
		case READING_MESSAGE:
			processLine_READING_MESSAGE(line);
			break;
        case READING_GPGSIG:
            processLine_READING_GPGSIG(line);
            break;
		default:
			throw new RuntimeException("Unkonwn state: " + _parserState);
		}
	}

	private void processLine_WAITING_FOR_NEW_COMMIT(String line) {
		if (!line.startsWith(HEADER_COMMIT)) {
			throw new RuntimeException("Expecting commit hash but got " + line);
		}

		_currCommit = new GitCommit();
		String[] splits = splitHeader(line);
		_currCommit.setCommit(splits[1]);

		// Read other commit metadata until the message starts
		_parserState = EnumParserState.WAITING_FOR_MESSAGE;
	}

	private void processLine_WAITING_FOR_MESSAGE(String line) {
		if (line.length() == 0) {
			// Empty line indicates start of message
			_parserState = EnumParserState.READING_MESSAGE;
		} else {
			String[] splits = splitHeader(line);

			if (splits[0].equals(HEADER_AUTHOR)) {
				_currCommit.setAuthor(splits[1]);
				_currCommit.setDate(extractDate(splits[1]));
			} else if (splits[0].equals(HEADER_COMMITTER)) {
				_currCommit.setCommiter(splits[1]);
			} else if (splits[0].equals(HEADER_PARENT)) {
				_currCommit.addParent(splits[1]);
			} else if (splits[0].equals(HEADER_TREE)) {
				_currCommit.setTree(splits[1]);
            } else if (splits[0].equals(HEADER_GPGSIG)) {
                _parserState = EnumParserState.READING_GPGSIG;
			} else {
                throw new RuntimeException("Unknown block header: '" + line + "' at line " + _lineNumber);
			}
		}
	}

	/**
     * Extract date from "smchan514 <smchan514@github.com> 1631313869 -0400"
     */
	private String extractDate(String str) {
		String[] splits = str.split(" ");
		int timeoffset = convertTimezoneOffsetMillis(splits[splits.length - 1]);
		long timestamp = Long.parseLong(splits[splits.length - 2]) * 1000;

		TimeZone tz = new RawTimeZone(timeoffset);
		_sdf.setTimeZone(tz);
		return _sdf.format(new Date(timestamp));
	}

	/**
	 * Convert "-0400" as "+hhmm" into milliseconds
	 */
	private int convertTimezoneOffsetMillis(String str) {
		int offset = Integer.parseInt(str);
		int sign = offset >= 0 ? 1 : -1;
		int hours = Math.abs(offset) / 100;
		int minutes = Math.abs(offset) % 100;

		return sign * ((hours * 60) + minutes) * 60 * 1000;
	}

	/**
	 * Split "commit f3ccd7eb811630cdbc84e8f7d40beafdbcd60e44"
	 */
	private String[] splitHeader(String line) {
		// Split to at most two items
		return line.split(" ", 2);
	}

    private void processLine_READING_MESSAGE(String line) {
        if (line.length() == 0) {
            // Empty line indicates end of message
            _list.add(_currCommit);
            _currCommit = null;
            _parserState = EnumParserState.WAITING_FOR_NEW_COMMIT;
        } else {
            // Remove the leading 4 spaces
            line = line.substring(4);
            _currCommit.appendMessage(line);
        }
    }

    private void processLine_READING_GPGSIG(String line) {
        if (line.length() == 0) {
            // Empty line indicates start of message
            // Assuming GPGSIG is the last header
            _parserState = EnumParserState.READING_MESSAGE;
		} else {
            // Otherwise keep reading
		}
	}
}
