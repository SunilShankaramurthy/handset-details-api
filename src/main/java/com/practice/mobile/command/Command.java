package com.practice.mobile.command;

import java.util.Map;
import org.springframework.data.mongodb.core.query.Criteria;

public interface Command {
  Criteria execute(Map<String, String> queryParams);
}
