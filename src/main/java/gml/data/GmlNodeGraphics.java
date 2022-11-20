package gml.data;

public class GmlNodeGraphics {

    /**
     * Trapezoid with smaller top and larger bottom
     */
    public static final String TYPE_TRAPEZOID = "trapezoid";

    /**
     * Trapezoid with larger top and smaller bottom
     */
    public static final String TYPE_TRAPEZOID2 = "trapezoid2";

    public static final String TYPE_RECTANGLE = "rectangle";
    public static final String TYPE_RECTANGLE3D = "rectangle3d";
    public static final String TYPE_ROUNDRECTANGLE = "roundrectangle";

    public static final String TYPE_ELLIPSE = "ellipse";
    public static final String TYPE_OVAL = "oval";
    public static final String TYPE_CIRCLE = "circle";
    public static final String TYPE_OCTAGON = "octagon";
    public static final String TYPE_DIAMOND = "diamond";

    public static final String TYPE_PARALLELOGRAM = "parallelogram";
    public static final String TYPE_PARALLELOGRAM2 = "parallelogram2";
    public static final String TYPE_HEXAGON = "hexagon";
    public static final String TYPE_FATARROW_RIGHT = "fatarrow";
    public static final String TYPE_FATARROW_LEFT = "fatarrow2";

    private String _fillColor;
    private String _type;

    public GmlNodeGraphics() {
        // ...
    }

    public void setFillColor(String fillColor) {
        _fillColor = fillColor;
    }

    public String getFillColor() {
        return _fillColor;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getType() {
        return _type;
    }

}
