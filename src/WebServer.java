// Author: Vance Morrison
// CreatedDate:  12/13/2019
import java.io.*;
import java.net.*;
import java.util.*;
/**
 * A WebServer is the basis for a HTTP Web Server. It is meant to be created by passing 
 * it a WebService which knows when to do when an HTTP get request is received. 
 * The WebServer listens to the 8080 port for HTTP requests from browsers, parses the 
 * request then passes it on to the service. When the service is finished generating HTML, 
 * it the WebServer then sends that data back to the requesting web browser.
 */
public abstract class WebServer {
    /** Make a web server and sends requests to the processGETRequest method */
    public WebServer() {}
    
    /** To be overridden by subclasses */
    public abstract boolean processGETRequest(String path, PrintStream response);

    public URI getLaunchBrowserURI(int port) {
        return URI.create(String.format("http://localhost:%d", port));
    }

    /** Starts up the server.   This will run until processGETRequest returns true.  
     * Given an instance of a WebServer (which knows how to handle processGETRequest() 
     * run the HTTP server (that is listen on the network and forward requests to the
     *  WebServer that as passed to us. */
    public void runServer(boolean launchBrowser) {
        try {
            // port to listen connection
            final int PORT = 8080; // 80 is the default port, use 8080 if you have permissions issues
            // we listen until user halts server execution
            System.out.println("To use this server\ntype this URL into a browser:  http://localhost:" + PORT);
            ServerSocket serverConnect = new ServerSocket(PORT);          
            if (launchBrowser)
                java.awt.Desktop.getDesktop().browse(getLaunchBrowserURI(PORT));
            while (true) {
                Socket clientConnection = serverConnect.accept();
                // This code tells Java to run processWebRequests on a new thread of execution.
                // Thus while this thread is processing the requests (which can take a while)
                // this thread loop and accepts the next connection.
                new Thread(() -> {
                    try {
                        processWebRequest(clientConnection); // This is runs concurrently on a new thread.
                    } catch (IOException e) {
                        e.printStackTrace();
                        Runtime.getRuntime().exit(-2);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
    }
    
    /******************************************************************************/
    /* private methods */
    // Called when you have established a the 'clientConnection' to some web
    // browser wanting service. Its job is to read the HTTP request, and
    // write back a response. It keeps doing this as long as the broswer
    // keeps the connection open.
    private void processWebRequest(Socket clientConnection) throws IOException {
        boolean stopping = false;
        System.out.println("Got an HTTP connection");
        InputStream inStream = clientConnection.getInputStream(); // Get the input data
        Scanner inScanner = new Scanner(inStream); // scan it as lines of chars
        OutputStream outStream = clientConnection.getOutputStream(); // Get place to send output
        PrintStream outWriter = new PrintStream(outStream); // generate it as lines of chars
        while (inScanner.hasNextLine()) {

            // get first line of the request from the client
            String line = inScanner.nextLine();
            // System.out.println("Got line: " + line);
            int firstSpace = line.indexOf(" ");
            String method = line.substring(0, firstSpace).toUpperCase();
            int secondSpace = line.indexOf(" ", firstSpace + 1);
            if (secondSpace < 0)
                secondSpace = line.length();
            String methodArg = line.substring(firstSpace + 1, secondSpace);
            while (inScanner.hasNextLine()) { // Keep reading until you see a blank line (zero length)
                line = inScanner.nextLine();
                if (line.length() == 0)
                    break;
            }
            // we support only GET commands
            if (!method.equals("GET")) {
                // we only support GET
                writeHTTPResponse("HTTP/1.1 501 Not Implemented", null, outWriter);
            } else if (methodArg.equals("/favicon.ico")) {
                // Web browsers look for this special icon, simply say we can't find it.
                writeHTTPResponse("HTTP/1.1 404 Not Found", null, outWriter);
            } else {
                // HTTP requires that we know the exact size of the response, so create a
                // ByteArrayOutputStream that gathers up the response into a byte array so
                // we know how many bytes it will take.
                ByteArrayOutputStream bufferStream = new ByteArrayOutputStream(); // put bytes here
                PrintStream bufferWriter = new PrintStream(bufferStream); // generate as lines of chars
                // Process the request doing println's to generate a response.
                // we undo the URL escaping of spaces before we send the path to this method.
                stopping = this.processGETRequest(methodArg.replace("%20", " "), bufferWriter);
                // The resulting output ends up in this byte[] called responseData.
                bufferWriter.close();
                byte[] responseData = bufferStream.toByteArray();
                // Now send out the complete HTTP response, including HTTP headers
                writeHTTPResponse("HTTP/1.1 200 OK", responseData, outWriter);
            }
            outWriter.flush();
            outStream.flush();
            if (stopping)
                break;
        }
        System.out.println("Closing HTTP connection: " + clientConnection.getRemoteSocketAddress());
        // Close down the streams.
        inScanner.close();
        inStream.close();
        outWriter.close();
        outStream.close();
        clientConnection.close();
        if (stopping) {
            System.out.println("Exiting Server");
            // Normally you would unwind out of each method, and have main() return, however
            // main is waiting on the next potential request and may never wake up, so we
            // call a special method that forcibly exits the program.
            Runtime.getRuntime().exit(0);
        }
    }
    
    // Writes out a properly formatted HTTP response. responseData can be null, which means 0 length data.
    private static void writeHTTPResponse(String responseCode, byte[] responseData, PrintStream outWriter) throws IOException {
        outWriter.println(responseCode);
        outWriter.println("Server: Java HTTP Server Version 1.0");
        outWriter.println("Date: " + new Date());
        outWriter.println("Content-type: text/html");
        int len = 0;
        if (responseData != null)
            len = responseData.length;
        outWriter.println("Content-length: " + len);
        // blank line between headers and content, very important !
        outWriter.println();
        if (responseData != null)
            outWriter.write(responseData);
    }
}