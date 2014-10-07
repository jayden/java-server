package com.jayden.server;

import junit.framework.*;
import java.io.*;

public class RequestProcessorTest extends TestCase
{
    private static final String GET_REQUEST = "GET / HTTP/1.1";
    private RequestProcessor requestProcessor;

    public void setUp() throws IOException
    {
        requestProcessor = new RequestProcessor(mockReader(GET_REQUEST));
    }

    private InputStream mockReader(String request)
    {
        return new ByteArrayInputStream(request.getBytes());
    }

    public void testGetSplitRequest() throws IOException
    {
        requestProcessor.process();
        assertEquals("GET", requestProcessor.getRequestMethod());
        assertEquals("/", requestProcessor.getRequestURI());
        assertEquals("HTTP/1.1", requestProcessor.getRequestProtocol());
    }
}