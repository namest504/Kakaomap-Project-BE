package com.list.kakaoMap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class StoreDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddStoreRequest {
        private String name;
        private String detail;
        private double posX;
        private double posY;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AddStoreResponse {
        private boolean success;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class FindStoreDto {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class FindStoreResponse {
        private boolean success;
        private List<FindStoreDto> result;
    }



}
