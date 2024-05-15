package app.services;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;


    public CarportSvg(int width, int length){
        this.width = width;
        this.length = length;
        carportSvg = new Svg(0, 0, "0 0 855 690", "100%");
        carportSvg.addRectangle(0, 0, length, width, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        //carportSvg.addArrow(0, length, width, 0, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        //carportSvg.addCross(1,2,3,4, "style=\"stroke:#000000; stroke-dasharray: 5 5;");
        addBeams();
        addRafters();
        //addCross();
    }

    private void addBeams(){ // -- remme
        //<rect x="0" y="35" height="4.5" width="780" style="stroke:#000000; fill: #ffffff" />
        //<rect x="0" y="565" height="4.5" width="780" style="stroke:#000000; fill: #ffffff" />
        carportSvg.addRectangle(0, 35, 4.5, width, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        carportSvg.addRectangle(0, length - 35, 4.5, width, "stroke:#000000; stroke-width:2px; fill: #ffffff");
    }

    private void addRafters(){ // -- spær
        for(double i = 0; i < width ; i += (int) 55.714){
            carportSvg.addRectangle(i, 0, length, 4.5, "stroke:#000000; fill: #ffffff");
        }
    }

    private void addCross(){ // -- kryds
        //<line x1="55" y1="35" x2="600" y2="569.5" style="stroke:#000000; stroke-dasharray: 5 5;" />
        //<line x1="55" y1="569.5" x2="600" y2="35" style="stroke:#000000; stroke-dasharray: 5 5;" />

        carportSvg.addCross(55, 35, 600, 569, "style=\"stroke:#000000; stroke-dasharray: 5 5;");
        carportSvg.addCross(55, 569, 600, 35, "style=\"stroke:#000000; stroke-dasharray: 5 5;");
    }

    private void addPost(){ // -- stolper

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
