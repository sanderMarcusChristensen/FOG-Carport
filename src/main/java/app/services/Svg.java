package app.services;

public class Svg {
    private final static String SVG_TEMPLATE = "<svg version=\"1.1\"\n" +
            "     x=\"%d\" y=\"%d\"\n" +
            "     viewBox=\"%s\" width=\"%s\" \n" +
            "     preserveAspectRatio=\"xMinYMin\" \n +" +
            "     class=\"carport-svg\" >";

    private static final String SVG_ARROW_DEFS = "<defs>\n" +
            "        <marker id=\"beginArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"0\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,6 L12,0 L12,12 L0,6\" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "        <marker id=\"endArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"12\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "    </defs>";
    public static final String SVG_RECT_TEMPLATE = "<rect x=\"%.2f\" y=\"%.2f\" height=\"%.2f\" width=\"%.2f\" style=\"%s\" />";

    public static final String SVG_CROSS = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\" />";

    public static final String SVG_TEXT_TEMPLATE ="<text style=\"%s\" x=\"%s\" y=\"%s\">%d cm</text>";

    public static final String SVG_LINE_TEMPLATE ="<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\" marker-start=\"url(#beginArrow)\" marker-end=\"url(#endArrow)\" />";
    private final StringBuilder svg = new StringBuilder();

    public Svg(int x, int y, String viewBox, String width){
        svg.append(String.format(SVG_TEMPLATE, x, y, viewBox, width));
        svg.append(SVG_ARROW_DEFS);
    }

    public void addRectangle(double x, double y, double length, double width, String style){
        svg.append(String.format(SVG_RECT_TEMPLATE, x, y, length, width, style));
    }

    public void addLine(int x1, int y1, int x2, int y2, String style){
        svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));
    }

    public void addArrow(int x1, int y1, int x2, int y2){
        addLine(x1, y1, x2, y2, "stroke:#000000;");
    }

    public void addCross(int x1, int y1, int x2, int y2, String style){
        svg.append(String.format(SVG_CROSS, x1, y1, x2, y2, style));
    }

    public void addText(String style, int x, int y, int text){
        svg.append(String.format(SVG_TEXT_TEMPLATE, style, x, y, text));
    }

    public void addSvg(Svg innerSvg){
        svg.append(innerSvg.toString());
    }

    @Override
    public String toString(){
        return svg.append("</svg>").toString();
    }
}