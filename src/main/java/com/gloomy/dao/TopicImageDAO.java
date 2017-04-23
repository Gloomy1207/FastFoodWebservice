package com.gloomy.dao;

import com.gloomy.beans.TopicImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 23-Apr-17.
 */
@Transactional
public interface TopicImageDAO extends JpaRepository<TopicImage, Integer> {

    Page<TopicImage> findByTopicTopicId(int topicId, Pageable pageable);
}
