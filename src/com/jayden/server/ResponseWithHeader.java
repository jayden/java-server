package com.jayden.server;

public interface ResponseWithHeader extends Response
{
    public String getHeader();
    public String getHeaderValue();
}
