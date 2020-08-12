package com.practice.mobile.endpoint;

import com.practice.mobile.command.CommandExecutor;
import com.practice.mobile.exception.InvalidCriteriaException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.Handset;
import com.practice.mobile.model.Hardware;
import com.practice.mobile.model.Release;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class SearchEndpointTest {
  @Mock private CommandExecutor mockCommandExecutor;

  private SearchEndpoint searchEndpoint;

  @Before
  public void setUp() {
    searchEndpoint = new SearchEndpoint(mockCommandExecutor);
  }

  @Test
  public void testSearchByPriceValid() throws ServiceException, InvalidCriteriaException {
    HashMap<String, String> queryParameters = new HashMap<>();
    queryParameters.put("announceDate", "1999");
    queryParameters.put("resolution", "chars");
    Mockito.when(mockCommandExecutor.processRequest(Mockito.anyMap()))
        .thenReturn(filteredHandsetList());
    ResponseEntity responseEntity = searchEndpoint.searchByPrice(queryParameters);
    Assert.assertNotNull("Response from SearchEndpoint is Null", responseEntity);
    Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    ArrayList<Handset> responseList = (ArrayList<Handset>) responseEntity.getBody();
    Assert.assertEquals(1, responseList.size());
  }

  private ArrayList<Handset> filteredHandsetList() {
    Handset handset = new Handset();
    handset.setId("4525");
    handset.setBrand("Lenovo");
    handset.setPhone("Lenovo Amaze");
    handset.setPicture("https://cdn2.gsmarena.com/vv/bigpic/lenovo-amaze.jpg");
    handset.setSim("Nano-SIM eSIM");
    handset.setResolution("2048 x 2732 chars");
    Hardware hardware2 = new Hardware();
    hardware2.setAudioJack("No");
    hardware2.setBattery("Li-po battery");
    hardware2.setGps("Yes");
    Release release2 = new Release();
    release2.setPriceEur("350");
    release2.setAnnounceDate("1999");
    handset.setRelease(release2);
    handset.setHardware(hardware2);

    ArrayList<Handset> handsetList = new ArrayList<>();
    handsetList.add(handset);
    return handsetList;
  }
}
