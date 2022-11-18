package gml.data;

public class GmlGroupNode extends GmlNode {

    public GmlGroupNode(int nodeId) {
        super(nodeId);
    }

    @Override
    public boolean isGroup() {
        return true;
    }

}
