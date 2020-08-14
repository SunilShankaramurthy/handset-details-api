package com.practice.mobile.command;

import com.practice.mobile.util.Constants;
import java.util.Map;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
public class GPSFilterCommand implements Command {

  @Override
  public Criteria execute(Map<String, String> queryParams) {
    return Criteria.where(Constants.HARDWARE_GPS)
        .regex(queryParams.get(Constants.GPS), Constants.CASE_INSENSITIVE);
  }
}
