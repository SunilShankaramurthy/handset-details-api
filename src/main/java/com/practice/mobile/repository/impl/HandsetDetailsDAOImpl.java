package com.practice.mobile.repository.impl;

import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.repository.HandsetDetailsDAO;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HandsetDetailsDAOImpl implements HandsetDetailsDAO {

  private MongoTemplate mongoTemplate;

  public HandsetDetailsDAOImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<HandsetDetails> searchByCriteria(Query query) {
    return mongoTemplate.find(query, HandsetDetails.class);
  }
}
