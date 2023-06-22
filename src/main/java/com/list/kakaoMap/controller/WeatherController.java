package com.list.kakaoMap.controller;

import com.list.kakaoMap.dto.WeatherDto.Body;
import com.list.kakaoMap.dto.WeatherDto.Items;
import com.list.kakaoMap.dto.WeatherDto.WeatherApiResponse;
import com.list.kakaoMap.service.WeatherAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherAPIService weatherAPIService;

    @GetMapping
    public WeatherApiResponse getWeatherDataWeather() {
        WeatherApiResponse weatherResult = weatherAPIService.gettingInfoWeather();
        return weatherResult;
    }

    @GetMapping("/1")
    public Body getWeatherParsingDataWeather() {
        WeatherApiResponse weatherResult = weatherAPIService.gettingInfoWeather();
        Items items = weatherAPIService.parsingWeather(weatherResult);
        return new Body(weatherResult.getResponse().getBody().getDataType(), items, weatherResult.getResponse().getBody().getPageNo(), weatherResult.getResponse().getBody().getNumOfRows(), weatherResult.getResponse().getBody().getTotalCount());
    }
}
