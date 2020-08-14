package com.practice.mobile.repository.impl;

import com.practice.mobile.model.HandsetDetails;
import com.practice.mobile.model.Hardware;
import com.practice.mobile.model.Release;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@RunWith(MockitoJUnitRunner.class)
public class HandsetDetailsDAOImplTest {

  @Mock private MongoTemplate mockMongoTemplate;

  private HandsetDetailsDAOImpl handsetDetailsDAOImpl;

  @Before
  public void setUp() {
    handsetDetailsDAOImpl = new HandsetDetailsDAOImpl(mockMongoTemplate);
  }

  @Test
  public void testSearchByCriteria() {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(25846));
    Mockito.when(mockMongoTemplate.find(Mockito.any(Query.class), Mockito.any()))
        .thenReturn(handsetDetailsList());
    List<HandsetDetails> handsetDetailsList = handsetDetailsDAOImpl.searchByCriteria(query);
    Assert.assertNotNull(handsetDetailsList);
    Assert.assertEquals(1, handsetDetailsList.size());
    Assert.assertEquals("25846", handsetDetailsList.get(0).getId());
  }

  private List<Object> handsetDetailsList() {
    HandsetDetails handsetDetails = new HandsetDetails();
    handsetDetails.setId("25846");
    handsetDetails.setBrand("Lenovo");
    handsetDetails.setPhone("Lenovo Amaze");
    handsetDetails.setPicture("https://cdn2.gsmarena.com/vv/bigpic/lenovo-amaze.jpg");
    handsetDetails.setSim("Nano-SIM eSIM");
    handsetDetails.setResolution("2048 x 2732 chars");
    Hardware hardware = new Hardware();
    hardware.setAudioJack("No");
    hardware.setBattery("Li-po battery");
    hardware.setGps("Yes");
    Release release = new Release();
    release.setPriceEur("350");
    release.setAnnounceDate("1999");
    handsetDetails.setRelease(release);
    handsetDetails.setHardware(hardware);

    ArrayList<Object> handsetList = new ArrayList<>();
    handsetList.add(handsetDetails);
    return handsetList;
  }
}
