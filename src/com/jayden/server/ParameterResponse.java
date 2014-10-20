package com.jayden.server;

import java.util.*;

public class ParameterResponse implements Response
{
    private HashMap<String, String> request;
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

    public byte[] getResponse(HashMap<String, String> request)
    {
        this.request = request;
        String response = "";

        ArrayList<String> params = getParamList();
        for (String param : params)
        {
            response += param + "\n";
        }

        return response.getBytes();
    }

    public String getContentType() {
        return "text/plain";
    }

    public int getStatus() {
        return 200;
    }

    public String[] getSeparateParams()
    {
        String query = request.get("Parameters");
        return query.split("&");
    }

    public ArrayList<String> getParamList()
    {
        String[] params = getSeparateParams();
        ArrayList<String> paramList = new ArrayList<String>();
        for (String param : params)
        {
            String paramName = param.split("=")[0];
            String paramValue = param.split("=")[1];
            paramValue = decodeValue(paramValue);
            paramList.add(paramName + " = " + paramValue);
        }

        return paramList;
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
}
