package team.gokiyeonmin.imacheater.domain.item.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Entity
@Table(name = "item_images")
@Getter
@NoArgsConstructor
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", length = 300)
    private String url;

    @Column(name = "is_thumbnail")
    private Boolean isThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public ItemImage(
            Item item,
            String url,
            Boolean isThumbnail
    ) {
        this.item = item;
        this.url = url;
        this.isThumbnail = isThumbnail;
    }

    public void updateUrl(String url) {
        if (this.url == null || !this.url.equals(url)) {
            this.url = url;
        }
    }

    public void deleteUrl() {
        this.url = null;
    }

    public void changeThumbnail(Boolean isThumbnail) {
        this.isThumbnail = isThumbnail;
    }
}
