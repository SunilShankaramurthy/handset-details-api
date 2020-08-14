package com.practice.mobile.repository.impl;

import com.practice.mobile.exception.DataRetrievalException;
import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.repository.HandsetDetailsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HandsetDetailsDAOImpl implements HandsetDetailsDAO {
  private static final Logger LOGGER = LogManager.getLogger(HandsetDetailsDAOImpl.class);

  private MongoTemplate mongoTemplate;

  public HandsetDetailsDAOImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<HandsetDetails> searchByCriteria(Query query) throws DataRetrievalException {
    List<HandsetDetails> handsetDetailsList;
    try {
      handsetDetailsList = mongoTemplate.find(query, HandsetDetails.class);
    } catch (Exception ex) {
      LOGGER.error("Failed to fetch records from DB with exception:{}", ex);
      throw new DataRetrievalException(ex.getMessage());
    }
    return handsetDetailsList;
  }
}
