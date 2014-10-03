package com.jayden.server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestProcessor
{
    private final String SP = " ";
    private String[] requestArray;
    private String requestMethod;
    private String requestURI;
    private String requestProtocol;

    public RequestProcessor(BufferedReader reader) throws IOException
    {
        process(reader);
    }

    public void process(BufferedReader reader) throws IOException
    {
        String requestLine = reader.readLine();
        requestArray = requestLine.split(SP);
        requestMethod = requestArray[0];
        requestURI = requestArray[1];
        requestProtocol = requestArray[2];

    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public String getRequestURI()
    {
        return requestURI;
    }

    public String getRequestProtocol()
    {
        return requestProtocol;
    }
}