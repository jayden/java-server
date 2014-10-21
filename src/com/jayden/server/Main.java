package com.jayden.server;

import java.io.File;
import java.util.*;

public class Main
{
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";
    private static int port = 5000;
    private static String directory = System.getProperty("user.dir") + "/public";
    private static Server server;
    private static ArrayList<String> fileList;

    public static void main(String args[])
    {
        parseArguments(args);
        fileList = getDirectoryContents();
        server = new Server(port);
        addRoutes();
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
        try {
            File[] files = new File(directory).listFiles();
            for (File file : files) {
                fileList.add("/" + file.getName());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return fileList;
    }

    private static void addRoutes()
    {
        for(String file : fileList)
            server.addRoute(file, new FileResponse(directory));

        server.addRoute("/", new FileDirectoryResponse());
        server.addRoute("/echo", new TimeResponse());
        server.addRoute("/logs", new AuthResponse(directory));
        server.addRoute("/parameters", new ParameterResponse());
        server.addRoute("/form", new FormResponse(directory));
        server.addRoute("/redirect", new RedirectResponse());
        server.addRoute("/method_options", new OptionsResponse());
    }
}
