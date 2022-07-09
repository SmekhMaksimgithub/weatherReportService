package com.services.weather.models;

import com.services.weather.apis.WeatherApi;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "weather_reports")
public class WeatherReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String description;


    private double temperature;
    private double tempFeelsLike;
    private double windSpeed;
    private int humidity;
    private int pressure;
    private double latitude;
    private double longtitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTempFeelsLike() {
        return tempFeelsLike;
    }

    public void setTempFeelsLike(double tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }



    public WeatherReport()
    {

    }

    public WeatherReport(String apiKey, String name, double latitude, double longtitude) {
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
        setDataViaApi(apiKey);
    }

    public void setDataViaApi(String apiKey)
    {

        String res = WeatherApi.makeApiCall(apiKey, latitude,longtitude);
        JSONObject object = new JSONObject(res);


        this.humidity = object.getJSONObject("main").getInt("humidity");
        this.pressure = object.getJSONObject("main").getInt("pressure");
        this.tempFeelsLike = object.getJSONObject("main").getFloat("feels_like");
        this.temperature = object.getJSONObject("main").getFloat("temp");
        this.windSpeed = object.getJSONObject("wind").getFloat("speed");

        JSONArray reportArray = (JSONArray) object.get("weather");
        JSONObject weatherData = (JSONObject) reportArray.get(0);
        this.description = (String)weatherData.get("description");
    }

}
