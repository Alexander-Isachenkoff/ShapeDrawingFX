package logic;

public class SizeConverter
{
    private final static int DPI = 141;
    private final static double CM_PER_INCH = 2.54;
    
    public static double toPixels(double cm) {
        double inches = cm / CM_PER_INCH;
        return DPI * inches;
    }
    
    public static double toCm(double px) {
        return px / DPI * CM_PER_INCH;
    }
    
    public static double squareToCm(double pixels) {
        double inches = pixels / (DPI*DPI);
        return inches * CM_PER_INCH * CM_PER_INCH;
    }
}
