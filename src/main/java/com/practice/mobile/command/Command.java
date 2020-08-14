package com.practice.mobile.command;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Map;

public interface Command {
  Criteria execute(Map<String, String> queryParams);
}
