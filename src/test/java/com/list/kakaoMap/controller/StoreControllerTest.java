package com.list.kakaoMap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.service.StoreService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.list.kakaoMap.dto.StoreDto.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StoreService storeService;

    @Test
    @DisplayName("가게 등록 테스트")
    public void addTest() throws Exception {
        StoreRequest storeRequest = new StoreRequest("testId", "testDetail", 11.01, 27.09);
        when(storeService.addStore(any())).thenReturn(new Store(1L, "testId", "testDetail", 11.01, 27.09));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeRequest)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andDo(print());
    }

    @Test
    @DisplayName("가게 조회 테스트")
    public void findTest() throws Exception {
        //given
        Store storeResponse1 = new Store(1L, "test_7e6b067e0783", "test_c5d923cc5984", 97.45, 72.58);
        Store storeResponse2 = new Store(2L, "test_1123klnsas", "test_29sne215231", 23.56, 28.65);
        Store storeResponse3 = new Store(3L, "test_24901hsksa", "test_32kjba9e1", 55.27, 81.21);
        List<Store> storeResponseList = new ArrayList<>();
        storeResponseList.add(storeResponse1);
        storeResponseList.add(storeResponse2);
        storeResponseList.add(storeResponse3);

        when(storeService.findStore(any())).thenReturn(storeResponseList);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("name", "test");

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/store")
                .param("name","test"));
        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 정보 수정 테스트")
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

        when(storeService.editStore(any(),any())).thenReturn(store);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/store/" + 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeRequest)));
        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.name").value("test_12523as"))
                .andDo(print());
    }

    @Test
    @DisplayName("가게 삭제 테스트")
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