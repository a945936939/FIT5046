package com.example.assignment;

import android.graphics.drawable.Icon;

import java.util.ArrayList;

public class Root {
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

    public String getIcon() {return weather.get(0).getIcon();}
    public double getTemp() {
        return main.getTemp();
    }
    public int getPressure() {
        return main.getPressure();
    }
    public int getHumidity() {
        return main.getHumidity();
    }
    public double getWindSpeed() {return wind.getSpeed();}
}

class Coord{
    public double lon;
    public double lat;
}

class Weather{
    public int id;
    public String main;
    public String description;
    public String icon;

    public String getIcon() {return icon;}
}

class Main{
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;

    public double getTemp() {
        return temp;
    }

    public int getPressure() {return pressure;}

    public int getHumidity() {return humidity;}

}

class Wind{
    public double speed;
    public int deg;

    public double getSpeed() {return speed;}
}

class Clouds{
    public int all;
}

class Sys{
    public int type;
    public int id;
    public String country;
    public int sunrise;
    public int sunset;
}