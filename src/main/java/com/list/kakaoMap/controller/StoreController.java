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

    @PostMapping
    public StoreResponse addStore(@RequestBody StoreRequest storeRequest) {
        return new StoreResponse(true, storeService.addStore(storeRequest).getName());
    }

    @GetMapping
    public FindStoreResponse findStore(@RequestParam String name) {
        List<Store> store = storeService.findStore(name);
        List<FindStoreDto> collect = store.stream()
                .map(s -> new FindStoreDto(s.getId(), s.getName(), s.getDetail(), s.getPosX(), s.getPosY())).collect(Collectors.toList());
        return new FindStoreResponse(true, collect);
    }

    @PutMapping("/{storeId}")
    public StoreResponse editStore(@PathVariable Long storeId, @RequestBody StoreRequest storeRequest) {
        Store store = storeService.editStore(storeId, storeRequest);
        return new StoreResponse(true, store.getName());
    }

    @DeleteMapping("/{storeId}")
    public DeleteStoreResponse deleteStore(@PathVariable Long storeId) {
        return new DeleteStoreResponse(storeService.deleteStore(storeId));
    }
}
