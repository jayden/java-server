package com.jayden.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


public class ResponseBuilder {

    private static final String SP = " ";
    private static final String CRLF = "\r\n";
    private static final String httpVersion = "HTTP/1.1";
    private int status = 404;
    private byte[] content = null;
    private String contentType;
    private static final HashMap<Integer, String> statusCodes = new HashMap<Integer, String>();
    private HashMap<String, String> headers = new HashMap<String, String>();
    private HashMap<String, String> request;
    private HashMap<String, Response> routes;

    static
    {
        statusCodes.put(200, "OK");
        statusCodes.put(401, "Unauthorized");
        statusCodes.put(404, "Not Found");
        statusCodes.put(405, "Method Not Allowed");
    }

    public ResponseBuilder(HashMap<String, String> request, HashMap<String, Response> routes)
    {
        this.request = request;
        this.routes = routes;
    }

    public byte[] getResponse()
    {
        if (routes.containsKey(request.get("URI")))
        {
            Response response = routes.get(request.get("URI"));
            setContent(response.getResponse(request));
            setStatus(response.getStatus());
            setContentType(response);
        }
        else
        {
            setContent("Error: This page doesn't exist!".getBytes());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            byte[] responseHeader = (statusLine() + getHeader() + CRLF).getBytes();
            byteArrayOutputStream.write(responseHeader);
            byteArrayOutputStream.write(getContent());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getStatus()
    {
        return this.status;
    }

    public String statusReason()
    {
        if (statusCodes.containsKey(status))
            return statusCodes.get(status);
        else
            return "";
    }

    public String statusLine()
    {
        return httpVersion + SP + getStatus() + SP + statusReason() + CRLF;
    }

    public void setHeader(String field, String value)
    {
        headers.put(field, value);
    }

    public String getHeader()
    {
        if (headers.size() == 0)
            setDefaultHeader();

        String header = "";
        for (String field : headers.keySet())
        {
            header += field + ": " + headers.get(field) + CRLF;
        }
        return header;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public byte[] getContent()
    {
        return content;
    }

    public void setDefaultHeader()
    {
        setHeader("Content-Type", contentType);
        setHeader("Content-Length", Integer.toString(getContent().length));
        setHeader("Server", "Jayden");
    }

    public void setContentType(Response response)
    {
        contentType = response.getContentType();
    }
}
