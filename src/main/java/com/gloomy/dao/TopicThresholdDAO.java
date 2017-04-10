package com.gloomy.dao;

import com.gloomy.beans.TopicThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Transactional
public interface TopicThresholdDAO extends JpaRepository<TopicThreshold, String> {

    @Query("SELECT th FROM TopicThreshold th WHERE th.typeName = ?1")
    TopicThreshold findThreshold(String topicTypeName);
}
