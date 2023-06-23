package com.list.kakaoMap.repository;

import com.list.kakaoMap.entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
}