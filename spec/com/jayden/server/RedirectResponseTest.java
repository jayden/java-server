package com.jayden.server;

import junit.framework.TestCase;

public class RedirectResponseTest extends TestCase
{
    public void testRedirectStatus()
    {
        RedirectResponse response = new RedirectResponse();
        assertTrue(response.getStatus() == 307);
    }
}