package com.jayden.server;

import junit.framework.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeResponseTest extends TestCase
{
    private TimeResponse timeResponse;

    public void setUp()
    {
        timeResponse = new TimeResponse();
    }

    public void testGetResponse()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String expectedString = dateFormat.format(date);
        assertTrue(timeResponse.getResponse().contains(expectedString));
    }

}