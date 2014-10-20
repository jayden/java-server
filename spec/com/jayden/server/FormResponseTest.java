package com.jayden.server;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FormResponseTest extends TestCase
{
    private FormResponse response;
    private String directory;
    private HashMap<String, String> request = new HashMap<String, String>();

    public void setUp()
    {
        directory = System.getProperty("user.dir") + "/public";
        response = new FormResponse(directory);
    }

    public void testGetResponse()
    {
        request.put("Method", "GET");
        request.put("URI", "/form");
        request.put("Protocol", "HTTP/1.1");

        assertEquals(response.getStatus(), 200);
    }

    public void testPutResponse() throws IOException
    {
        String body = "\"My\"=\"Data\"";
        request.put("Method", "PUT");
        request.put("Body", body);
        response.getResponse(request);

        assertEquals(getFileContents(), body);
    }

    public void testPostResponse() throws IOException
    {
        String body = "watbro";
        request.put("Method", "POST");
        request.put("Body", body);
        response.getResponse(request);

        assertEquals(getFileContents(), body);
    }

    public String getFileContents() throws IOException
    {
        Path filePath = Paths.get(directory + "/form");
        return new String(Files.readAllBytes(filePath));
    }

}