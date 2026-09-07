package com.group16.stardewvalley.model;


public record Result(boolean isSuccessful, String message) {

    public Result(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return message ;
    }
}
