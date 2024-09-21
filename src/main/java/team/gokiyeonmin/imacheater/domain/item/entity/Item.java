package team.gokiyeonmin.imacheater.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.dto.req.ItemUpdateRequest;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserImage;
import team.gokiyeonmin.imacheater.domain.user.entity.UserRole;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(
        name = "items"
)

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "deposit", nullable = false)
    private Long deposit;

    @Column(name = "rent", nullable = false)
    private Long rent;

    @Column(name = "maintenance_fee_included", nullable = false)
    private Boolean maintenanceFeeIncluded;

    @Column(name = "move_in_date", nullable = false)
    private LocalDate moveInDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    private Door door;

    @Column(name = "floor", nullable = false)
    private Long floor;

    @Column(name = "room_count", nullable = false)
    private Long roomCount;

    @Column(name = "window_direction", nullable = false)
    private Direction windowDirection;

    @Column(name = "is_sold", nullable = false)
    private Boolean isSold;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemImage> itemImages = new ArrayList<>();

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public Item(
            String title,
            String content,
            String address,
            Long deposit,
            Long rent,
            Boolean maintenanceFeeIncluded,
            LocalDate moveInDate,
            LocalDate expirationDate,
            Door door,
            Long floor,
            Long roomCount,
            Direction windowDirection,
            User user
    ) {
        this.title = title;
        this.content = content;
        this.address = address;
        this.deposit = deposit;
        this.rent = rent;
        this.maintenanceFeeIncluded = maintenanceFeeIncluded;
        this.moveInDate = moveInDate;
        this.expirationDate = expirationDate;
        this.door = door;
        this.floor = floor;
        this.roomCount = roomCount;
        this.windowDirection = windowDirection;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.isSold = false;
    }

    // 뭘 업데이트 가능하게 할건지?
    public void updateInfo(ItemUpdateRequest request) {
        if (request.title() != null && !this.title.equals(request.title())) {
            this.title = request.title();
        }
        if (request.content() != null && !this.content.equals(request.content())) {
            this.content = request.content();
        }
        if (request.address() != null && !this.address.equals(request.address())) {
            this.address = request.address();
        }
        if (request.deposit() != null && !this.deposit.equals(request.deposit())) {
            this.deposit = request.deposit();
        }
        if (request.rent() != null && !this.rent.equals(request.rent())) {
            this.rent = request.rent();
        }
        if (request.maintenanceFeeIncluded() != null && !this.maintenanceFeeIncluded.equals(request.maintenanceFeeIncluded())) {
            this.maintenanceFeeIncluded = request.maintenanceFeeIncluded();
        }
        if (request.moveInDate() != null && !this.moveInDate.equals(request.moveInDate())) {
            this.moveInDate = request.moveInDate();
        }
        if (request.expirationDate() != null && !this.expirationDate.equals(request.expirationDate())) {
            this.expirationDate = request.expirationDate();
        }
        if (request.door() != null && !this.door.equals(request.door())) {
            this.door = request.door();
        }
        if (request.floor() != null && !this.floor.equals(request.floor())) {
            this.floor = request.floor();
        }
        if (request.roomCount() != null && !this.roomCount.equals(request.roomCount())) {
            this.roomCount = request.roomCount();
        }
        if (request.windowDirection() != null && !this.windowDirection.equals(request.windowDirection())) {
            this.windowDirection = request.windowDirection();
        }
        if (request.isSold() != null && !this.isSold.equals(request.isSold())) {
            this.isSold = request.isSold();
        }
    }

//    public void changeSold(Boolean isSold) {
//        this.isSold = isSold;
//    }

    public void addImage(ItemImage itemImage) {
        if (itemImage != null && !this.itemImages.contains(itemImage)) {
            this.itemImages.add(itemImage);
        }
    }

    public void removeImage(Long itemImageId) {
        this.itemImages.removeIf(image -> image.getId().equals(itemImageId));
    }

//    public void updateImage(Long itemImageId, String newUrl) {
//        this.itemImages.stream()
//                .filter(image -> image.getId().equals(itemImageId))
//                .findFirst()
//                .ifPresent(image -> image.updateUrl(newUrl));
//    }
//
//    public void changeThumbnailImage(Long itemImageId) {
//        this.itemImages.forEach(image -> image.changeThumbnail(false));
//
//        this.itemImages.stream()
//                .filter(image -> image.getId().equals(itemImageId))
//                .findFirst()
//                .ifPresent(image -> image.changeThumbnail(true));
//    }
}
