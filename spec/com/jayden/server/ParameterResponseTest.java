package com.jayden.server;

import junit.framework.TestCase;
import java.util.*;

public class ParameterResponseTest extends TestCase
{
    private ParameterResponse response;
    private static HashMap<String, String> parameterRequest;
    static
    {
        parameterRequest = new HashMap<String, String>();
        parameterRequest.put("Method", "GET");
        parameterRequest.put("URI", "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C" +
                "%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");
        parameterRequest.put("Protocol", "HTTP/1.1");
    }
    private String requestURI = parameterRequest.get("URI");

    public void setUp()
    {
        response = new ParameterResponse();
    }

    public void testDecode()
    {
        requestURI = "%26%20%5D";
        response = new ParameterResponse();
        assertEquals(response.decodeValue(requestURI), "& ]");
    }

    public void testGetParamMap()
    {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("variable_1", "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?");
        paramMap.put("variable_2", "stuff");

        assertEquals(response.getParamMap(), paramMap);
    }

    /*
    public void testBuildResponse()
    {
        String expected = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?\n" + "variable_2 = stuff\n";
        assertEquals(response.buildResponse(), expected);
    }*/

}