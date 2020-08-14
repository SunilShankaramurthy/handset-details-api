package com.practice.mobile.endpoint;

import com.practice.mobile.command.CommandExecutor;
import com.practice.mobile.exception.InvalidCriteriaException;
import com.practice.mobile.exception.ServiceException;
import com.practice.mobile.model.HandsetDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Mobile Service")
@RestController
@RequestMapping(value = "/mobile")
public class SearchEndpoint {

  private CommandExecutor commandExecutor;

  public SearchEndpoint(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @ApiOperation(
      value =
          "Allows consumers to filter Handset Details based on criteria provided as query parameters",
      produces = "application/json",
      httpMethod = "GET")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successfully retrieved handset details with the filters provided",
            response = HandsetDetails.class),
        @ApiResponse(code = 400, message = "No records found for specified filters"),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/search")
  public ResponseEntity searchByCriteria(@RequestParam Map<String, String> queryParameters)
      throws ServiceException, InvalidCriteriaException {
    List<HandsetDetails> filteredHandsetList = commandExecutor.processRequest(queryParameters);
    return ResponseEntity.status(HttpStatus.OK).body(filteredHandsetList);
  }
}
