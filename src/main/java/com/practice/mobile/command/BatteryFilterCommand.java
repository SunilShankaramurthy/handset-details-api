package com.practice.mobile.command;

import com.practice.mobile.util.Constants;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BatteryFilterCommand implements Command {

  @Override
  public Criteria execute(Map<String, String> queryParams) {
    return Criteria.where(Constants.HARDWARE_BATTERY)
        .regex(queryParams.get(Constants.BATTERY), Constants.CASE_INSENSITIVE);
  }
}
