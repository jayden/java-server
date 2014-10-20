package com.jayden.server;

import java.util.HashMap;

public class RedirectResponse implements Response
{
    public byte[] getResponse(HashMap<String, String> request)
    {
        return new byte[0];
    }

    public String getHeader()
    {
        return "Location";
    }

    public String getHeaderValue()
    {
        return "http://localhost:5000/";
    }

    public String getContentType()
    {
       return "text/plain";
    }

    public int getStatus()
    {
        return 307;
    }
}
