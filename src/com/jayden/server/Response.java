package com.jayden.server;

import java.util.HashMap;

public interface Response
{
    public byte[] getResponse(HashMap<String, String> request);
    public String getContentType();
    public int getStatus();
}
