package com.services.weather.apis;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URLConnection;


public class WeatherApi
{

    public static String makeApiCall(String apiKey, double latitude, double longtitude)
    {

        String output="";
        StringBuffer content = new StringBuffer();

        try
        {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longtitude+"&appid="+apiKey);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            output = content.toString();
            bufferedReader.close();

            return output;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }



}
