package com.jayden.server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Worker implements Runnable
{
    private Socket clientSocket = null;
    private HashMap<String, Response> routes;

    public Worker(Socket clientSocket, HashMap<String, Response> routes)
    {
        this.clientSocket = clientSocket;
        this.routes = routes;
    }

    public void run()
    {
        try
        {
            RequestProcessor requestProcessor = new RequestProcessor(getInputStream());
            requestProcessor.process();
            HashMap<String, String> request = requestProcessor.getRequest();

            byte[] response = new ResponseBuilder(request, this.routes).getResponse();
            getOutputStream().write(response);
            clientSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() throws IOException
    {
        return clientSocket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException
    {
        return clientSocket.getOutputStream();
    }
}