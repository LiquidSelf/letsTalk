package dto.feed;

import dao.feed.DB_FEED;
import dao.users.DB_USER;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

public class FeedDTO {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Long id;
    private String imageUrl;
    private String description;

    public FeedDTO() {}

    public FeedDTO(DB_FEED db_feed){
        if(db_feed == null) return;
        setId(db_feed.getId());
        setDescription(db_feed.getDescription());
        setImageUrl(db_feed.getImageUrl());
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
