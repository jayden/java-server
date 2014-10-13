package com.jayden.server;

import java.util.HashMap;
import java.util.Map;

public class ParameterResponse implements Response
{
    private static final HashMap<String, String> encodingMap;
    static
    {
        encodingMap = new HashMap<String, String>();
        encodingMap.put("%20", " ");
        encodingMap.put("%21", "!");
        encodingMap.put("%22", "\"");
        encodingMap.put("%23", "#");
        encodingMap.put("%24", "$");
        encodingMap.put("%26", "&");
        encodingMap.put("%2A", "*");
        encodingMap.put("%2B", "+");
        encodingMap.put("%2C", ",");
        encodingMap.put("%2D", "-");
        encodingMap.put("%3A", ":");
        encodingMap.put("%3B", ";");
        encodingMap.put("%3C", "<");
        encodingMap.put("%3D", "=");
        encodingMap.put("%3E", ">");
        encodingMap.put("%3F", "?");
        encodingMap.put("%40", "@");
        encodingMap.put("%5B", "[");
        encodingMap.put("%5D", "]");
    }
    private HashMap<String, String> request;

    public byte[] getResponse(HashMap<String, String> request)
    {
        this.request = request;
        return buildResponse().getBytes();
    }

    public String getContentType() {
        return "text/plain";
    }

    public int getStatus() {
        return 0;
    }

    public String[] getSeparateParams()
    {
        String query = request.get("Parameters");
        return query.split("&");
    }

    public HashMap<String, String> getParamMap()
    {
        String[] params = getSeparateParams();
        HashMap<String, String> paramMap = new HashMap<String, String>();
        for (String param : params)
        {
            String paramName = param.split("=")[0];
            String paramValue = param.split("=")[1];
            paramValue = decodeValue(paramValue);
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }

    public String decodeValue(String value)
    {
        for (Map.Entry<String, String> entry : encodingMap.entrySet())
        {
            String encoding = entry.getKey();
            String asciiValue = entry.getValue();
            value = value.replace(encoding, asciiValue);
        }
        return value;
    }

    public String buildResponse()
    {
        String response = "";
        HashMap<String, String> params = getParamMap();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            response += entry.getKey() + " = " + entry.getValue() + "\n";
        }

        return response;
    }
}
