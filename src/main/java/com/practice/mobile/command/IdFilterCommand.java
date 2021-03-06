package com.practice.mobile.command;

import com.practice.mobile.model.Handset;
import com.practice.mobile.util.Constants;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class IdFilterCommand implements Command {

  @Override
  public List<Handset> execute(List<Handset> handsetList, Map<String, String> queryParams) {
    return handsetList.stream()
        .filter(p -> p.getId().equals(queryParams.get(Constants.ID)))
        .collect(Collectors.toList());
  }
}
