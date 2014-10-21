package com.jayden.server;

import junit.framework.*;
import java.io.*;
import java.util.HashMap;

public class RequestProcessorTest extends TestCase
{
    private InputStream mockReader(String request)
    {
        return new ByteArrayInputStream(request.getBytes());
    }

    public void testGetSplitRequest() throws IOException
    {
        String request = "GET / HTTP/1.1";
        RequestProcessor requestProcessor = new RequestProcessor(mockReader(request));
        requestProcessor.getRequest();

        assertEquals("GET", requestProcessor.getRequestMethod());
        assertEquals("/", requestProcessor.getRequestURI());
        assertEquals("HTTP/1.1", requestProcessor.getRequestProtocol());
    }

    public void testSplitRequestHeaders() throws IOException
    {
        String request = "GET / HTTP/1.1\nHost: watbro\nConnection: keep-alive\nUser-Agent: fake-agent";
        RequestProcessor requestProcessor = new RequestProcessor(mockReader(request));
        HashMap<String, String> requestMap = requestProcessor.getRequest();

        assertEquals(requestMap.get("Method"), "GET");
        assertEquals(requestMap.get("Host"), "watbro");
    }

    public void testSplitParametersFromURI() throws IOException
    {
        String request = "GET /location?name=value HTTP/1.1";
        RequestProcessor requestProcessor = new RequestProcessor(mockReader(request));
        HashMap<String, String> requestMap = requestProcessor.getRequest();

        assertEquals(requestMap.get("URI"), "/location");
        assertEquals(requestMap.get("Parameters"), "name=value");
    }

    public void testGetBody() throws IOException
    {
        String request = "GET / HTTP/1.1\nHost: watbro\nConnection: keep-alive\nUser-Agent: fake-agent\nContent-Length: 8\r\n\r\nsup bro?";
        RequestProcessor requestProcessor = new RequestProcessor(mockReader(request));
        HashMap<String, String> requestMap = requestProcessor.getRequest();

        assertEquals(requestMap.get("Body"), "sup bro?");
    }
}