package com.list.kakaoMap.controller;

import com.list.kakaoMap.dto.WeatherDto.Body;
import com.list.kakaoMap.dto.WeatherDto.Items;
import com.list.kakaoMap.dto.WeatherDto.WeatherApiResponse;
import com.list.kakaoMap.service.WeatherAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
@Slf4j
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

    @Scheduled(cron = "0 45 * * * ?", zone = "Asia/Seoul") // 00초 45분 매시 매일 매월 모든요일 서울 시간을 기준을 실행
    public void scheduledGetWeather() {
        log.info("scheduledGetWeather 실행");
        WeatherApiResponse weatherResult = weatherAPIService.gettingInfoWeather();
        Items items = weatherAPIService.parsingWeather(weatherResult);
        weatherAPIService.saveInfo(items.getItem());
    }
}
