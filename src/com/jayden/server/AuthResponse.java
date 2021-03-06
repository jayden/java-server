package com.jayden.server;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AuthResponse implements Response {
    private HashMap<String, String> request;
    private final String AUTHORIZED_CREDENTIALS = "admin:hunter2";
    private String directory;
    private int status;

    public AuthResponse(String directory) {
        this.directory = directory + "/logs";
    }

    public byte[] getResponse(HashMap<String, String> request) {
        this.request = request;

        if (isAuthenticated()) {
            this.status = 200;
            try {
                return new String(Files.readAllBytes(Paths.get(directory))).getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.status = 401;
        return "Authentication required".getBytes();
    }

    public boolean isAuthenticated() {
        boolean isAuthenticated = false;

        if (containsAuthorization()) {
            String credentials = request.get("Authorization");
            credentials = credentials.split(" ")[1];
            String decoded = decodedCredentials(credentials);
            if (decoded.equals(AUTHORIZED_CREDENTIALS))
                isAuthenticated = true;
        }

        return isAuthenticated;
    }

    public boolean containsAuthorization() {
        return request.containsKey("Authorization");
    }

    public String decodedCredentials(String encodedCredentials) {
        Base64 decoder = new Base64();
        byte[] decodedCredentials = decoder.decode(encodedCredentials);
        return new String(decodedCredentials);
    }

    public String getContentType() {
        return "text/plain";
    }

    public int getStatus() {
        return this.status;
    }

    public void setRequest(HashMap<String, String> request) {
        this.request = request;
    }

    public String getLogDirectory()
    {
        return directory;
    }
}
