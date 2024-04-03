package com.example.investisment.exeption;

public class ImmobilierNotFoundException extends RuntimeException {
    public ImmobilierNotFoundException(String message) {
        super(message);
    }
}