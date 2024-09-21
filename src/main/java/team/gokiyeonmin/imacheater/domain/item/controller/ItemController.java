package team.gokiyeonmin.imacheater.domain.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemEnrollRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemUpdateRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemImagesResponse;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemResponse;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemSimpleResponse;
import team.gokiyeonmin.imacheater.domain.item.service.ItemService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetail;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/api/items")
    public ResponseEntity<ItemResponse> createItem(
            @Valid @RequestBody ItemEnrollRequest itemEnrollRequest,
            @AuthenticationPrincipal CustomUserDetail customUserDetail // 인증된 유저 정보
    ) {
        // 유저 정보 추출
        String username = customUserDetail.getUsername();

        ItemResponse itemResponse = itemService.enrollItem(itemEnrollRequest, username);
        return ResponseEntity.created(URI.create("/api/items")).body(itemResponse);
    }

    @PostMapping("/api/items/{itemId}/images")
    public ResponseEntity<?> uploadItemImages(
            @PathVariable Long itemId,
            @RequestPart("images") List<MultipartFile> images
    ) {
        itemService.uploadItemImages(itemId, images);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/items")
    public ResponseEntity<List<ItemSimpleResponse>> getSimpleItems(
            @RequestParam(required = false) Door door,
            @RequestParam(required = false) Long floor,
            @RequestParam(required = false) Long roomCount,
            @RequestParam(required = false) Direction windowDirection,
            @RequestParam(required = false) Long minDeposit,
            @RequestParam(required = false) Long maxDeposit,
            @RequestParam(required = false) Long minRent,
            @RequestParam(required = false) Long maxRent,
            @RequestParam(required = false) Boolean maintenanceFeeIncluded,
            @RequestParam(required = false) LocalDate moveInDate,
            @RequestParam(required = false) Boolean isSold
    ) {
        List<ItemSimpleResponse> items = itemService.searchSimpleItems(
                door, floor, roomCount, windowDirection, minDeposit,
                maxDeposit, minRent, maxRent, maintenanceFeeIncluded, moveInDate, isSold
        );
        return ResponseEntity.ok(items);
    }

    @GetMapping("/api/items/{itemId}")
    public ResponseEntity<ItemResponse> getItem(
            @PathVariable Long itemId
    ) {
        ItemResponse itemResponse = itemService.getItem(itemId);
        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping("/api/items/{itemId}/images")
    public ResponseEntity<ItemImagesResponse> getItemImages(
            @PathVariable Long itemId
    ) {
        ItemImagesResponse itemImageResponse = itemService.getItemImages(itemId);
        return ResponseEntity.ok(itemImageResponse);
    }

    @GetMapping("/api/items/my")
    public ResponseEntity<List<ItemSimpleResponse>> getMyItems(
            @UserId Long userId
    ) {
        List<ItemSimpleResponse> myItems = itemService.getMyItems(userId);
        return ResponseEntity.ok(myItems);
    }

    @PutMapping("/api/items/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestPart("itemUpdateRequest") ItemUpdateRequest request,
            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages
    ) {
        ItemResponse updatedItem = itemService.updateItem(itemId, request, newImages);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/api/items/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId
    ) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
