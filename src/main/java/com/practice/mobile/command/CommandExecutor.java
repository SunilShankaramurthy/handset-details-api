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
  private AnnounceDateCommand announceDateCommand;
  private AudioJackCommand audioJackCommand;
  private BatteryCommand batteryCommand;
  private BrandCommand brandCommand;
  private GPSCommand gpsCommand;
  private IdCommand idCommand;
  private PhoneCommand phoneCommand;
  private PictureCommand pictureCommand;
  private PriceCommand priceCommand;
  private ResolutionCommand resolutionCommand;
  private SimCommand simCommand;

  public CommandExecutor(
      DataRetrievalService dataRetrievalService,
      AnnounceDateCommand announceDateCommand,
      AudioJackCommand audioJackCommand,
      BatteryCommand batteryCommand,
      BrandCommand brandCommand,
      GPSCommand gpsCommand,
      IdCommand idCommand,
      PhoneCommand phoneCommand,
      PictureCommand pictureCommand,
      PriceCommand priceCommand,
      ResolutionCommand resolutionCommand,
      SimCommand simCommand) {
    this.dataRetrievalService = dataRetrievalService;
    this.announceDateCommand = announceDateCommand;
    this.audioJackCommand = audioJackCommand;
    this.batteryCommand = batteryCommand;
    this.brandCommand = brandCommand;
    this.gpsCommand = gpsCommand;
    this.idCommand = idCommand;
    this.phoneCommand = phoneCommand;
    this.pictureCommand = pictureCommand;
    this.priceCommand = priceCommand;
    this.resolutionCommand = resolutionCommand;
    this.simCommand = simCommand;
  }

  public List<Handset> processRequest(Map<String, String> queryParams)
      throws InvalidCriteriaException, ServiceException {

    List<Command> commandList = new ArrayList<>();
    queryParams.keySet().stream()
        .forEach(
            s -> {
              if (Constants.ANNOUNCE_DATE.equals(s)) {
                commandList.add(announceDateCommand);
              }
              if (Constants.AUDIO_JACK.equals(s)) {
                commandList.add(audioJackCommand);
              }
              if (Constants.BATTERY.equals(s)) {
                commandList.add(batteryCommand);
              }
              if (Constants.BRAND.equals(s)) {
                commandList.add(brandCommand);
              }
              if (Constants.GPS.equals(s)) {
                commandList.add(gpsCommand);
              }
              if (Constants.ID.equals(s)) {
                commandList.add(idCommand);
              }
              if (Constants.PHONE.equals(s)) {
                commandList.add(phoneCommand);
              }
              if (Constants.PICTURE.equals(s)) {
                commandList.add(pictureCommand);
              }
              if (Constants.PRICE_EUR.equals(s)) {
                commandList.add(priceCommand);
              }
              if (Constants.RESOLUTION.equals(s)) {
                commandList.add(resolutionCommand);
              }
              if (Constants.SIM.equals(s)) {
                commandList.add(simCommand);
              }
            });

    if (commandList.isEmpty()) {
      throw new InvalidCriteriaException("No valid criteria provided to filter records");
    } else {
      LOGGER.info("Executing search with " + commandList.size() + " filters");
      return execute(commandList, queryParams);
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
