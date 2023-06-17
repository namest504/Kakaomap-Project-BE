package com.list.kakaoMap.service;

import com.list.kakaoMap.entity.QStore;
import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.list.kakaoMap.dto.StoreDto.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Store addStore(AddStoreRequest addStoreRequest) {
        return storeRepository.save(Store.builder()
                .name(addStoreRequest.getName())
                .detail(addStoreRequest.getDetail())
                .posX(addStoreRequest.getPosX())
                .posY(addStoreRequest.getPosY())
                .build());
    }

    public List<Store> findStore(String name) {
        QStore qStore = new QStore("store");
        return jpaQueryFactory
                .selectFrom(qStore)
                .where(qStore.name.contains(name))
                .fetch();
    }
}
