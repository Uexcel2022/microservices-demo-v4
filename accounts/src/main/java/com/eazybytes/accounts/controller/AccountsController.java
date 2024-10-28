package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Tag(
        name = "CRUD REST APIs FOR EazyBank",
        description = "CRUD REST APIs in EazyBank to CREAT, RETRIEVE, UPDATE AND DELETE Account details"
)
@RestController
//@AllArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    @Value("${build.version}")
    private String buildVersion;


    private final Environment env;
    private final IAccountService iAccountService;
    private final AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(Environment env, IAccountService iAccountService, AccountsContactInfoDto accountsContactInfoDto) {
        this.env = env;
        this.iAccountService = iAccountService;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Operation(
            summary = "REST API To Create Account ",
            description = "REST API to create new Customer and Account details inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = AccountConstants.STATUS_201_desc
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc ,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )

    @PostMapping(path = "/create-account")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){

        iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Details REST API",
            description = "REST API to fetch Customer and Account details  inside EazyBank",
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

    @GetMapping("/fetch-customer")
    public ResponseEntity<CustomerDto> updateAccount(@Valid @RequestParam("mobileNumber")
                                                         @Pattern(regexp = "0[7-9][01][0-9]{8}",
                                                                 message = "Not a valid Nigeria mobile number")
                                                         String mobileNumber
    ){

        return ResponseEntity.ok().body(iAccountService.getCustomerByMobileNumber(mobileNumber));
    }

    @Operation(
            summary = "Update Details REST API",
            description = "REST API to update Customer and Account details  inside EazyBank",
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

                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = AccountConstants.STATUS_417_desc
                    )
            }

    )
    @PutMapping("/update-account")
    public  ResponseEntity<ResponseDto> updateBankAccount(@Valid @RequestBody CustomerDto customerDto) {

        boolean isUpdated = iAccountService.updateCustomer(customerDto);

        if (isUpdated) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_Update));
        }
    }
    @Operation(
            summary = "Delete Details REST API",
            description = "REST API to delete Customer and Account details  inside EazyBank",
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
                            content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                      )
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = AccountConstants.STATUS_417_desc
                    )
            }

    )
    @DeleteMapping("/delete-account")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam("mobileNumber")
                                                         @Pattern(regexp = "^0[7-9][01][0-9]{8}$", message = "Invalid mobile number")
                                                         String mobileNumber
    ){
            Boolean isDeleted = iAccountService.deleteCustomer(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_Delete));
        }

    }

    @Operation(
            summary = "REST API to Fetch Build information",
            description = "REST API to fetch application buildVersion details  inside EazyBank",
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
    @GetMapping("/build-info")
    public ResponseEntity<Map<String,String>> getBuildVersion(){
        Map<String,String> versionInfo = new LinkedHashMap<>();
        versionInfo.put("Name","Eazy Bank Account Microservice");
        versionInfo.put("version", buildVersion);
        versionInfo.put("Build Date", "2024-10-20");
        return ResponseEntity.ok(versionInfo);
    }


    @Operation(
            summary = "REST API to Fetch JDK version",
            description = "REST API to fetch application JDK information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            example = "JDK: version"
                                    )
                            )
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
    @GetMapping("/java-version")
    public ResponseEntity<Map> getJavaVersionInfo(){
        Map<String,String> javaInfo = new  LinkedHashMap<>();
        javaInfo.put("JDK",env.getProperty("java.version"));
        return ResponseEntity.ok(javaInfo);
    }

    @Operation(
            summary = "REST API to Fetch Contact",
            description = "REST API to fetch  contact details in EazyBank",
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
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity.ok(accountsContactInfoDto);
    }


}
