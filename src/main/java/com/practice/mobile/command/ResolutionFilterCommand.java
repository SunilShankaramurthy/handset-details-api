package com.practice.mobile.command;

import com.practice.mobile.util.Constants;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResolutionFilterCommand implements Command {

  @Override
  public Criteria execute(Map<String, String> queryParams) {
    return Criteria.where(Constants.RESOLUTION)
        .regex(queryParams.get(Constants.RESOLUTION), Constants.CASE_INSENSITIVE);
  }
}
