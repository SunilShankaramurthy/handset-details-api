package com.practice.mobile.command;

import com.practice.mobile.model.Handset;
import com.practice.mobile.util.Constants;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PriceCommand implements Command {

  @Override
  public List<Handset> execute(List<Handset> handsetList, Map<String, String> queryParams) {
    List<Handset> list =
        handsetList.stream()
            .filter(p -> p.getRelease().getPriceEur().equals(queryParams.get(Constants.PRICE_EUR)))
            .collect(Collectors.toList());
    return list;
  }
}
