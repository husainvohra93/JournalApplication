package com.example.JournalApplication.service;

/*Business Logic is written in Service */

import com.example.JournalApplication.api.response.WeatherResponse;
import com.example.JournalApplication.cache.AppCache;
import com.example.JournalApplication.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.apiKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {

        /* In Below statement it is appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()) ---> Gets the value of
        "WEATHER_API" key from the Map. Map was already
         initialized by database values at time of Bean Creation by
         function @PostConstruct init.*/

        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apiKey);
        ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
