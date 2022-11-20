package gml.data;

public class GmlLabelGraphics {

    private String _text;
    private String _alignment;
    private Integer _fontSize;
    private String _fontStyle;
    private String _fontName;

    public GmlLabelGraphics() {
        // ...
    }

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }

    public String getAlignment() {
        return _alignment;
    }

    public void setAlignment(String alignment) {
        _alignment = alignment;
    }

    public Integer getFontSize() {
        return _fontSize;
    }

    public void setFontSize(Integer fontSize) {
        _fontSize = fontSize;
    }

    public String getFontStyle() {
        return _fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        _fontStyle = fontStyle;
    }

    public String getFontName() {
        return _fontName;
    }

    public void setFontName(String fontName) {
        _fontName = fontName;
    }

}
