package com.list.kakaoMap.service;

import com.list.kakaoMap.dto.WeatherInfoDto.Item;
import com.list.kakaoMap.dto.WeatherInfoDto.Items;
import com.list.kakaoMap.dto.WeatherInfoDto.WeatherApiResponse;
import com.list.kakaoMap.dto.WeatherInfoDto.WeatherInfoResponse;
import com.list.kakaoMap.entity.QWeatherInfo;
import com.list.kakaoMap.entity.WeatherInfo;
import com.list.kakaoMap.repository.WeatherInfoRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAPIService {

    @Value("${WEATHER_SECRET_KEY}")
    private String WEATHER_SECRET_KEY;

    private final WeatherInfoRepository weatherInfoRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public WeatherApiResponse gettingTestInfo(String dateString, String timeString) {
        WeatherApiResponse weatherApiResponse = WebClient.create("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=CnLzOXapELF0TIJM2t5aR0U7u6oVK9Y5Uf22qHxkiMUILuRP1pSn09iKEEhQkUSYObWk0u8ueCNLD2nPIvA8TQ==&pageNo=1&numOfRows=1000&dataType=JSON&base_date=" + dateString + "&base_time=" + timeString + "&nx=55&ny=127")
                .get()
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
        return weatherApiResponse;
    }

    public WeatherApiResponse gettingInfoWeather(String dateString, String timeString) {

        WeatherApiResponse weatherApiResponseMono = WebClient.create("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUltraSrtFcst")
                        .queryParam("ServiceKey", WEATHER_SECRET_KEY)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "1000")
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", dateString)
                        .queryParam("base_time", timeString)
                        .queryParam("nx", "59")
                        .queryParam("ny", "110")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
        return weatherApiResponseMono;
    }

    public Items parsingWeather(WeatherApiResponse weatherApiResponse, String dateString, String nextTimeString) {

        List<Item> item = weatherApiResponse.getResponse().getBody().getItems().getItem();
        List<Item> result = new ArrayList<>();
        for (Item info : item) {
            if (info.getCategory().equals("T1H") || info.getCategory().equals("RN1")) {
                if (info.getFcstDate().equals(dateString) && info.getFcstTime().equals(nextTimeString)) {
                    result.add(info);
                }
            }
        }
        return new Items(result);
    }

    public void saveInfo(List<Item> items) {
        for (Item item : items) {
            WeatherInfo weatherInfo = WeatherInfo.builder()
                    .baseDate(item.getBaseDate())
                    .baseTime(item.getBaseTime())
                    .category(item.getCategory())
                    .fcstDate(item.getFcstDate())
                    .fcstTime(item.getFcstTime())
                    .fcstValue(item.getFcstValue())
                    .nx(item.getNx())
                    .ny(item.getNy())
                    .build();
            weatherInfoRepository.save(weatherInfo);
        }
        log.info("saveInfo 실행");
    }

    public WeatherInfoResponse findRecentWeatherInfo(String date, String time) {
        QWeatherInfo qWeatherInfo = new QWeatherInfo("weatherinfo");

        List<WeatherInfo> weatherInfos = jpaQueryFactory.selectFrom(qWeatherInfo)
                .where(qWeatherInfo.fcstDate.eq(date))
                .where(qWeatherInfo.fcstTime.eq(time))
                .fetch();

        WeatherInfoResponse weatherInfoResponse = new WeatherInfoResponse();
        for (WeatherInfo weatherInfo : weatherInfos) {
            if (weatherInfo.getCategory().equals("T1H"))
                weatherInfoResponse.setTemperatureValue(weatherInfo.getFcstValue());
            else if (weatherInfo.getCategory().equals("RN1"))
                weatherInfoResponse.setRainValue(weatherInfo.getFcstValue());
        }

        return weatherInfoResponse;
    }
}
