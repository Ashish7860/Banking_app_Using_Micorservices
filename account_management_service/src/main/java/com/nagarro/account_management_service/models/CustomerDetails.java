package com.nagarro.account_management_service.models;
import jakarta.persistence.Id;


public class CustomerDetails {
    @Id
    private String customerId;
    private String customerName;

    public CustomerDetails() {
        // Default constructor
    }

    // Getters and setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
