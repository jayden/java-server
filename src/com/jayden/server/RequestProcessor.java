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
        processedRequest = new HashMap<String, String>();

        processedRequest.put("Method", requestMethod);
        processedRequest.put("URI", requestURI);
        processedRequest.put("Parameters", requestParameters);
        processedRequest.put("Protocol", requestProtocol);

        try
        {
            String requestLine = bufferedReader.readLine();
            while (readableRequestLine(requestLine))
            {
                String[] requestArray = requestLine.split(": ");

                if (requestArray.length > 1)
                    processedRequest.put(requestArray[0], requestArray[1]);

                requestLine = bufferedReader.readLine();
            }

            if (processedRequest.containsKey("Content-Length"))
            {
                int contentLength = Integer.parseInt(processedRequest.get("Content-Length"));
                requestBody = getBody(contentLength);
            }

            processedRequest.put("Body", requestBody);

            for (Map.Entry<String, String> entry : processedRequest.entrySet())
                System.out.println(entry.getKey() + " : " + entry.getValue());

            System.out.println("#####END OF REQUEST#####\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return processedRequest;
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

    public void process() throws IOException
    {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            while (!bufferedReader.ready())
                Thread.sleep(100);

            String requestLine = bufferedReader.readLine();

            System.out.println("\n#####INCOMING REQUEST#####\n");
            System.out.println(requestLine);

            String[] requestArray = requestLine.split(SP);
            requestMethod = requestArray[0];
            requestURI = requestArray[1];
            requestProtocol = requestArray[2];

            if (requestURI.contains("?"))
            {
                String URI = requestURI.split("\\?")[0];
                requestParameters = requestURI.split("\\?")[1];
                requestURI = URI;
            }

        }
        catch (InterruptedException e)
        {
        }
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