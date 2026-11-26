package br.checker.monitor.dto;

public class RefreshTokenResponse {
    private String token;
    private String message;

    public RefreshTokenResponse() {
    }

    public RefreshTokenResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}