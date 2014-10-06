package com.jayden.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeResponse extends Response
{
    public String getResponse()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return "Hello World! " + dateFormat.format(date);
    }
}