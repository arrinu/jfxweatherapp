package com.oopmp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;

class JsonParserBase {
    protected JSONObject json;

    public JsonParserBase(String jsonString) throws ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        this.json = (JSONObject) parser.parse(jsonString);
    }
}

class WeatherDataParser extends JsonParserBase {
    public WeatherDataParser(String jsonString) throws ParseException, org.json.simple.parser.ParseException {
        super(jsonString);
    }

    public String getFeelsLike() {
        JSONObject current = (JSONObject) json.get("current");
        return current.get("feelslike").toString();
    }

    public String getHumidity() {
        JSONObject current = (JSONObject) json.get("current");
        return current.get("humidity").toString();
    }

    public String getPrecipitation() {
        JSONObject current = (JSONObject) json.get("current");
        return current.get("precip").toString();
    }

    public String getWeatherDescriptions() {
        JSONArray weatherDescriptions = (JSONArray) ((JSONObject) json.get("current")).get("weather_descriptions");
        return weatherDescriptions.get(0).toString();
    }

    public String getTemperature() {
        JSONObject current = (JSONObject) json.get("current");
        return current.get("temperature").toString();
    }
}

