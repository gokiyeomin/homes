package team.gokiyeonmin.imacheater.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.auth.event.SignInEvent;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemEnrollRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemUpdateRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemResponse;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemRepository;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemSpecification;
import team.gokiyeonmin.imacheater.domain.user.dto.req.UserUpdateRequest;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserResponse;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item enrollItem(ItemEnrollRequest itemEnrollRequest) {
        Item item = Item.builder()
                .title(itemEnrollRequest.title())
                .content(itemEnrollRequest.content())
                .address(itemEnrollRequest.address())
                .deposit(itemEnrollRequest.deposit())
                .rent(itemEnrollRequest.rent())
                .maintenanceFeeIncluded(itemEnrollRequest.maintenanceFeeIncluded())
                .moveInDate(itemEnrollRequest.moveInDate())
                .expirationDate(itemEnrollRequest.expirationDate())
                .door(itemEnrollRequest.door())
                .floor(itemEnrollRequest.floor())
                .roomCount(itemEnrollRequest.roomCount())
                .windowDirection(itemEnrollRequest.windowDirection())
                .build();

        return itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Item getItem(Long itemId) {
         return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> searchSimpleItems(Door door, Long floor, Long roomCount, Direction windowDirection,
                                             Long minDeposit, Long maxDeposit, Long minRent, Long maxRent,
                                             Boolean maintenanceFeeIncluded, LocalDate moveInDate) {

        Specification<Item> specification = ItemSpecification.withFilters(door, floor, roomCount, windowDirection,
                minDeposit, maxDeposit, minRent, maxRent,
                maintenanceFeeIncluded, moveInDate);

        return itemRepository.findAll(specification).stream()
                .map(ItemResponse::fromEntity)
                .toList();
    }

    @Transactional
    public Item updateItem(Long itemId, ItemUpdateRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        item.updateInfo(request);

        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        itemRepository.delete(item);
    }
}
