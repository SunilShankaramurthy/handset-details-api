package com.practice.mobile.command;

import com.practice.mobile.util.Constants;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AnnounceDateFilterCommand implements Command {

  @Override
  public Criteria execute(Map<String, String> queryParams) {

    return Criteria.where(Constants.RELEASE_ANNOUNCE_DATE)
        .regex(queryParams.get(Constants.ANNOUNCE_DATE), Constants.CASE_INSENSITIVE);
  }
}
