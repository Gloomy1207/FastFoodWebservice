package com.gloomy.dao;

import com.gloomy.beans.TopicLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 15-Apr-17.
 */
@Transactional
public interface TopicLikeDAO extends JpaRepository<TopicLike, Integer> {

    @Query("SELECT t FROM TopicLike t WHERE t.topic.topicId = ?1 AND t.user.userId = ?2")
    TopicLike getTopicLikeByTopicAndUser(int topicId, Long userId);

    TopicLike findTopicLikeByUserUserIdAndTopicTopicId(Long userId, int topicId);
}
