package team.gokiyeonmin.imacheater.domain.item.entity;

import jakarta.persistence.*;
import lombok.Getter;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Entity
@Table(name = "items")
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "deposit", nullable = false)
    private Integer deposit;

    @Column(name = "rent", nullable = false)
    private Integer rent;

    @Column(name = "maintenance_fee_included", nullable = false)
    private Boolean maintenanceFeeIncluded;
}
