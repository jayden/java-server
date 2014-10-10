package com.jayden.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeResponse implements Response
{
    public byte[] getResponse(HashMap<String, String> request)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        try
        {
            Thread.sleep(4000);
        }
        catch(InterruptedException e)
        {
        }

        return ("Hello world: " + dateFormat.format(date)).getBytes();
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