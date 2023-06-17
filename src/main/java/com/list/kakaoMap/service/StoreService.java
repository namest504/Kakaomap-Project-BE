package com.list.kakaoMap.service;

import com.list.kakaoMap.entity.QStore;
import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.exception.CustomException;
import com.list.kakaoMap.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.list.kakaoMap.dto.StoreDto.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Store addStore(StoreRequest storeRequest) {
        return storeRepository.save(Store.builder()
                .name(storeRequest.getName())
                .detail(storeRequest.getDetail())
                .posX(storeRequest.getPosX())
                .posY(storeRequest.getPosY())
                .build());
    }

    public List<Store> findStore(String name) {
        QStore qStore = new QStore("store");
        return jpaQueryFactory
                .selectFrom(qStore)
                .where(qStore.name.contains(name))
                .fetch();
    }

    public Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "가게가 존재하지 않습니다."));
    }

    public Store editStore(Long storeId, StoreRequest storeRequest) {
        return storeRepository.save(Store.builder()
                .id(storeId)
                .name(storeRequest.getName())
                .detail(storeRequest.getDetail())
                .posX(storeRequest.getPosX())
                .posY(storeRequest.getPosY())
                .build());
    }
}
