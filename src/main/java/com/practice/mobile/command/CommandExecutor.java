package com.practice.mobile.command;

import com.practice.mobile.exception.InvalidCriteriaException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.service.HandsetDetailsService;
import com.practice.mobile.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommandExecutor class controls the execution of different filters based on provided input
 * criteria. A single class where core logic of which filter to execute is implemented.
 */
@Component
public class CommandExecutor {
  private static final Logger LOGGER = LogManager.getLogger(CommandExecutor.class);
  private HandsetDetailsService handsetDetailsService;
  private AnnounceDateFilterCommand announceDateFilterCommand;
  private AudioJackFilterCommand audioJackFilterCommand;
  private BatteryFilterCommand batteryFilterCommand;
  private BrandFilterCommand brandFilterCommand;
  private GPSFilterCommand gpsFilterCommand;
  private IdFilterCommand idFilterCommand;
  private PhoneFilterCommand phoneFilterCommand;
  private PictureFilterCommand pictureFilterCommand;
  private PriceFilterCommand priceFilterCommand;
  private ResolutionFilterCommand resolutionFilterCommand;
  private SimFilterCommand simFilterCommand;

  public CommandExecutor(
      HandsetDetailsService handsetDetailsService,
      AnnounceDateFilterCommand announceDateFilterCommand,
      AudioJackFilterCommand audioJackFilterCommand,
      BatteryFilterCommand batteryFilterCommand,
      BrandFilterCommand brandFilterCommand,
      GPSFilterCommand gpsFilterCommand,
      IdFilterCommand idFilterCommand,
      PhoneFilterCommand phoneFilterCommand,
      PictureFilterCommand pictureFilterCommand,
      PriceFilterCommand priceFilterCommand,
      ResolutionFilterCommand resolutionFilterCommand,
      SimFilterCommand simFilterCommand) {
    this.handsetDetailsService = handsetDetailsService;
    this.announceDateFilterCommand = announceDateFilterCommand;
    this.audioJackFilterCommand = audioJackFilterCommand;
    this.batteryFilterCommand = batteryFilterCommand;
    this.brandFilterCommand = brandFilterCommand;
    this.gpsFilterCommand = gpsFilterCommand;
    this.idFilterCommand = idFilterCommand;
    this.phoneFilterCommand = phoneFilterCommand;
    this.pictureFilterCommand = pictureFilterCommand;
    this.priceFilterCommand = priceFilterCommand;
    this.resolutionFilterCommand = resolutionFilterCommand;
    this.simFilterCommand = simFilterCommand;
  }

  public List<HandsetDetails> processRequest(Map<String, String> queryParams)
      throws InvalidCriteriaException, ServiceException {

    List<Command> filterCommandList = new ArrayList<>();
    queryParams.keySet().stream()
        .forEach(
            s -> {
              if (Constants.ANNOUNCE_DATE.equals(s)) {
                filterCommandList.add(announceDateFilterCommand);
              }
              if (Constants.AUDIO_JACK.equals(s)) {
                filterCommandList.add(audioJackFilterCommand);
              }
              if (Constants.BATTERY.equals(s)) {
                filterCommandList.add(batteryFilterCommand);
              }
              if (Constants.BRAND.equals(s)) {
                filterCommandList.add(brandFilterCommand);
              }
              if (Constants.GPS.equals(s)) {
                filterCommandList.add(gpsFilterCommand);
              }
              if (Constants.ID.equals(s)) {
                filterCommandList.add(idFilterCommand);
              }
              if (Constants.PHONE.equals(s)) {
                filterCommandList.add(phoneFilterCommand);
              }
              if (Constants.PICTURE.equals(s)) {
                filterCommandList.add(pictureFilterCommand);
              }
              if (Constants.PRICE_EUR.equals(s)) {
                filterCommandList.add(priceFilterCommand);
              }
              if (Constants.RESOLUTION.equals(s)) {
                filterCommandList.add(resolutionFilterCommand);
              }
              if (Constants.SIM.equals(s)) {
                filterCommandList.add(simFilterCommand);
              }
            });

    if (filterCommandList.isEmpty()) {
      throw new InvalidCriteriaException("No valid criteria provided to filter records");
    } else {
      LOGGER.info("Executing search with " + filterCommandList.size() + " filter(s)");
      return execute(filterCommandList, queryParams);
    }
  }

  private List<HandsetDetails> execute(List<Command> commandList, Map<String, String> queryParams)
      throws ServiceException {
    List<HandsetDetails> handsetResponseList;
    List<Criteria> criteriaList = new ArrayList<>();
    try {
      queryParams = removeSpecialCharacters(queryParams);
      for (Command command : commandList) {
        criteriaList.add(command.execute(queryParams));
      }
      Criteria criteria =
          new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
      Query searchQuery = new Query(criteria);
      handsetResponseList = handsetDetailsService.searchByCriteria(searchQuery);
      LOGGER.info("Found " + handsetResponseList.size() + " records matching to provided filters.");
    } catch (Exception ex) {
      LOGGER.error("Command execution failed while processing request", ex);
      throw new ServiceException(ex.getMessage());
    }
    return handsetResponseList;
  }

  /**
   * Using this method to replace special characters in query parameter value to avoid obtaining
   * unexpected results as we are using regex to do wild card search from MongoDB
   *
   * @param queryParams
   * @return map
   */
  private Map<String, String> removeSpecialCharacters(Map<String, String> queryParams) {
    Map<String, String> refreshedMap = new HashMap<>();
    for (Map.Entry<String, String> entrySet : queryParams.entrySet()) {
      String key = entrySet.getKey();
      String value = entrySet.getValue().replaceAll("[^a-zA-Z0-9]", "");
      refreshedMap.put(key, value);
    }
    return refreshedMap;
  }
}
