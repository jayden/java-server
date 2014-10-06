package com.jayden.server;

import java.io.*;
import java.util.HashMap;

public class RequestProcessor
{
    private final String SP = " ";
    private String[] requestArray;
    private String requestMethod;
    private String requestURI;
    private String requestProtocol;
    private InputStream inputStream;

    public RequestProcessor(InputStream inputStream) throws IOException
    {
        this.inputStream = inputStream;
        process();
    }

    public void process() throws IOException
    {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String requestLine = bufferedReader.readLine();
        requestArray = requestLine.split(SP);
        requestMethod = requestArray[0];
        requestURI = requestArray[1];
        requestProtocol = requestArray[2];
    }

    public HashMap<String, String> getRequest()
    {
        HashMap<String, String> processedRequest = new HashMap<String, String>();

        processedRequest.put("Method", requestMethod);
        processedRequest.put("URI", requestURI);
        processedRequest.put("Protocol", requestProtocol);

        return processedRequest;
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