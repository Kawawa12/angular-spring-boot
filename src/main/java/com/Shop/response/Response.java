package com.Shop.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private int status;

    private String message;

    private Long id;

    private String role;

    private String jwtToken;

    private String refToken;

    private byte[] imgData;

    public Response() {
        super();
    }

    public Response(int status, String message, Long id, String role, String jwtToken, String refToken) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.role = role;
        this.jwtToken = jwtToken;
        this.refToken = refToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefToken() {
        return refToken;
    }

    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }
}
