package app.services;

import app.persistence.ConnectionPool;

public class CarportSvg {
    private int width;
    private int length;
    private final Svg carportSvg;
    private final Svg mainCarport;
    private final int offset = 35;

    private final ConnectionPool connectionPool = new ConnectionPool();
    Calculator calculator = new Calculator(width, length, connectionPool);

    public CarportSvg(int width, int length){
        this.width = width;
        this.length = length;
        mainCarport = new Svg(0, 0, "0 0 1000 1000", "100%");

        carportSvg = new Svg(offset, 0, String.format("0 0 %d %d", length, width), "50%");  // Inner carport SVG
        addArrow();
        addText();
        addInnerSvg();
    }

    private void addBeams(){ // -- remme
        carportSvg.addRectangle(0, 35, 4.5, length, "stroke:#00000095; stroke-width:2px; fill: #ffffff95");
        carportSvg.addRectangle(0, width - 35, 4.5, length, "stroke:#00000095; stroke-width:2px; fill: #ffffff95");
    }

    private void addRafters(){ // -- spær
        for(double i = 0; i < length ; i += (int) 55.714){
            carportSvg.addRectangle(i, 0, width, 4.5, "stroke:#00000095; fill: #ffffff95");
        }
    }

    private void addCross(){ // -- kryds
        carportSvg.addCross(offset, offset, length - offset, width -offset, "stroke:#00000095; stroke-width:2px; stroke-dasharray: 5 5;");
        carportSvg.addCross(offset, width-offset, length - offset, offset, "stroke:#00000095; stroke-width:2px; stroke-dasharray: 5 5;");
    }

    private void addArrow(){
        mainCarport.addArrow(offset / 2,0, offset / 2, width ); // left arrow
        mainCarport.addArrow(offset, width + (offset / 2), length + offset, width + (offset / 2)); // bottom arrow
    }

    private void addPost(){ // -- stolper
        double postSize = 9.7;

        carportSvg.addRectangle(100,  offset - (postSize / 4) , postSize, postSize, "stroke:#000000; fill: #ffffff95" );
        carportSvg.addRectangle(length - 100,  offset - (postSize / 4), postSize, postSize, "stroke:#000000; fill: #ffffff95" );
        carportSvg.addRectangle(100,  width - offset - (postSize / 4), postSize, postSize, "stroke:#000000; fill: #ffffff95" );
        carportSvg.addRectangle(length - 100,  width - offset - (postSize / 4), postSize, postSize, "stroke:#000000; fill: #ffffff95" );
    }

    public void addText(){
        mainCarport.addText("text-anchor: middle; transform: rotate(-90deg);",-(width / 2) , 12, width); // left side
        mainCarport.addText("text-anchor: middle;", (length / 2) + offset, width + offset, length); // bottom
    }

    public void addInnerSvg(){
        carportSvg.addRectangle(0, 0, width, length, "stroke:#000000; stroke-width:2px; fill: #ffffff95");
        addCross();
        addBeams();
        addRafters();
        addPost();
        mainCarport.addSvg(carportSvg);
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCarportSvg(int width) {
        this.width = width;
    }

    @Override
    public String toString(){
        return mainCarport.toString();
    }
}
