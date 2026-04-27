package com.api.productengine.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    private final String productName;

    public ProductAlreadyExistsException(String productName) {
        super("Product already exists with name: " + productName);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
