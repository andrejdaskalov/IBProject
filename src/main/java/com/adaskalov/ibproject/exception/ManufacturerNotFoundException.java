package com.adaskalov.ibproject.exception;

public class ManufacturerNotFoundException extends Exception{
    public ManufacturerNotFoundException() {
        super("Invalid Manufacturer ID");
    }
}
