package com.jayden.server;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ResponseBuilder {

    private static final String SP = " ";
    private static final String CRLF = "\r\n";
    private static final String httpVersion = "HTTP/1.1";
    private int status = 200;
    private PrintStream writer;
    private String content = "";
    private static final HashMap<Integer, String> statusCodes = new HashMap<Integer, String>();
    private HashMap<String, String> headers = new HashMap<String, String>();
    static
    {
        statusCodes.put(200, "OK");
        statusCodes.put(404, "Not Found");
    }

    public ResponseBuilder(PrintStream writer)
    {
        this.writer = writer;
    }

    public void writeResponse()
    {
        String response = statusLine() + getHeader() + CRLF + getContent();
        writer.print(response);
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

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setDefaultHeader()
    {
        setHeader("Content-Type", "text/html;charset=utf-8");
        setHeader("Content-Length", Integer.toString(0));
        setHeader("Server", "Jayden");
    }

    public String currentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
