package com.gloomy.dao;

import com.gloomy.beans.PlaceComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25/04/2017.
 */
public interface PlaceCommentDAO extends JpaRepository<PlaceComment, Integer> {

    Page<PlaceComment> findByPlacePlaceIdOrderByCommentTimeDesc(int placeId, Pageable pageable);

    PlaceComment findPlaceCommentByCommentIdAndUserUserId(int commentId, Long userId);
}
