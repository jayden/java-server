package com.jayden.server;

import junit.framework.TestCase;

public class RedirectResponseTest extends TestCase
{
    private RedirectResponse response;

    public void setUp()
    {
        response = new RedirectResponse();
    }

    public void testRedirectStatus()
    {
        assertTrue(response.getStatus() == 307);
    }

    public void testHeaderValue()
    {
       assertEquals(response.getHeaderValue(), "http://localhost:5000/");
    }

}