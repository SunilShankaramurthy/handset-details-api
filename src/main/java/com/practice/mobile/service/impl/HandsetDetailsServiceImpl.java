package com.practice.mobile.service.impl;

import com.practice.mobile.exception.DataRetrievalException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.repository.HandsetDetailsDAO;
import com.practice.mobile.service.HandsetDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandsetDetailsServiceImpl implements HandsetDetailsService {

  private static final Logger LOGGER = LogManager.getLogger(HandsetDetailsServiceImpl.class);

  private HandsetDetailsDAO handsetDetailsDAO;

  public HandsetDetailsServiceImpl(HandsetDetailsDAO handsetDetailsDAO) {
    this.handsetDetailsDAO = handsetDetailsDAO;
  }

  @Override
  public List<HandsetDetails> searchByCriteria(Query query) throws ServiceException {
    List<HandsetDetails> handsetDetailsList = null;
    try {
      handsetDetailsList = handsetDetailsDAO.searchByCriteria(query);
    } catch (DataRetrievalException ex) {
      LOGGER.error("Failed to fetch records from DB.{}", ex);
      throw new ServiceException(ex.getMessage());
    }
    return handsetDetailsList;
  }
}
