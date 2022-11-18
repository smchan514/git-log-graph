package git_log_graph.dot.data;

public class DotArc {
	private String _sourceNodeId;
	private String _targetNodeId;

	public DotArc(String sourceNodeId, String targetNodeId) {
		_sourceNodeId = sourceNodeId;
		_targetNodeId = targetNodeId;
	}

	public String getSourceNodeId() {
		return _sourceNodeId;
	}

	public String getTargetNodeId() {
		return _targetNodeId;
	}

}
