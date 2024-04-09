package com.eliasfs06.spring.stockmanager.model.enums;

public enum RequestStatus {
    ACCEPT("Accepted"),
    REJECT("Rejected"),
    WAITING("Waiting");

    private String description;

    RequestStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
