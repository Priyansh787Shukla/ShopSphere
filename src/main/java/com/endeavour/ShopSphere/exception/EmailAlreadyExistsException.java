package com.endeavour.ShopSphere.exception;

public class EmailAlreadyExistsException extends RuntimeException
{
    public  EmailAlreadyExistsException(String message)
    {
        super(message);
    }
}
