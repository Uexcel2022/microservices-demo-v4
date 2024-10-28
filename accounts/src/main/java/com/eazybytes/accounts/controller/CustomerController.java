package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(
        name = "REST API to fetch Customer Details in EazyBank",
        description = "REST API in EazyBank to  RETRIEVE Customer details"
)
@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer, Account, Loan and Card details  inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )

    @GetMapping("/customerDetails")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestParam String mobileNumber) {
       return ResponseEntity.ok().body(customerService.getCustomerDetails(mobileNumber));

    }
}
