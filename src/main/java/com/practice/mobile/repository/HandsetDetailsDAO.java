package com.practice.mobile.repository;

import com.practice.mobile.exception.DataRetrievalException;
import com.practice.mobile.model.HandsetDetails;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface HandsetDetailsDAO {

  List<HandsetDetails> searchByCriteria(Query query) throws DataRetrievalException;
}
