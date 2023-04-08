package com.example.vendor.exceptions;

public class VendorNotFoundException extends RuntimeException{

    public VendorNotFoundException(String exception) {
        super(exception);
    }
}
