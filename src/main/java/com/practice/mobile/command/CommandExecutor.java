package com.practice.mobile.command;

import com.practice.mobile.exception.InvalidCriteriaException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.Handset;
import com.practice.mobile.service.DataRetrievalService;
import com.practice.mobile.util.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * CommandExecutor class controls the execution of different filters based on provided input
 * criteria. A single class where core logic of which filter to execute is implemented.
 */
@Component
public class CommandExecutor {
  private static final Logger LOGGER = LogManager.getLogger(CommandExecutor.class);
  private DataRetrievalService dataRetrievalService;
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
      DataRetrievalService dataRetrievalService,
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
    this.dataRetrievalService = dataRetrievalService;
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

  public List<Handset> processRequest(Map<String, String> queryParams)
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
      LOGGER.info("Executing search with " + filterCommandList.size() + " filters");
      return execute(filterCommandList, queryParams);
    }
  }

  private List<Handset> execute(List<Command> commandList, Map<String, String> queryParams)
      throws ServiceException {
    List<Handset> handsetResponseList;
    try {
      handsetResponseList = dataRetrievalService.getHandsetRecords();
      for (Command command : commandList) {
        handsetResponseList = command.execute(handsetResponseList, queryParams);
      }
      LOGGER.info("Found " + handsetResponseList.size() + " records matching to provided filters.");
    } catch (Exception ex) {
      LOGGER.error("Command execution failed while processing request", ex);
      throw new ServiceException(ex.getMessage());
    }
    return handsetResponseList;
  }
}
