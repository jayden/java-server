package com.jayden.server;

import junit.framework.TestCase;
import java.util.*;

public class AuthResponseTest extends TestCase
{
    private static final HashMap<String, String> authorizedRequest;
    static
    {
        authorizedRequest = new HashMap<String, String>();
        authorizedRequest.put("Method", "GET");
        authorizedRequest.put("URI", "/logs");
        authorizedRequest.put("Protocol,", "HTTP/1.1");
        authorizedRequest.put("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        authorizedRequest.put("Connection", "close");
        authorizedRequest.put("Host", "localhost:5000");
    }

    private static final HashMap<String, String> unauthorizedRequest;
    static
    {
        unauthorizedRequest = new HashMap<String, String>();
        unauthorizedRequest.put("Method", "GET");
        unauthorizedRequest.put("URI", "/logs");
        unauthorizedRequest.put("Protocol,", "HTTP/1.1");
        unauthorizedRequest.put("Connection", "close");
        unauthorizedRequest.put("Host", "localhost:5000");
    }
    private AuthResponse response;

    public void setUp()
    {
        response = new AuthResponse(System.getProperty("user.dir") + "/public");
    }

    public void testIsAuthenticated()
    {
        response.setRequest(unauthorizedRequest);
        assertFalse(response.isAuthenticated());

        response.setRequest(authorizedRequest);
        assertTrue(response.isAuthenticated());
    }

    public void testDecodeCredentials()
    {
        String encodedCredentials = authorizedRequest.get("Authorization").split(" ")[1];
        String credentials = response.decodedCredentials(encodedCredentials);
        assertEquals(credentials, "admin:hunter2");
    }

    public void testGetResponse()
    {
        byte[] unauthorizedResponse = response.getResponse(unauthorizedRequest);
        assertEquals("Authentication required", new String(unauthorizedResponse));
        assertEquals(response.getStatus(), 401);


        byte[] authorizedResponse = response.getResponse(authorizedRequest);
        assertTrue(new String(authorizedResponse).contains("GET /wat HTTP/1.1"));
        assertEquals(response.getStatus(), 200);
    }
}