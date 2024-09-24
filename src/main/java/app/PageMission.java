package app;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html += "<head><title>Our Mission</title>";
        html += "<link rel='stylesheet' type='text/css' href='mission.css' /></head>";

        // Add the body
        html += "<body>";

        // Add the topnav
        html += "<div class='topnav'>";
        html += "<a href='/'>Homepage</a>";
        html += "<a href='mission.html'>Our Mission</a>";
        html += "<a href='Learning.html'>LearningandAwareness</a>";
         html += "<a href='Target.html'> Website Target</a>";
       
        html += "</div>";

       html += "<div class='header'>";
        html += "<h1>Our Mission</h1>";
        html += "</div>";

        html = html + "<div class='content'>";

        html = html + """
            <p>We are the first generation to feel the impact of climate change and the last generation that can do something about it. Climate change poses a 
            significant threat to our planet's ecosystems, biodiversity, and human well-being. It is crucial that we take immediate action to address this global issue.</p>
            <img src='/bbb.png' alt='index Image' class='centered-image' width='70%'>
          
            <h2>Mission Statement</h2>
            <p>Our mission is to raise awareness about climate change and its impact on the planet. We strive to empower individuals, communities, and organizations to take action towards mitigating climate change and creating a sustainable future for all.</p>
            
             <h3>How to use our website</h3>
             <p>With the help of our website user will be able to understand climate change. Additionally they will be able to access the data of change in temperature globally,city,country and state.The can also find population trend for last 60 years.</p>
            
            
            
            <h4>Who We Are</h4>

            <p>We are a passionate team dedicated to promoting climate change awareness and advocating for sustainable solutions. Together, we believe that by working collectively, we can make a positive impact on the environment and secure a better future for generations to come.</p>
            <p>Meet our team:</p>
            <ul>
        """;

        // Retrieve team member names from the student database
        JDBCConnection jdbcConnection = new JDBCConnection();
        List<Student> students = jdbcConnection.getStudents();
        System.out.println("Retrieved " + students.size() + " students from the database.");

        for (Student student : students) {
            html += "<li>" + student.getName() + "</li>";
        }

        html += "</ul>";

        html = html + """
            </div>
            <div class='footer'>
                <p>Climate voice (Apr23)</p>
            </div>
        """;

        html = html + "</body>" + "</html>";

        context.html(html);
    }
}
