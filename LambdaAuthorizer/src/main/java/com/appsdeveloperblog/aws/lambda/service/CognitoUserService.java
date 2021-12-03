package com.appsdeveloperblog.aws.lambda.service;

import com.google.gson.JsonObject;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;

public class CognitoUserService {

    public JsonObject getUserByUsername(String userName, String poolId) {
        AdminGetUserRequest  adminGetUserRequest = AdminGetUserRequest.builder()
                .username(userName)
                .userPoolId(poolId)
                .build();

        return null;
    }
}
