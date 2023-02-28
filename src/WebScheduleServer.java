import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;

public class WebScheduleServer extends WebServer {
        /** main program for the server. Open a HTTP port and listen for new connections. */
        public static void main(String[] args) {
            // Create a HTTP Web Server and start the service up.
            // processGetRequest will be called from there when requests come in.
            // pass 'true' if you want the Webserver to also launch a local web browser (makes it more like an app).
            new WebScheduleServer().runServer(true);
        }

        public WebScheduleServer() {
            // TODO
        }

        public URI getLaunchBrowserURI(int port) {
            String launchURI = String.format("http://localhost:%d/%s?%s=%d", port, pathGenerateSchedule, paramKeyRounds, paramValueRounds);
            return URI.create(launchURI);
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

        }

        /* this read-only method, given the URL path 'path' renders the HTML for
        service as a whole to the PrintStream 'response'. */
        private void renderAsHTML(String path, PrintStream response) {
            response.println("<html>");
            response.println("<body>");
            response.println("Class Scheduler<br>"); // <br> makes a line break
            response.println("hi");

            response.println("</svg>");
            response.println("<br>");

            // Write a status lines.
            response.println("The path for the web page is " + path + "<br>");
            response.println("Server has been called " + _numRequestsServiced + " times.<br>");
            response.println("<br>");

            response.println("</body>");
            response.println("</html>");
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
        private final String pathGenerateSchedule = "generate-schedule";
        private final String paramKeyRounds = "rounds";
        private final int paramValueRounds = 20;
}
