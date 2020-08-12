package com.practice.mobile.command;

import com.practice.mobile.model.Handset;
import java.util.List;
import java.util.Map;

public interface Command {
  public List<Handset> execute(List<Handset> handsetList, Map<String, String> queryParams);
}
