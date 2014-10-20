package com.jayden.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FormResponse implements Response
{
    private int status = 200;
    private HashMap<String, String> request;
    private String directory;

    public FormResponse(String directory)
    {
        this.directory = directory;
    }

    public byte[] getResponse(HashMap<String, String> request) {
        this.request = request;
        byte[] response = "".getBytes();
        String filePath = directory + "/form";

        try
        {
            if (isGetRequest())
                response = Files.readAllBytes(Paths.get(filePath));
            else
                writeToFile(filePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    private boolean isGetRequest()
    {
        return getRequestMethod().equals("GET");
    }

    private void writeToFile(String filePath) throws IOException
    {
        PrintWriter printWriter = new PrintWriter(filePath, "UTF-8");
        printWriter.print(request.get("Body"));
        printWriter.close();
    }

    public String getContentType() {
        return "text/plain";
    }

    public int getStatus()
    {
        return status;
    }

    public String getRequestMethod()
    {
        return request.get("Method");
    }
}
