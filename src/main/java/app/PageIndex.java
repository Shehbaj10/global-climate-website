package app;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageIndex implements Handler {

    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";

        html = html + "<head>" +
                "<title>Climate Change awareness</title>";

        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        html = html + "<body>";

        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Global Data Explorer</a>
                <a href='page2B.html'>State and City Data Explorer</a>
                <a href='pageST3A.html'>Temperature Data</a>
                <a href='pageST3B.html'>Climate Data Similarity Explorer</a>
            </div>
        """;
  // Add header content block
        html += "<div class='header'>";
        html += "<h1>ClimateVoice</h1>";
        html += "</div>";

        // Add the content
        html += "<div class='content'>";
        
        // Add the image container with overlay
        html += "<div class='container'>";
        html += "<img src='jkjk.png' alt='Avatar' class='image'>";
        html += "<div class='overlay'>Unleash Your Eco-Passion: Explore Climate's Gateway to Sustainability.</div>";
        html += "</div>";

        html += "</div>";
        html = html + """
            </div>
            <div class='footer'>
                <p>Climate voice (Apr23)</p>
            </div>
        """;
        
        html += "</body></html>";

        // Makes Javalin render the webpage
        context.html(html);
    }
}
       