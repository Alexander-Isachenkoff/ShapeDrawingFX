package sample;

import javafx.util.StringConverter;
import logic.Rounding;

class IgnoreSeparatorStringConverter extends StringConverter<Double>
{
    @Override
    public String toString(Double object) {
        return String.valueOf(Rounding.roundTo(object, 3));
    }

    @Override
    public Double fromString(String string) {
        return Double.parseDouble(string.replace(',', '.'));
    }
}
