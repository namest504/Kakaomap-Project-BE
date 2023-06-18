package com.list.kakaoMap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.service.StoreService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.list.kakaoMap.dto.StoreDto.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    StoreService storeService;

    @Test
    @DisplayName("가게 등록 테스트")
    @Transactional
    public void addTest() throws Exception {
        //given
        StoreRequest storeRequest = new StoreRequest("test_7e6b067e0783", "test_c5d923cc5984", 97.45, 72.58);
        Store storeResponse = new Store(1L, "test_7e6b067e0783", "test_c5d923cc5984", 97.45, 72.58);
        given(storeService.addStore(storeRequest))
                .willReturn(storeResponse);
        String json = new ObjectMapper().writeValueAsString(storeRequest);

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.name").value("test_7e6b067e0783"))
                .andDo(print());
    }

    @Test
    @DisplayName("가게 조회 테스트")
    @Transactional
    public void findTest() throws Exception {
        //given
        Store storeResponse1 = new Store(1L, "test_7e6b067e0783", "test_c5d923cc5984", 97.45, 72.58);
        Store storeResponse2 = new Store(2L, "test_1123klnsas", "test_29sne215231", 23.56, 28.65);
        Store storeResponse3 = new Store(3L, "test_24901hsksa", "test_32kjba9e1", 55.27, 81.21);
        List<Store> storeResponses = new ArrayList<>();
        storeResponses.add(storeResponse1);
        storeResponses.add(storeResponse2);
        storeResponses.add(storeResponse3);

        String json = new ObjectMapper().writeValueAsString(storeResponses);

        given(storeService.findStore("test"))
                .willReturn(storeResponses);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("name", "test");

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/store")
                .param("name","test"));
//                .contentType(MediaType.APPLICATION_JSON));
        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.result").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 정보 수정 테스트")
    @Transactional
    public void editTest() throws Exception {
        //given
        StoreRequest storeRequest = new StoreRequest("test_12523as", "test_29sne215231", 23.56, 28.65);
        Store store = Store.builder()
                .id(2L)
                .name("test_12523as")
                .detail("test_29sne215231")
                .posX(23.56)
                .posY(28.65)
                .build();
        String json = new ObjectMapper().writeValueAsString(storeRequest);
        given(storeService.editStore(2L, storeRequest))
                .willReturn(store);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/store/" + 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.name").value("test_12523as"))
                .andDo(print());
    }

    @Test
    @DisplayName("가게 삭제 테스트")
    @Transactional
    public void deleteTest() throws Exception {
        //given
        given(storeService.deleteStore(1L))
                .willReturn(true);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.delete("/store/" + 1));
        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}