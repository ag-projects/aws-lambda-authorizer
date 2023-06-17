package com.appsdeveloperblog.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.appsdeveloperblog.aws.lambda.service.CognitoUserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class GetUserByUsernameHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final CognitoUserService cognitoUserService;

    public GetUserByUsernameHandler() {
        this.cognitoUserService = new CognitoUserService("AWS_REGION");
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        response.setHeaders(header);

        String userName = input.getPathParameters().get("userName");
        String poolId = System.getenv("PHOTO_APP_USERS_POOL_ID");

        JsonObject userDetails;
        try {
            userDetails = cognitoUserService.getUserByUsername(userName, poolId);
            response.withBody(new Gson().toJson(userDetails, JsonObject.class));
            response.withStatusCode(200);
        } catch (Exception e) {
            response.setBody("{\n" +
                    "\t\"message\":\"e.exception\"\n" +
                    "}");
            response.withStatusCode(500);
            return response;
        }

        return response;
    }
}
