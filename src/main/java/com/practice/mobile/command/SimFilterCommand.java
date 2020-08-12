package com.practice.mobile.command;

import com.practice.mobile.model.Handset;
import com.practice.mobile.util.Constants;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SimFilterCommand implements Command {

  @Override
  public List<Handset> execute(List<Handset> handsetList, Map<String, String> queryParams) {
    return handsetList.stream()
        .filter(p -> StringUtils.containsIgnoreCase(p.getSim(), queryParams.get(Constants.SIM)))
        .collect(Collectors.toList());
  }
}
