package com.jayden.server;

import java.io.File;
import java.util.*;

public class Main
{
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";
    private static int port = 5000;
    private static String directory = "/public";

    public static void main(String args[])
    {
        parseArguments(args);
        ArrayList<String> fileList = getDirectoryContents();
        Server server = new Server(port);

        for(String file : fileList)
            server.addRoute(file, new FileResponse(directory));

        server.addRoute("/", new FileDirectoryResponse());
        server.addRoute("/echo", new TimeResponse());
        server.addRoute("/logs", new AuthResponse(directory));
        server.addRoute("/parameters", new ParameterResponse());

        server.start();
    }

    private static void parseArguments(String[] args)
    {
        for(int i=0; i < args.length; i++ )
        {
            if (args[i].equals(PORT_FLAG))
            {
                port = Integer.parseInt(args[i+1]);
            }
            else if (args[i].equals(DIRECTORY_FLAG))
            {
                directory = args[i+1];
            }
        }
    }

    private static ArrayList<String> getDirectoryContents()
    {
        ArrayList<String> fileList = new ArrayList<String>();
        File[] files = new File(System.getProperty("user.dir") + directory).listFiles();
        for (File file : files)
        {
            fileList.add("/" + file.getName());
        }
        return fileList;
    }
}
