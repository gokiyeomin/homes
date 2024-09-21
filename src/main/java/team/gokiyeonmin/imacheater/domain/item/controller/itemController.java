package team.gokiyeonmin.imacheater.domain.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemEnrollRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemUpdateRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemResponse;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemSimpleResponse;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.service.ItemService;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class itemController {

    private final ItemService itemService;

    @PostMapping("/api/items")
    public ResponseEntity<ItemResponse> createItem(
            @RequestPart("json") @Valid ItemEnrollRequest itemEnrollRequest,
//            @Valid @RequestBody ItemEnrollRequest itemEnrollRequest
            @RequestPart("images") List<MultipartFile> images
    ) {
        // 이미지가 한 개도 없는 경우 예외 처리
        if (images == null || images.isEmpty()) {
            throw new BusinessException(ErrorCode.ITEM_IMAGE_REQUIRED);
        }

        ItemResponse itemResponse = itemService.enrollItem(itemEnrollRequest, images);
        return ResponseEntity.created(URI.create("/api/items")).body(itemResponse);
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
            @RequestParam(required = false) LocalDate moveInDate
    ) {
        List<ItemSimpleResponse> items = itemService.searchSimpleItems(
                door, floor, roomCount, windowDirection, minDeposit,
                maxDeposit, minRent, maxRent, maintenanceFeeIncluded, moveInDate
        );
        return ResponseEntity.ok(items);
    }

//    @GetMapping("/api/items")
//    public Item getItem(
//            @ItemId Long itemId
//    ) {
//        return itemService.getItem(itemId);
//    }


    @GetMapping("/api/items/{itemId}")
    public ResponseEntity<ItemResponse> getItem(
            @PathVariable Long itemId
    ) {
        ItemResponse itemResponse = itemService.getItem(itemId);
        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping("/api/items/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestPart("json") ItemUpdateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> newImages,
            @RequestParam(required = false) List<Long> deletedImageIds
    ) {
        ItemResponse updatedItem = itemService.updateItem(itemId, request, newImages, deletedImageIds);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/api/items/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId
    ) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/items/{itemId}/status/sold")
    public ResponseEntity<ItemResponse> changeItemSoldStatus(
            @PathVariable Long itemId,
            @RequestBody Boolean isSold
    ) {
        ItemResponse updatedItem = itemService.changeIsSold(itemId, isSold);
        return ResponseEntity.ok(updatedItem);
    }
}
