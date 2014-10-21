package com.jayden.server;

import junit.framework.TestCase;

public class OptionsResponseTest extends TestCase
{
    public void testHeaderContent()
    {
        OptionsResponse response = new OptionsResponse();
        assertEquals(response.getHeaderValue(), "GET,HEAD,POST,OPTIONS,PUT");
    }
}