package com.jayden.server;

import java.util.HashMap;

public class OptionsResponse implements ResponseWithHeader
{
    public byte[] getResponse(HashMap<String, String> request)
    {
        return new byte[0];
    }

    public String getHeader()
    {
        return "Allow";
    }

    public String getHeaderValue()
    {
        return "GET,HEAD,POST,OPTIONS,PUT";
    }

    public String getContentType()
    {
        return "text/plain";
    }

    public int getStatus()
    {
        return 200;
    }
}
