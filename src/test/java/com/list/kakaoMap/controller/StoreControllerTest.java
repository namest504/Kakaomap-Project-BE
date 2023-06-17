package com.list.kakaoMap.controller;

import com.list.kakaoMap.dto.StoreDto;
import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.service.StoreService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.list.kakaoMap.dto.StoreDto.*;

@SpringBootTest
@Transactional
class StoreControllerTest {

    @Autowired
    StoreService storeService;

    @Test
    @DisplayName("가게 등록 테스트")
    public void addTest() {
        //given
        Store store = Store.builder()
                .id(1L)
                .name("test_7e6b067e0783")
                .detail("test_c5d923cc5984")
                .posX(97.45)
                .posY(72.58)
                .build();

        AddStoreRequest add = new AddStoreRequest(store.getName(), store.getDetail(), store.getPosX(), store.getPosY());
        //when
        storeService.addStore(add);
        List<Store> result = storeService.findStore("test_7e6b067e0783");

        //then
        Assertions.assertThat(result.get(0).getName()).isEqualTo("test_7e6b067e0783");
    }

}