package com.ezyfox.cvconnect.service;

public interface AuthenticationService {

    long verifyAccessToken(String accessToken);

    String generateAccessToken(long userId);

    void removeAccessToken(String accessToken);
}
