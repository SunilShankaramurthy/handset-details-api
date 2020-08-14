package com.practice.mobile.service;

import com.practice.mobile.model.HandsetDetails;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;

public interface HandsetDetailsService {
  List<HandsetDetails> searchByCriteria(Query query);
}
