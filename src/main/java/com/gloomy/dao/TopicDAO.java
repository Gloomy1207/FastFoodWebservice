package com.gloomy.dao;

import com.gloomy.beans.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Transactional
public interface TopicDAO extends JpaRepository<Topic, Integer> {

    @Query("SELECT t FROM Topic t ORDER BY t.topicLikes.size")
    Page<Topic> findAllOrderByLike(Pageable pageable);
}
