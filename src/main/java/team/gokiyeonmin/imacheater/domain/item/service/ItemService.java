package team.gokiyeonmin.imacheater.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemEnrollRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemUpdateRequest;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemImageResponse;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemResponse;
import team.gokiyeonmin.imacheater.domain.item.dto.res.ItemSimpleResponse;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemImageRepository;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemRepository;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemSpecification;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserImageResponse;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserImage;
import team.gokiyeonmin.imacheater.domain.user.event.RollbackUploadedImageEvent;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.util.S3Util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;
    private final S3Util s3Util;

    public ItemResponse enrollItem(ItemEnrollRequest request, List<MultipartFile> images, String username) {
        // username을 사용하여 User 엔티티 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        // 매물 저장
        Item item = Item.builder()
                .title(request.title())
                .content(request.content())
                .address(request.address())
                .deposit(request.deposit())
                .rent(request.rent())
                .maintenanceFeeIncluded(request.maintenanceFeeIncluded())
                .moveInDate(request.moveInDate())
                .expirationDate(request.expirationDate())
                .door(request.door())
                .floor(request.floor())
                .roomCount(request.roomCount())
                .windowDirection(request.windowDirection())
                .user(user)
                .build();
        itemRepository.save(item);

        // 이미지 처리 및 S3 업로드
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            String uploadedImageUrl = s3Util.uploadImage(S3Util.ITEM_IMAGE_FOLDER, image);

            ItemImage itemImage = ItemImage.builder()
                    .item(item)
                    .url(uploadedImageUrl)
                    .isThumbnail((i == 0))  // 첫 번째 이미지를 썸네일로 설정
                    .build();
            itemImageRepository.save(itemImage);
        }

        return ItemResponse.fromEntity(item);
    }

    @Transactional(readOnly = true)
    public ItemResponse getItem(Long itemId) {
         Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

         return ItemResponse.fromEntity(item);
    }

    @Transactional(readOnly = true)
    public List<ItemSimpleResponse> searchSimpleItems(Door door, Long floor, Long roomCount, Direction windowDirection,
                                                      Long minDeposit, Long maxDeposit, Long minRent, Long maxRent,
                                                      Boolean maintenanceFeeIncluded, LocalDate moveInDate,
                                                      Boolean isSold) {

        Specification<Item> specification = ItemSpecification.withFilters(door, floor, roomCount, windowDirection,
                minDeposit, maxDeposit, minRent, maxRent,
                maintenanceFeeIncluded, moveInDate, isSold);

        return itemRepository.findAll(specification).stream()
                .map(ItemSimpleResponse::fromEntity)
                .toList();
    }

    @Transactional
    public ItemResponse updateItem(Long itemId, ItemUpdateRequest request, List<MultipartFile> newImages) {
        // 1. 매물정보 가져옴
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        // 2. 삭제된 이미지 처리
        List<String> deletedImageUrls = request .deletedImageUrls();
        if (deletedImageUrls != null) {
            for (String deletedImageUrl : deletedImageUrls) {
                // 삭제할 이미지 가져오기
                ItemImage deletedItemImage = item.getItemImages().stream()
                        .filter(image -> image.getUrl().equals(deletedImageUrl))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM_IMAGE));

                // S3에서 이미지 삭제
                s3Util.deleteImage(deletedItemImage.getUrl());

                // DB에서 이미지 삭제
                item.removeImage(deletedItemImage.getId());
                itemImageRepository.deleteById(deletedItemImage.getId());
            }
        }

        // 3. 새로운 이미지 업로드 및 추가
        if (newImages != null) {
            for (MultipartFile image : newImages) {
                String uploadedImageUrl = s3Util.uploadImage(S3Util.ITEM_IMAGE_FOLDER, image);

                ItemImage itemImage = ItemImage.builder()
                        .item(item)
                        .url(uploadedImageUrl)
                        .isThumbnail(false)
                        .build();
                itemImageRepository.save(itemImage);

                item.addImage(itemImage);
            }
        }

        // 4. 이미지 순서 업데이트
        List<String> imageOrder = request.imageOrder();
        if (imageOrder != null && !imageOrder.isEmpty()) {
            List<ItemImage> orderedImages = new ArrayList<>();

            // 이미지 순서에 맞게 재정렬
            for (String imageUrl : imageOrder) {
                item.getItemImages().stream()
                        .filter(image -> image.getUrl().equals(imageUrl))
                        .findFirst()
                        .ifPresent(orderedImages::add);
            }

            // 기존 순서와 일치하지 않는다면 리스트 업데이트
            if (!orderedImages.equals(item.getItemImages())) {
                item.setItemImages(orderedImages);  // itemImages 리스트에 새로운 순서를 반영
            }
        }

        // 5. 첫 번째 이미지를 썸네일로 설정
        List<ItemImage> itemImages = item.getItemImages();

        if (!itemImages.isEmpty()) {
            itemImages.forEach(image -> image.changeThumbnail(false));  // 기존 썸네일 초기화
            itemImages.get(0).changeThumbnail(true);  // 첫 번째 이미지를 썸네일로 설정
        }

        // 6. 나머지 아이템 정보 업데이트
        item.updateInfo(request);
        itemRepository.save(item);

        return ItemResponse.fromEntity(item);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
        List<ItemImage> itemImages = item.getItemImages();

        for (ItemImage image : itemImages) {
            s3Util.deleteImage(image.getUrl());  // S3에서 해당 이미지 삭제
        }
        itemImageRepository.deleteAll(itemImages);  // 관련된 이미지들 DB에서 삭제

        itemRepository.delete(item);
    }

//    @Transactional
//    public ItemResponse changeIsSold(Long itemId, Boolean isSold) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
//
//        item.changeSold(isSold);
//
//        return ItemResponse.fromEntity(item);
//    }
}
