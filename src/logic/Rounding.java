package logic;

public class Rounding
{
    public static double roundTo(double value, int order) {
        double a = Math.pow(10, order);
        return Math.round(value * a) / a;
    }
}
