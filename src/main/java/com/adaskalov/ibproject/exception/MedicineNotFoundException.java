package com.adaskalov.ibproject.exception;

public class MedicineNotFoundException extends Exception{
    public MedicineNotFoundException() {
        super("Invalid Medicine ID");
    }
}
