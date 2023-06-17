package com.list.kakaoMap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.list.kakaoMap.dto.StoreDto.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StoreRepository storeRepository;

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    @Test
    @DisplayName("가게 등록 테스트")
    @Transactional
    public void addTest() throws Exception {
        //given
        StoreRequest storeRequest = new StoreRequest("test_7e6b067e0783", "test_c5d923cc5984", 97.45, 72.58);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/store/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(storeRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.name").value("test_7e6b067e0783"))
                .andDo(print());
    }

    @Test
    @DisplayName("가게 조회 테스트")
    @Transactional
    public void findTest() throws Exception {
        //given
        Store store = Store.builder()
                .name("test_7e6b067e0783")
                .detail("test_c5d923cc5984")
                .posX(97.45)
                .posY(72.58)
                .build();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("name", "test");
        //when
        storeRepository.save(store);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/store/find")
                        .params(param)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 정보 수정 테스트")
    @Transactional
    public void editTest() throws Exception {
        //given
        Store store = Store.builder()
                .name("test_7e6b067e0783")
                .detail("test_c5d923cc5984")
                .posX(97.45)
                .posY(72.58)
                .build();

        StoreRequest storeRequest = new StoreRequest("test_7e6b067e0783", "test_c5d923cc5984", 97.45, 72.58);
        //when
        Store save = storeRepository.save(store);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/store/edit/" + save.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(storeRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.name").value("test_7e6b067e0783"))
                .andDo(print());
    }
}