package com.list.kakaoMap.service;

import com.list.kakaoMap.dto.WeatherDto.Item;
import com.list.kakaoMap.dto.WeatherDto.Items;
import com.list.kakaoMap.dto.WeatherDto.WeatherApiResponse;
import com.list.kakaoMap.entity.WeatherInfo;
import com.list.kakaoMap.repository.WeatherInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAPIService {

    @Value("${WEATHER_SECRET_KEY}")
    private String WEATHER_SECRET_KEY;

    private final WeatherInfoRepository weatherInfoRepository;

    public WeatherApiResponse gettingInfoWeather() {

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        // 날짜(format: yyyyMMdd)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = localDateTime.format(dateFormatter);

        // 시간(format: HH00)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH");
        String timeString = localDateTime.minusHours(1).format(timeFormatter);

        WeatherApiResponse weatherApiResponseMono = WebClient.create("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUltraSrtFcst")
                        .queryParam("ServiceKey", WEATHER_SECRET_KEY)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "1000")
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", dateString)
                        .queryParam("base_time", timeString + "00")
                        .queryParam("nx", "59")
                        .queryParam("ny", "110")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
        return weatherApiResponseMono;
    }

    public Items parsingWeather(WeatherApiResponse weatherApiResponse) {
        List<Item> item = weatherApiResponse.getResponse().getBody().getItems().getItem();
        List<Item> result = new ArrayList<>();
        for (Item info : item) {
            if (info.getCategory().equals("T1H") || info.getCategory().equals("RN1")) {
                result.add(info);
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
}
