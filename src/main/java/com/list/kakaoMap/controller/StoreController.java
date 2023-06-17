package com.list.kakaoMap.controller;

import com.list.kakaoMap.entity.Store;
import com.list.kakaoMap.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.list.kakaoMap.dto.StoreDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/add")
    public AddStoreResponse addStore(@RequestBody AddStoreRequest addStoreRequest) {
        return new AddStoreResponse(true, storeService.addStore(addStoreRequest).getName());
    }

    @GetMapping("/find")
    public FindStoreResponse findStore(@RequestParam String name) {
        List<Store> store = storeService.findStore(name);
        List<FindStoreDto> collect = store.stream()
                .map(s -> new FindStoreDto(s.getId(), s.getName())).collect(Collectors.toList());
        return new FindStoreResponse(true, collect);
    }

}
