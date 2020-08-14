package com.practice.mobile.service.impl;

import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.repository.HandsetDetailsDAO;
import com.practice.mobile.service.HandsetDetailsService;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class HandsetDetailsServiceImpl implements HandsetDetailsService {

  private HandsetDetailsDAO handsetDetailsDAO;

  public HandsetDetailsServiceImpl(HandsetDetailsDAO handsetDetailsDAO) {
    this.handsetDetailsDAO = handsetDetailsDAO;
  }

  @Override
  public List<HandsetDetails> searchByCriteria(Query query) {
    return handsetDetailsDAO.searchByCriteria(query);
  }
}
