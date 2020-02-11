package logic;

public class SizeConverter
{
    private final static int DPI = 141;
    private final static double CM_PER_INCH = 2.54;
    
    public static double toPixels(double cm) {
        final double inches = cm / CM_PER_INCH;
        return DPI * inches;
    }
    
    public static double squareToCm(double pixels) {
        final double inches = pixels / (DPI*DPI);
        return inches * CM_PER_INCH * CM_PER_INCH;
    }
}
