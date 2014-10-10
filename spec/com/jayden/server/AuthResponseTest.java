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
        authorizedRequest.put("Authorization:", "YWRtaW46aHVudGVyMg==");
        authorizedRequest.put("Connection:", "close");
        authorizedRequest.put("Host:", "localhost:5000");
    }

    private static final HashMap<String, String> unauthorizedRequest;
    static
    {
        unauthorizedRequest = new HashMap<String, String>();
        unauthorizedRequest.put("Method", "GET");
        unauthorizedRequest.put("URI", "/logs");
        unauthorizedRequest.put("Protocol,", "HTTP/1.1");
        unauthorizedRequest.put("Connection:", "close");
        unauthorizedRequest.put("Host:", "localhost:5000");
    }
    private AuthResponse response;

    public void setUp()
    {
        response = new AuthResponse("/public");
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
        String credentials = response.decodedCredentials(authorizedRequest.get("Authorization:"));
        assertEquals(credentials, "admin:hunter2");
    }

    public void testGetResponse()
    {
        byte[] unauthorizedResponse = response.getResponse(unauthorizedRequest);
        assertEquals("Authentication required", new String(unauthorizedResponse));
        assertEquals(response.getStatus(), 401);


        byte[] authorizedResponse = response.getResponse(authorizedRequest);
        assertEquals("GET /log HTTP/1.1\n" +
                "PUT /these HTTP/1.1\n" +
                "HEAD /requests HTTP/1.1", new String(authorizedResponse));
        assertEquals(response.getStatus(), 200);
    }


}