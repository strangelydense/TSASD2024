import java.io.PrintStream;
import java.util.Arrays;

/** A WebGameService a web based game where balls move around on a court. */
public class WebGameServer extends WebServer {  
    /** main program for the server. Open a HTTP port and listen for new connections. */
    public static void main(String[] args) { 
        // Create a HTTP Web Server and start the service up.   
        // processGetRequest will be called from there when requests come in. 
        // pass 'true' if you want the Webserver to also launch a local web browser (makes it more like an app).  
        new WebGameServer().runServer(true);
    }
    
    public WebGameServer() {
        _numRequestsServiced = 0; 
        _shapes = new Shape[] {
                new Ball(200, 200, "red", 20), 
                new Ball(300, 200, "green", 20), 
                new Face(400, 200, "blue", 20),
                new Rectangle(100, 200, "yellow", 20, 40),
                new Rectangle(500, 200, "blue", 30, 20)};
        _stopped = true;
        _newBallSize = 20;
        _newBallColor = "red";
    }
    
    /** implements logic to generate a Web Page */  
    public boolean processGETRequest(String path, PrintStream response) {
        _numRequestsServiced++;
        this.updateStateForURLCommand(path); // This updates the state of the system
        this.renderAsHTML(path, response); // and output the updated state as HTML
        return false;   // returning true will cause the server to exit.  
    }
    
    /****************************************************************************/
    /* private methods */
    // updates the state based on the URL path given by 'path'.
    private void updateStateForURLCommand(String path) {
        if (path.startsWith("/courtMouseClick=")) {
            _shapes = Arrays.copyOf(_shapes, _shapes.length + 1);
            _shapes[_shapes.length - 1] = new Ball(getArgAsInt(path, 0), getArgAsInt(path, 1), _newBallColor, sizes[(int) (Math.random() * 9)]);
        } else if (path.startsWith("/ballSizeTextBox=")) {
            int value = getArgAsInt(path, 0);
            if (5 < value && value < 150) // Simple error checking.
                _newBallSize = value;
        } else if (path.startsWith("/ballColorTextBox=")) {
            String newColor = getArg(path, 0);
            if (newColor.equals("red") || newColor.equals("green") || newColor.equals("blue") || newColor.equals("black"))
                _newBallColor = newColor;
        } else if (path.equals("/startButtonClick")) {
            _stopped = false;
        } else if (path.equals("/stopButtonClick")) {
            _stopped = true;
        } else if (path.equals("/advanceButtonClick")) {
            // update the shapes.
            for (Shape shape : _shapes) {
                shape.update(600, 400);
            }
        }
    }

    /* Given a string of the form ...=ARG0,ARG1,ARG2 ... called 'args' and
    an argument number 0 is first arg, return the string for that argument
    for example getArg("/myCommand=356,34", 0) == "356"
    throws exceptions if the string is not of the required syntax. */
    private static String getArg(String args, int argNum) {
        // compute the start index (count ,)
        int argStartIdx = args.indexOf('=') + 1;
        for (int i = 0; i < argNum; i++)
            argStartIdx = args.indexOf(',', argStartIdx) + 1;
        // Compute the stop index.
        int argStopIdx = args.indexOf(',', argStartIdx);
        if (argStopIdx < 0)
            argStopIdx = args.length();
        return args.substring(argStartIdx, argStopIdx);
    }

    // Like getArg, but converts the result to an integer.
    private static int getArgAsInt(String args, int argNum) {
        return Integer.parseInt(getArg(args, argNum));
    }

    /* this read-only method, given the URL path 'path' renders the HTML for
    service as a whole to the PrintStream 'response'. */
    private void renderAsHTML(String path, PrintStream response) {
        response.println("<html>");
        response.println("<body>");
        response.println("A Simulation Game<br>"); // <br> makes a line break
        response.println("Click around to see what happens<br>");

        // Create a canvas on which we can draw shapes.
        response.printf("<svg style='background-color:gray;' width='600' height='400' "
                + "onClick=\"location.href='courtMouseClick='+event.offsetX+','+event.offsetY\">\n");
        // draw the shapes.
        for (Shape shape : _shapes) {
            shape.renderAsHTML(response);
        }

        response.println("</svg>");
        response.println("<br>");

        // draw the GUI controls
        renderButtonAsHtml("Start", "startButtonClick", _stopped, response);
        renderButtonAsHtml("Stop", "stopButtonClick", !_stopped, response);
        renderButtonAsHtml("Advance", "advanceButtonClick", true, response);
        response.println("<br>");
        renderTextBoxAsHtml("Ball Size", "ballSizeTextBox", Integer.toString(_newBallSize), response);
        response.println("<br>");
        renderTextBoxAsHtml("Ball Color", "ballColorTextBox", _newBallColor, response);
        response.println("<br>");

        // Write a status lines.
        response.println("The path for the web page is " + path + "<br>");
        response.println("Server has been called " + _numRequestsServiced + " times.<br>");
        response.println("<br>");

        // Tells browser to load a page called 'advanceButtonClick' in 500 msec = 1/2 of a second.
        if (!_stopped)
            response.println("  <script> setTimeout(() => location.href = 'advanceButtonClick', 500); </script>");
        response.println("</body>");
        response.println("</html>");
    }

    /* renders the HTML for an input text box to the PrintStream 'response'
    default value is the value in the textBox when it is rendered.
    The URL generated is 'url'=value where value is the value in the textBox; */
    private static void renderTextBoxAsHtml(String label, String url, String defaultValue, PrintStream response) {
        response.printf("%s: <input type='text' value='%s' onchange=\"location.href='%s='+event.target.value\"></input>\n", label,
                defaultValue, url);
    }

    /* renders the HTML for a button to the PrintStream 'response'
    label is text on the button.
    url is the URL that is when the button is clicked.
    if isActive is false then the button is rendered as dark gray to indicate
    it is inactive. */
    private static void renderButtonAsHtml(String label, String url, boolean isActive, PrintStream response) {
        String style = "";
        if (!isActive)
            style = "style='background-color:gray'";
        response.printf("<button %s onClick=\"location.href='%s'\">%s</button>", style, url, label);
    }
    
    /****************************************************************************/
    /* private fields */
    private int _numRequestsServiced;
    private Shape[] _shapes; // The main model state. The position and state of the shapes.
    // GUI State
    private boolean _stopped; // Should send 'advanceButtonClick' every 500ms or not?
    private int _newBallSize; // Values that you use when creating a new ball with mouse
    private String _newBallColor;
    private int[] sizes = {6, 10, 10, 15, 15, 20, 20, 20, 30, 35};
}
