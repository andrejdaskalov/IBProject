package com.adaskalov.ibproject.exception;

public class PrescriptionNotFoundException extends Exception{
    public PrescriptionNotFoundException() {
        super("Invalid Prescription ID.");
    }
}
