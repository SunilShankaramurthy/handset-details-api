package com.practice.mobile.command;

import com.practice.mobile.exception.InvalidCriteriaException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.Handset;
import com.practice.mobile.model.Hardware;
import com.practice.mobile.model.Release;
import com.practice.mobile.service.DataRetrievalService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {
  @Mock private DataRetrievalService mockDataRetrievalService;
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
            mockDataRetrievalService,
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
    queryParameters.put("announceDate", "1999");
    queryParameters.put("resolution", "chars");
    Mockito.when(mockDataRetrievalService.getHandsetRecords()).thenReturn(prepareHandsetList());
    List<Handset> handsetList = commandExecutor.processRequest(queryParameters);
    Assert.assertEquals(1, handsetList.size());
    Assert.assertEquals("Yes", handsetList.get(0).getHardware().getGps());
    Assert.assertEquals("Nano-SIM eSIM", handsetList.get(0).getSim());
    Assert.assertEquals("4525", handsetList.get(0).getId());
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

    Mockito.when(mockDataRetrievalService.getHandsetRecords()).thenReturn(prepareHandsetList());
    List<Handset> handsetList = commandExecutor.processRequest(queryParameters);
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

  private ArrayList<Handset> prepareHandsetList() {
    Handset handset1 = new Handset();
    handset1.setId("25846");
    handset1.setBrand("Apple");
    handset1.setPhone("Apple iPad Pro 12.9 (2018)");
    handset1.setPicture("https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg");
    handset1.setSim("Nano-SIM eSIM");
    handset1.setResolution("2048 x 2732 pixels");
    Hardware hardware1 = new Hardware();
    hardware1.setAudioJack("Yes");
    hardware1.setBattery("Lion battery");
    hardware1.setGps("Yes with A-GPS");
    Release release1 = new Release();
    release1.setPriceEur("150");
    release1.setAnnounceDate("2020");
    handset1.setRelease(release1);
    handset1.setHardware(hardware1);

    Handset handset2 = new Handset();
    handset2.setId("4525");
    handset2.setBrand("Lenovo");
    handset2.setPhone("Lenovo Amaze");
    handset2.setPicture("https://cdn2.gsmarena.com/vv/bigpic/lenovo-amaze.jpg");
    handset2.setSim("Nano-SIM eSIM");
    handset2.setResolution("2048 x 2732 chars");
    Hardware hardware2 = new Hardware();
    hardware2.setAudioJack("No");
    hardware2.setBattery("Li-po battery");
    hardware2.setGps("Yes");
    Release release2 = new Release();
    release2.setPriceEur("350");
    release2.setAnnounceDate("1999");
    handset2.setRelease(release2);
    handset2.setHardware(hardware2);

    ArrayList<Handset> handsetList = new ArrayList<>();
    handsetList.add(handset1);
    handsetList.add(handset2);
    return handsetList;
  }
}
