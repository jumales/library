package com.jumales.library.rest.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class StatusDTO {

    @Setter(AccessLevel.NONE)
    private int code;
    @Setter(AccessLevel.NONE)
    private String shortMsg;
    @Setter(AccessLevel.NONE)
    private String longMsg;

    private StatusDTO(int code, String shortMsg, String longMsg){
        this.code = code;
        this.shortMsg = shortMsg;
        this.longMsg = longMsg;
    }

    public static StatusDTO success(){
        return new StatusDTO(200, "OK", null);
    }

    public static StatusDTO success(String message) {
        return new StatusDTO(200, "OK", message);
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
}