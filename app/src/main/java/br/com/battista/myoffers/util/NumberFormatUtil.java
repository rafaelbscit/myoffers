package br.com.battista.myoffers.util;

import com.google.common.base.Strings;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by rabsouza on 16/04/16.
 */
public class NumberFormatUtil {

    public static Locale locale = new Locale("pt", "BR");
    public static NumberFormat numberInstance = NumberFormat.getNumberInstance(locale);

    public static String format(Double value) {
        String result = null;
        if (value == null) {
            return result;
        }

        numberInstance.setMinimumFractionDigits(2);
        numberInstance.setMaximumFractionDigits(2);
        result = numberInstance.format(value);
        return result;
    }

    public static Double parse(String value) {
        Double result = null;
        if (Strings.isNullOrEmpty(value)) {
            return result;
        }

        numberInstance.setMinimumFractionDigits(2);
        numberInstance.setMaximumFractionDigits(2);
        try {
            result = numberInstance.parse(value).doubleValue();
        } catch (Exception e) {
            result = Double.valueOf(value.replaceAll("\\,", "\\."));
        }
        return result;
    }
}
