package com.jayden.server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestLogger
{
    private String logDirectory;

    public RequestLogger(String logDirectory)
    {
        this.logDirectory = logDirectory;
    }

    public void logRequest(String request) throws IOException
    {
        PrintWriter writer = new PrintWriter(new FileWriter(logDirectory, true));
        writer.println(request);
        writer.close();
    }
}
