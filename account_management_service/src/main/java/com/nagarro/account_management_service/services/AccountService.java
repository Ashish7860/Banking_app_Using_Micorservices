package com.nagarro.account_management_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.account_management_service.models.Account;
import com.nagarro.account_management_service.models.AccountDetails;
import com.nagarro.account_management_service.models.Customer;
import com.nagarro.account_management_service.models.CustomerDetails;
import com.nagarro.account_management_service.models.TransactionRequest;
import com.nagarro.account_management_service.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {
	
	@Autowired
	private WebClient webClient;

    @Autowired
    private AccountRepository accountRepository;

    public void validateCustomerDetails(CustomerDetails customerDetails) {
        if (customerDetails == null || customerDetails.getCustomerId() == null || customerDetails.getCustomerId().isEmpty()) {
            throw new IllegalArgumentException("Invalid customer details");
        }
    }


    public void addMoney(TransactionRequest request) {
        validateCustomerDetails(request.getCustomerDetails());

        Optional<Account> optionalAccount = accountRepository.findById(request.getAccountId());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Check if account details match with the transaction request
            if (account.getCustomerId().equals(request.getCustomerDetails().getCustomerId()) &&
                    account.getCustomerName().equals(request.getCustomerDetails().getCustomerName())) {
                
                account.setBalance(account.getBalance().add(request.getAmount()));
                accountRepository.save(account);
            } else {
                throw new IllegalArgumentException("Transaction failed. Account details do not match with the provided customer details.");
            }
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    public void withdrawMoney(TransactionRequest request) {
        validateCustomerDetails(request.getCustomerDetails());

        Optional<Account> optionalAccount = accountRepository.findById(request.getAccountId());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Check if account details match with the transaction request
            if (account.getCustomerId().equals(request.getCustomerDetails().getCustomerId()) &&
                    account.getCustomerName().equals(request.getCustomerDetails().getCustomerName())) {

                if (account.getBalance().compareTo(request.getAmount()) >= 0) {
                    account.setBalance(account.getBalance().subtract(request.getAmount()));
                    accountRepository.save(account);
                } else {
                    throw new IllegalArgumentException("Insufficient funds");
                }
            } else {
                throw new IllegalArgumentException("Transaction failed. Account details do not match with the provided customer details.");
            }
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    
    public AccountDetails getAccountDetails(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            Customer customer = webClient.get()
                    .uri("http://localhost:8081/customers/" + account.getCustomerId())
                    .retrieve()
                    .bodyToMono(Customer.class)
                    .block(); // Block to get the result
            
            if (customer != null) {
                AccountDetails accountDetails = new AccountDetails();
                accountDetails.setAccountId(account.getAccountId());
                accountDetails.setBalance(account.getBalance());
                accountDetails.setCustomerId(customer.getId().toString());
                accountDetails.setCustomerName(customer.getFirst_name() + " " + customer.getLast_name());
                return accountDetails;
            } else {
                throw new IllegalArgumentException("Customer not found for Account ID: " + accountId);
            }
        } else {
            throw new IllegalArgumentException("Account not found for ID: " + accountId);
        }
    }

    public void deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    
    //Create a account considering the CustomerId
    
    public AccountDetails createAccount(CustomerDetails customerDetails, BigDecimal initialBalance) {
        validateCustomerDetails(customerDetails);
        
        // Check if the customer with the given ID exists
        if (!customerExists(customerDetails.getCustomerId())) {
            throw new RuntimeException("Customer with ID " + customerDetails.getCustomerId() + " not found. Account can't be created for the same");
        }
        
        // creating the account with the customerId
        Customer customer = webClient.get()
                .uri("http://localhost:8081/customers/" + customerDetails.getCustomerId())
                .retrieve()
                .bodyToMono(Customer.class) 
                .block();
        

        Account newAccount = new Account();
        newAccount.setCustomerId(customerDetails.getCustomerId());
        newAccount.setBalance(initialBalance);

        // Set the customer name in the new account
        newAccount.setCustomerName(customer.getFirst_name() + " " + customer.getLast_name());

        Account savedAccount = accountRepository.save(newAccount);

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountId(savedAccount.getAccountId());
        accountDetails.setBalance(savedAccount.getBalance());
        accountDetails.setCustomerId(savedAccount.getCustomerId());
        accountDetails.setCustomerName(customer.getFirst_name() + " " + customer.getLast_name());

        return accountDetails;
    }
    private boolean customerExists(String customerId) {
        try {
            webClient.get()
                    .uri("http://localhost:8081/customers/" + customerId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
