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

    private BufferedReader mockReader(String request)
    {
        return new BufferedReader(new StringReader(request));
    }

    public void testGetSplitRequest()
    {
        assertEquals("GET", requestProcessor.getRequestMethod());
        assertEquals("/", requestProcessor.getRequestURI());
        assertEquals("HTTP/1.1", requestProcessor.getRequestProtocol());
    }


}