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
        addBeams();
        addRafters();
    }

    private void addBeams(){
        //<rect x="0" y="35" height="4.5" width="780" style="stroke:#000000; fill: #ffffff" />
        //<rect x="0" y="565" height="4.5" width="780" style="stroke:#000000; fill: #ffffff" />
        carportSvg.addRectangle(0, 35, 4.5, width, "stroke:#000000; stroke-width:2px; fill: #ffffff");
        carportSvg.addRectangle(0, 565, 4.5, width, "stroke:#000000; stroke-width:2px; fill: #ffffff");
    }

    private void addRafters(){
        for(double i = 0; i < width ; i += (int) 55.714){
            carportSvg.addRectangle(i, 0, length, 4.5, "stroke:#000000; fill: #ffffff");
        }
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
