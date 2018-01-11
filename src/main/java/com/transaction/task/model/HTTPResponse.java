package com.transaction.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HTTPResponse {

    private String status;

    public HTTPResponse(String status){
        this.status = status;
    }

    @JsonProperty("status")
    public String getStatus() { return this.status; }



}
