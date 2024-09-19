package team.gokiyeonmin.imacheater.domain.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemEnrollRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemUpdateRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemResponse;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.service.ItemService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.ItemId;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class itemController {

    private final ItemService itemService;

    @PostMapping("/api/items")
    public ResponseEntity<Item> createItem(
            @Valid @RequestBody ItemEnrollRequest itemEnrollRequest
    ) {
        Item item = itemService.enrollItem(itemEnrollRequest);
        return ResponseEntity.created(URI.create("/api/items")).body(item);
    }

    @GetMapping("/api/items")
    public List<ItemResponse> getSimpleItems(
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
        return itemService.searchSimpleItems(
                door, floor, roomCount, windowDirection, minDeposit,
                maxDeposit, minRent, maxRent, maintenanceFeeIncluded, moveInDate
        );
    }

//    @GetMapping("/api/items")
//    public Item getItem(
//            @ItemId Long itemId
//    ) {
//        return itemService.getItem(itemId);
//    }


    @GetMapping("/api/items/{itemId}")
    public Item getItem(
            @PathVariable Long itemId
    ) {
        return itemService.getItem(itemId);
    }

    @PutMapping("/api/items")
    public Item updateItem(
            @ItemId Long itemId,
            @Valid @RequestBody ItemUpdateRequest request
    ) {
        return itemService.updateItem(itemId, request);
    }

    @DeleteMapping("/api/items/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long id
    ) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
