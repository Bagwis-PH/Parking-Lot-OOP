package com.parkinglot.exception;

public class NoAvailableSlotException extends ParkingException {
    public NoAvailableSlotException(String message) {
        super(message);
    }
}
