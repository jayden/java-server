package com.jayden.server;

import junit.framework.TestCase;
import java.util.*;

public class FormResponseTest extends TestCase
{
    public void testCheckMethod()
    {
        FormResponse response = new FormResponse("directory");
        HashMap<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("Method", "PUT");
        requestMap.put("URI", "/form");
        requestMap.put("Protocol", "HTTP/1.1");
        requestMap.put("Parameters", "");

        String method = response.getRequestMethod(requestMap);
        assertEquals(method, "PUT");
    }

    public void testGetBody()
    {

    }
}