package com.practice.mobile.command;

import com.practice.mobile.exception.InvalidCriteriaException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.model.Hardware;
import com.practice.mobile.model.Release;
import com.practice.mobile.service.HandsetDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {
  @Mock private HandsetDetailsService mockHandsetDetailsService;
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
  private CommandExecutor commandExecutor;
  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    commandExecutor =
        new CommandExecutor(
            mockHandsetDetailsService,
            new AnnounceDateFilterCommand(),
            new AudioJackFilterCommand(),
            new BatteryFilterCommand(),
            new BrandFilterCommand(),
            new GPSFilterCommand(),
            new IdFilterCommand(),
            new PhoneFilterCommand(),
            new PictureFilterCommand(),
            new PriceFilterCommand(),
            new ResolutionFilterCommand(),
            new SimFilterCommand());
  }

  @Test
  public void testProcessRequest() throws InvalidCriteriaException, ServiceException {
    HashMap<String, String> queryParameters = new HashMap<>();
    queryParameters.put("announceDate", "2020");
    queryParameters.put("resolution", "pixels");
    Mockito.when(mockHandsetDetailsService.searchByCriteria(Mockito.any(Query.class)))
        .thenReturn(prepareHandsetList());
    List<HandsetDetails> handsetList = commandExecutor.processRequest(queryParameters);
    Assert.assertEquals(1, handsetList.size());
    Assert.assertEquals("Yes with A-GPS", handsetList.get(0).getHardware().getGps());
    Assert.assertEquals("25846", handsetList.get(0).getId());
  }

  @Test
  public void testProcessRequestWithMultipleQueryParams()
      throws InvalidCriteriaException, ServiceException {
    HashMap<String, String> queryParameters = new HashMap<>();
    queryParameters.put("id", "25846");
    queryParameters.put("brand", "Apple");
    queryParameters.put("phone", "Apple iPad Pro 12.9 (2018)");
    queryParameters.put(
        "picture", "https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg");
    queryParameters.put("sim", "Nano-SIM eSIM");
    queryParameters.put("audioJack", "Yes");
    queryParameters.put("battery", "Lion battery");
    queryParameters.put("priceEur", "150");
    queryParameters.put("gps", "Yes with A-GPS");
    Mockito.when(mockHandsetDetailsService.searchByCriteria(Mockito.any(Query.class)))
        .thenReturn(prepareHandsetList());
    List<HandsetDetails> handsetList = commandExecutor.processRequest(queryParameters);
    Assert.assertEquals(1, handsetList.size());
    Assert.assertEquals("Yes", handsetList.get(0).getHardware().getAudioJack());
    Assert.assertEquals("Apple iPad Pro 12.9 (2018)", handsetList.get(0).getPhone());
    Assert.assertEquals("150", handsetList.get(0).getRelease().getPriceEur());
  }

  @Test
  public void testProcessRequestWithInvalidFilters()
      throws InvalidCriteriaException, ServiceException {
    HashMap<String, String> queryParameters = new HashMap<>();
    queryParameters.put("make", "nokia");
    expectedException.expect(InvalidCriteriaException.class);
    expectedException.expectMessage("No valid criteria provided to filter records");
    commandExecutor.processRequest(queryParameters);
  }

  private ArrayList<HandsetDetails> prepareHandsetList() {
    HandsetDetails handsetDetails1 = new HandsetDetails();
    handsetDetails1.setId("25846");
    handsetDetails1.setBrand("Apple");
    handsetDetails1.setPhone("Apple iPad Pro 12.9 (2018)");
    handsetDetails1.setPicture("https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg");
    handsetDetails1.setSim("Nano-SIM eSIM");
    handsetDetails1.setResolution("2048 x 2732 pixels");
    Hardware hardware1 = new Hardware();
    hardware1.setAudioJack("Yes");
    hardware1.setBattery("Lion battery");
    hardware1.setGps("Yes with A-GPS");
    Release release1 = new Release();
    release1.setPriceEur("150");
    release1.setAnnounceDate("2020");
    handsetDetails1.setRelease(release1);
    handsetDetails1.setHardware(hardware1);

    ArrayList<HandsetDetails> handsetList = new ArrayList<>();
    handsetList.add(handsetDetails1);
    return handsetList;
  }
}
