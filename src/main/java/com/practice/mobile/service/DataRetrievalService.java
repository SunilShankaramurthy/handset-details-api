package com.practice.mobile.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.Handset;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * DataRetrievalService helps in calling Mobile handset service to pull the list of handset details.
 */
@Service
public class DataRetrievalService {
  private static final Logger LOGGER = LogManager.getLogger(DataRetrievalService.class);

  private RestTemplate restTemplate;
  private Type handsetList;
  private Gson gson;
  private String handsetServiceURL;

  public DataRetrievalService(
      RestTemplate restTemplate, @Value("${handsetService}") String handsetServiceURL) {
    gson = new Gson();
    handsetList = new TypeToken<List<Handset>>() {}.getType();
    this.restTemplate = restTemplate;
    this.handsetServiceURL = handsetServiceURL;
  }

  public List<Handset> getHandsetRecords() throws ServiceException {
    List<Handset> handsetResponseList;
    try {
      ResponseEntity<String> response = restTemplate.getForEntity(handsetServiceURL, String.class);
      handsetResponseList = gson.fromJson(response.getBody(), handsetList);
      LOGGER.info("Retrieved response from HandsetService");
    } catch (ResourceAccessException exception) {
      LOGGER.error("I/O error on request", exception);
      throw new ServiceException(exception.getMessage());
    } catch (RestClientException exception) {
      LOGGER.error("RestClient Exception occurred while sending request", exception);
      throw new ServiceException(exception.getMessage());
    } catch (Exception exception) {
      LOGGER.error("Exception occurred while sending request", exception);
      throw new ServiceException(exception.getMessage());
    }
    return handsetResponseList;
  }
}
