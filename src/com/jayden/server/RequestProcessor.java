package com.jayden.server;

import java.io.*;
import java.util.HashMap;

public class RequestProcessor
{
    private final String SP = " ";
    private String requestMethod;
    private String requestURI;
    private String requestProtocol;
    private String requestParameters = "";
    private InputStream inputStream;
    private BufferedReader bufferedReader;

    public RequestProcessor(InputStream inputStream) throws IOException
    {
        this.inputStream = inputStream;
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

    public HashMap<String, String> getRequest()
    {
        HashMap<String, String> processedRequest = new HashMap<String, String>();

        processedRequest.put("Method", requestMethod);
        processedRequest.put("URI", requestURI);
        processedRequest.put("Parameters", requestParameters);
        processedRequest.put("Protocol", requestProtocol);

        try
        {
            while (!bufferedReader.ready())
            {
                Thread.sleep(100);
            }

            while (bufferedReader.ready())
            {
                String requestLine = bufferedReader.readLine();
                String[] requestArray = requestLine.split(SP);

                System.out.println(requestLine);

                if (requestArray.length > 1)
                {
                    processedRequest.put(requestArray[0], requestArray[1]);
                    if (requestArray[0].equals("Authorization:"))
                        processedRequest.put("Authorization:", requestArray[2]);
                }
            }

            System.out.println("#####END OF REQUEST#####\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

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