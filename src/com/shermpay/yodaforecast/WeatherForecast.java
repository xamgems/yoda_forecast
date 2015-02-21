package com.shermpay.yodaforecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shermpay on 2/17/15.
 */
public class WeatherForecast {
    enum DayOfWeek {
        @SerializedName("Mon")
        MON("Mon", "Monday"),
        @SerializedName("Tue")
        TUE("Tue", "Tuesday"),
        @SerializedName("Wed")
        WED("Wed", "Wednesday"),
        @SerializedName("Thu")
        THU("Thu", "Thursday"),
        @SerializedName("Fri")
        FRI("Fri", "Friday"),
        @SerializedName("Sat")
        SAT("Sat", "Saturday"),
        @SerializedName("Sun")
        SUN("Sun", "Sunday");


        String abbrev;
        String canonical;
        DayOfWeek(String abbrev, String canonical) {
            this.abbrev = abbrev;
            this.canonical = canonical;
        }

        @Override
        public String toString() {
            return canonical;
        }
    }

    String condition;
    @SerializedName("day_of_week")
    DayOfWeek dayOfWeek;
    double high;
    double low;
    double highCelcius;
    double lowCelcius;

    public String getCondition() {
        return condition;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getHighCelcius() {
        return highCelcius;
    }

    public double getLowCelcius() {
        return lowCelcius;
    }

    @Override
    public String toString() {
        return "\ncondition: " +  condition +
                "\ndayOfWeek:" + dayOfWeek +
                "\nhigh: " + high +
                "\nlow: " + low +
                "\nhigh C: " + highCelcius +
                "\nlow C: " + lowCelcius;
    }

    public String forecastString() {
       return "The weather for " + dayOfWeek + " will be " + condition + ".\n" +
               "With temperatures up to " + high + " Fahrenheit and " + highCelcius + " Celsius.\n" +
               "And as down to " + low + " Fahrenheit and " + lowCelcius + " Celsius.\n";
    }
}
