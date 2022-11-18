package git_log_graph.git_log.data;

import java.util.LinkedList;
import java.util.List;

public class GitCommit {
	private String _commit;
	private String _tree;
	private final List<String> _parents = new LinkedList<>();
	private String _author;
	private String _date;
	private String _commiter;
	private String _message;

	public GitCommit() {
		// ...
	}

	public String getCommit() {
		return _commit;
	}

	public void setCommit(String commit) {
		_commit = commit;
	}

	public String getTree() {
		return _tree;
	}

	public void setTree(String tree) {
		_tree = tree;
	}

	public List<String> getParents() {
		return _parents;
	}

	public void addParent(String parent) {
		_parents.add(parent);
	}

	public String getCommiter() {
		return _commiter;
	}

	public void setCommiter(String commiter) {
		_commiter = commiter;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public String getAuthor() {
		return _author;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public String getDate() {
		return _date;
	}

	public void setDate(String date) {
		_date = date;
	}

	public String getMessage() {
		return _message;
	}

	public void appendMessage(String line) {
		if (_message == null) {
			_message = line;
		} else {
			_message += "\n";
			_message += line;
		}
	}

}
