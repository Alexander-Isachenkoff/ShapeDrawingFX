package sample;

import javafx.util.StringConverter;
import logic.Rounding;

class IgnoreSeparatorStringConverter extends StringConverter<Double>
{
    private String units;
    
    IgnoreSeparatorStringConverter(String units) {
        this.units = units;
    }
    
    @Override
    public String toString(Double object) {
        return Rounding.roundTo(object, 3) + " " + units;
    }
    
    @Override
    public Double fromString(String string) {
        string = string.replace(units, "");
        string = string.replace(" ", "");
        string = string.replace(',', '.');
        return Double.parseDouble(string);
    }
}
