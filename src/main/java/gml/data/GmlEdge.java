package gml.data;

public class GmlEdge {

	private final int _edgeId;
	private GmlNode _sourceNode;
	private GmlNode _targetNode;

	public GmlEdge(int edgeId) {
		_edgeId = edgeId;
	}

	public int getEdgeId() {
		return _edgeId;
	}

	public GmlNode getSourceNode() {
		return _sourceNode;
	}

	public void setSourceNode(GmlNode sourceNode) {
		assert sourceNode != null;
		_sourceNode = sourceNode;
	}

	public GmlNode getTargetNode() {
		return _targetNode;
	}

	public void setTargetNode(GmlNode targetNode) {
		assert targetNode != null;
		_targetNode = targetNode;
	}

}
