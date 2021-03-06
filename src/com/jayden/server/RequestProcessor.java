package com.jayden.server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestProcessor
{
    private final String SP = " ";
    private String requestMethod;
    private String requestURI;
    private String requestProtocol;
    private String requestParameters = "";
    private String requestBody = "";
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private HashMap<String, String> processedRequest;

    public RequestProcessor(InputStream inputStream) throws IOException
    {
        this.inputStream = inputStream;
    }


    public HashMap<String, String> getRequest()
    {
        try
        {
            String requestLine = processFirstLineOfRequest();
            initializeRequestMap();
            splitRequestHeader();

            if (processedRequest.containsKey("Content-Length"))
            {
                int contentLength = Integer.parseInt(processedRequest.get("Content-Length"));
                requestBody = getBody(contentLength);
            }

            processedRequest.put("Body", requestBody);
            printRequestToConsole(requestLine);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return processedRequest;
    }

    private void initializeRequestMap()
    {
        processedRequest = new HashMap<String, String>();

        processedRequest.put("Method", requestMethod);
        processedRequest.put("URI", requestURI);
        processedRequest.put("Parameters", requestParameters);
        processedRequest.put("Protocol", requestProtocol);
    }

    public String processFirstLineOfRequest() throws IOException
    {
        String requestLine = "";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            while (!bufferedReader.ready())
                Thread.sleep(100);

            requestLine = bufferedReader.readLine();
            splitRequestLine(requestLine);
            splitParameterFromURI();
        }
        catch (InterruptedException e)
        {
        }

        return requestLine;
    }

    public void splitRequestLine(String requestLine)
    {
        String[] requestArray = requestLine.split(SP);
        requestMethod = requestArray[0];
        requestURI = requestArray[1];
        requestProtocol = requestArray[2];
    }

    public void splitParameterFromURI()
    {
        if (requestURI.contains("?"))
        {
            String URI = requestURI.split("\\?")[0];
            requestParameters = requestURI.split("\\?")[1];
            requestURI = URI;
        }
    }

    private void printRequestToConsole(String requestLine)
    {
        System.out.println("#####INCOMING REQUEST#####");
        System.out.println(requestLine);

        for (Map.Entry<String, String> entry : processedRequest.entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue());

        System.out.println("#####END OF REQUEST#####\n");
    }

    public void splitRequestHeader() throws IOException
    {
        String requestLine = bufferedReader.readLine();

        while (readableRequestLine(requestLine))
        {
            String[] requestArray = requestLine.split(": ");
            if (requestArray.length > 1)
                processedRequest.put(requestArray[0], requestArray[1]);
            requestLine = bufferedReader.readLine();
        }
    }

    public boolean readableRequestLine(String requestLine)
    {
        return (requestLine != null && !requestLine.equals(""));
    }

    public String getBody(int contentLength) throws IOException
    {
        char[] characters = new char[contentLength];
        StringBuilder postContent = new StringBuilder();
        bufferedReader.read(characters, 0, contentLength);
        postContent.append(new String(characters));
        return postContent.toString();
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

    public String getRequestString()
    {
        return requestMethod + SP + requestURI + SP + requestProtocol;
    }
}