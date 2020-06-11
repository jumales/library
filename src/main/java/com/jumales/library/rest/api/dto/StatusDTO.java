package com.jumales.library.rest.api.dto;

public class StatusDTO {

    private int code;
    private String shortMsg;
    private String longMsg;

    private StatusDTO(int code, String shortMsg, String longMsg){
        this.code = code;
        this.shortMsg = shortMsg;
        this.longMsg = longMsg;
    }

    public static StatusDTO success(){
        return new StatusDTO(200, "OK", null);
    }

    public static StatusDTO created(){
        return new StatusDTO(201, "Created", null);
    }

    public static StatusDTO noContent(String message){
        return new StatusDTO(204, "No content", message);
    }

    public static StatusDTO badRequest(String message){
        return new StatusDTO(400, "Bad request", message);
    }

    public static StatusDTO unauthorized(String message){
        return new StatusDTO(401, "Unauthorized", message);
    }

    public int getCode() {
        return code;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public String getLongMsg() {
        return longMsg;
    }
}