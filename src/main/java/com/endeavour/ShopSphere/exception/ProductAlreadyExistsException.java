package com.endeavour.ShopSphere.exception;

public class ProductAlreadyExistsException extends RuntimeException
{
    public ProductAlreadyExistsException(String message)
    {
        super(message);
    }
}
