package gml.data;

public class GmlNode {
	private final int _nodeId;
	private String _name;
	private String _label;
	private GmlNodeGraphics _graphics;
	private GmlLabelGraphics _labelGrpahics;
    /** Reference, if specified, of the group node for which this node is part of */
    private GmlNode _groupNode;

    public GmlNode(int nodeId) {
		_nodeId = nodeId;
	}

	public int getNodeId() {
		return _nodeId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public GmlNodeGraphics createNodeGraphics() {
		_graphics = new GmlNodeGraphics();
		return _graphics;
	}

	public GmlNodeGraphics getGraphics() {
		return _graphics;
	}

	public GmlLabelGraphics createLabelGraphics() {
		_labelGrpahics = new GmlLabelGraphics();
		return _labelGrpahics;
	}

	public GmlLabelGraphics getLabelGraphics() {
		return _labelGrpahics;
	}

    public boolean isGroup() {
        return false;
    }

    public GmlNode getGroupNode() {
        return _groupNode;
    }

    public void setGroupNode(GmlNode groupNode) {
        _groupNode = groupNode;
    }

}
