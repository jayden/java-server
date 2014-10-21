package com.jayden.server;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.*;

public class RequestLoggerTest extends TestCase
{
    public void testRequestLogger() throws IOException
    {
        String testInput = "watbro";
        String logDirectory = System.getProperty("user.dir") + "/public/test_logs";
        RequestLogger logger = new RequestLogger(logDirectory);
        logger.logRequest(testInput);

        Path logPath = Paths.get(logDirectory);
        byte[] logContent = Files.readAllBytes(logPath);

        assertTrue(new String(logContent).contains(testInput));
    }
}