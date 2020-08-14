package com.practice.mobile.service;

import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.HandsetDetails;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface HandsetDetailsService {
  List<HandsetDetails> searchByCriteria(Query query) throws ServiceException;
}
