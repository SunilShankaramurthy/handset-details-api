package com.practice.mobile.repository;

import com.practice.mobile.model.HandsetDetails;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;

public interface HandsetDetailsDAO {

  List<HandsetDetails> searchByCriteria(Query query);
}
