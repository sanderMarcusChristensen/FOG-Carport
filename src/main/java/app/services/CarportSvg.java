package app.services;

import app.persistence.ConnectionPool;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;
    private final int offset = 35;

    private final ConnectionPool connectionPool = new ConnectionPool();
    Calculator calculator = new Calculator(width, length, connectionPool);

    public CarportSvg(int width, int length){
        this.width = width;
        this.length = length;
        carportSvg = new Svg(0, 0, "0 0 1000 1000", "100%");
        carportSvg.addRectangle(0, 0, width, length, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        //carportSvg.addArrow(0, length, width, 0, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        //carportSvg.addCross(1,2,3,4, "style=\"stroke:#000000; stroke-dasharray: 5 5;");
        addCross();
        addBeams();
        addRafters();
        addArrow();
        addPost();
    }

    private void addBeams(){ // -- remme
        carportSvg.addRectangle(0, 35, 4.5, length, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        carportSvg.addRectangle(0, width - 35, 4.5, length, "stroke:#000000; stroke-width:2px; fill: #ffffff");
    }

    private void addRafters(){ // -- spær
        for(double i = 0; i < length ; i += (int) 55.714){
            carportSvg.addRectangle(i, 0, width, 4.5, "stroke:#000000; fill: #ffffff");
        }
    }

    private void addCross(){ // -- kryds
        carportSvg.addCross(offset, offset, length - offset, width -offset, "stroke:#000000; stroke-width:2px; stroke-dasharray: 5 5;");
        carportSvg.addCross(offset, width-offset, length - offset, offset, "stroke:#000000; stroke-width:2px; stroke-dasharray: 5 5;");
    }

    private void addArrow(){ // -- kryds
        carportSvg.addArrow();
        carportSvg.addArrow();
    }

    private void addPost(){ // -- stolper
        int postSize = 20;
        // første række
        for (int i = 0; i < length; i +=  340){
            //<rect x="110" y="32" height="9.7" width="10" style="stroke:#000000; fill: #ffffff" />
            carportSvg.addRectangle(offset + i,  offset - ((double)postSize / 2), postSize, postSize, "stroke:#000000; fill: #ffffff" );
        }
        // Anden række
        //for (int i = 0; i < length ; i++){

        //}
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCarportSvg(int width) {
        this.width = width;
    }

    @Override
    public String toString(){
        return carportSvg.toString();
    }
}
