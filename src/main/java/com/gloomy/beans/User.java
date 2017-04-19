package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Set<FoodRating> foodRating;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<PlaceRating> placeRatings;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    @Email
    @NotNull
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "description")
    private String description;

    @Column(name = "point")
    @NotNull
    private int point;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "facebook_access_token")
    @JsonIgnore
    private String facebookAccessToken;

    @Column(name = "facebook_id")
    @JsonIgnore
    private String facebookId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favorite", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "place_id")})
    @JsonIgnore
    private Set<Place> userFavoritePlaces;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<PlaceComment> placeComments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<TopicLike> topicLikes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<TopicComment> topicComments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<TopicReply> postReplies;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<PlaceReply> placeReplies;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<Topic> topics;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            userId = ((User) obj).getUserId();
        }
        return super.equals(obj);
    }
}
