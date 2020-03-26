package dao.feed;

import dto.feed.FeedDTO;
import dto.users.UsersDTO;

import javax.persistence.*;

@Entity
@Table(name = "feed_items")
public class DB_FEED {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true,
            name = "image_url",
            length = 500)
    private String imageUrl;

    @Column(nullable = true,
            columnDefinition="TEXT")
    private String description;

    public DB_FEED() {
    }

    public DB_FEED(FeedDTO dto) {
        if(dto == null) return;
        setId(dto.getId());
        setDescription(dto.getDescription());
        setImageUrl(dto.getImageUrl());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}