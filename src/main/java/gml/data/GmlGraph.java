package gml.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GmlGraph {

	private int _version = 2;
	private int _directed = 1;

	private final LinkedList<GmlNode> _lstNodes = new LinkedList<>();
	private final LinkedList<GmlEdge> _lstEdges = new LinkedList<>();

	private int _lastNodeId;
	private int _lastEdgeId;

	public GmlGraph() {
		// ...
	}

	public int getVersion() {
		return _version;
	}

	public int getDirected() {
		return _directed;
	}

    public GmlNode createNode() {
		GmlNode node = new GmlNode(_lastNodeId++);
		_lstNodes.add(node);
		return node;
	}

    public GmlNode createNode(GmlGroupNode groupNode) {
        GmlNode node = createNode();
        node.setGroupNode(groupNode);
        return node;
    }

    public GmlGroupNode createGroupNode() {
        GmlGroupNode node = new GmlGroupNode(_lastNodeId++);
        _lstNodes.add(node);
        return node;
    }

	public GmlEdge createEdge() {
		GmlEdge edge = new GmlEdge(_lastEdgeId++);
		_lstEdges.add(edge);
		return edge;
	}

	public List<GmlNode> getNodes() {
		return Collections.unmodifiableList(_lstNodes);
	}

	public List<GmlEdge> getEdges() {
		return Collections.unmodifiableList(_lstEdges);
	}

}
