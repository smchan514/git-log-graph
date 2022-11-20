package git_log_graph.dot.data;

public class DotNode {

    private String _nodeId;
    private String _label;

    public DotNode(String nodeId, String label) {
        _nodeId = nodeId;
        _label = label;
    }

    public String getNodeId() {
        return _nodeId;
    }

    public String getLabel() {
        return _label;
    }

}
