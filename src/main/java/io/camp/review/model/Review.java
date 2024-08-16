package io.camp.review.model;

import io.camp.audit.BaseEntity;
import io.camp.campsite.model.entity.Campsite;
import io.camp.image.model.Image;
import io.camp.like.model.Like;
import io.camp.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString(exclude = {"campsite"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Campsite campsite;

    @OneToMany(mappedBy = "review")
    private List<Like> likes;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

}
