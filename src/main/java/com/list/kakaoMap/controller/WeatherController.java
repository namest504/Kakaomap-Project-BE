package com.list.kakaoMap.controller;

import com.list.kakaoMap.dto.WeatherInfoDto.Items;
import com.list.kakaoMap.dto.WeatherInfoDto.WeatherApiResponse;
import com.list.kakaoMap.dto.WeatherInfoDto.WeatherInfoResponse;
import com.list.kakaoMap.service.WeatherAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
@Slf4j
public class WeatherController {

    private final WeatherAPIService weatherAPIService;

    @GetMapping
    public WeatherApiResponse getWeatherDataWeather() {

        List<String> time = weatherAPIService.gettingTime();
        String dateString = time.get(0);
        String timeString = time.get(1);

        log.info("[{}],[{}] scheduledGetWeather 전체 실행", dateString, timeString);

        WeatherApiResponse weatherResult = weatherAPIService.gettingInfoWeather(dateString, timeString);
        return weatherResult;
    }

    @GetMapping("/test1")
    public Items getWeatherParsingDataWeather() {

        List<String> time = weatherAPIService.gettingTime();
        String dateString = time.get(0);
        String timeString = time.get(1);
        String nextTimeString = time.get(2);

        log.info("[{}],[{}],[{}] scheduledGetWeather 1 수동 실행", dateString, timeString, nextTimeString);

        WeatherApiResponse weatherResult = weatherAPIService.gettingInfoWeather(dateString, timeString);
        Items items = weatherAPIService.parsingWeather(weatherResult, dateString, nextTimeString);
        return items;
    }

    @GetMapping("/test2")
    public WeatherApiResponse gettingTest2() {

        List<String> time = weatherAPIService.gettingTime();
        String dateString = time.get(0);
        String timeString = time.get(1);
        String nextTimeString = time.get(2);

        log.info("[{}],[{}],[{}] scheduledGetWeather 2 수동 및 저장 실행", dateString, timeString, nextTimeString);

        WeatherApiResponse weatherApiResponse = weatherAPIService.gettingTestInfo(dateString, timeString);
        Items items = weatherAPIService.parsingWeather(weatherApiResponse, dateString, nextTimeString);
        weatherAPIService.saveInfo(items.getItem());
        return weatherApiResponse;
    }

    @Scheduled(cron = "0 47 * * * ?", zone = "Asia/Seoul") // 00초 47분 매시 매일 매월 모든요일 서울 시간을 기준을 실행
    public void scheduledGetWeather() {

        List<String> time = weatherAPIService.gettingTime();
        String dateString = time.get(0);
        String timeString = time.get(1);
        String nextTimeString = time.get(2);

        log.info("[{}],[{}],[{}] scheduledGetWeather 자동 실행", dateString, timeString, nextTimeString);

        WeatherApiResponse weatherResult = weatherAPIService.gettingInfoWeather(dateString, timeString);
        Items items = weatherAPIService.parsingWeather(weatherResult, dateString, nextTimeString);
        weatherAPIService.saveInfo(items.getItem());
    }

    @GetMapping("/info")
    public WeatherInfoResponse gettingRecentWeatherData() {

        List<String> time = weatherAPIService.gettingTime();
        String dateString = time.get(0);
        String nextTimeString = time.get(2);

        WeatherInfoResponse recentWeatherInfo = weatherAPIService.findRecentWeatherInfo(dateString, nextTimeString);
        recentWeatherInfo.setSuccess(true);
        return recentWeatherInfo;
    }
}
