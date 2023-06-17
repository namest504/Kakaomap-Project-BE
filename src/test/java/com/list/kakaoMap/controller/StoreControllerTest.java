package com.list.kakaoMap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.list.kakaoMap.dto.StoreDto.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    @Test
    @DisplayName("가게 등록 테스트")
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
                .andExpect(jsonPath("$.name").value("test_7e6b067e0783"));
    }

    @Test
    @DisplayName("가게 조회 테스트")
    public void findTest() throws Exception {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("가게 정보 수정 테스트")
    public void editTest() throws Exception {
        //given
        //when
        //then
    }
}