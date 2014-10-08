package com.jayden.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeResponse implements Response
{
    public String getResponse()
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

        return "Hello world: " + dateFormat.format(date);
    }
}