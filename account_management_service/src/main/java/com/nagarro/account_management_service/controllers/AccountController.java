package com.nagarro.account_management_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nagarro.account_management_service.models.AccountDetails;
import com.nagarro.account_management_service.models.CustomerDetails;
import com.nagarro.account_management_service.models.TransactionRequest;
import com.nagarro.account_management_service.services.AccountService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/addMoney")
    public ResponseEntity<String> addMoney(@RequestBody TransactionRequest request) {
        try {
            accountService.validateCustomerDetails(request.getCustomerDetails());
            accountService.addMoney(request);
            return ResponseEntity.ok("Money added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/withdrawMoney")
    public ResponseEntity<String> withdrawMoney(@RequestBody TransactionRequest request) {
        try {
            accountService.validateCustomerDetails(request.getCustomerDetails());
            accountService.withdrawMoney(request);
            return ResponseEntity.ok("Money withdrawn successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAccountDetails/{accountId}")
    public ResponseEntity<?> getAccountDetails(@PathVariable Long accountId) {
        try {
            AccountDetails accountDetails = accountService.getAccountDetails(accountId);
            return ResponseEntity.ok(accountDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.ok("Account deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //  to create a new account
    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody CustomerDetails customerDetails,
                                                        @RequestParam BigDecimal initialBalance) {
        try {
            AccountDetails createdAccount = accountService.createAccount(customerDetails, initialBalance);
            return ResponseEntity.ok(createdAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
