package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as a regular Java application will start the 
 * Javalin HTTP Server and our web application.
 * 
 * Authors:
 * Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int JAVALIN_PORT = 7001;
    public static final String CSS_DIR = "css/";
    public static final String IMAGES_DIR = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen on port 7001
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));

            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);

        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        JDBCConnection jdbcConnection = new JDBCConnection(); // Instantiate your JDBCConnection class here
    
        app.get("/", new PageIndex());
        app.get("/mission.html", new PageMission());
        app.get("/Learning.html", new LearningAndAwareness());
      app.get("/Target.html", new Target());
        app.get("/page2A.html", new PageST2A());
        app.get("/page2B.html", new PageST2B()); // Provide the JDBCConnection instance
        app.get("/page3A.html", new PageST3A());
        app.get("/page3B.html", new PageST3B());
        app.get("/pageST3A.html", new PageST3A());
        app.get("/pageST3B.html", new PageST3B());
    
        // Add other routes here if needed
    
        // Add / uncomment POST commands for any pages that need web form POSTS
         app.post("/", new PageIndex());
        app.post("/learning.html", new LearningAndAwareness());
        app.post("/pageST2A.html", new PageST2A());
        app.post("/page2B.html", new PageST2B());
        app.post("/page3A.html", new PageST3A());
        app.post("/page3B.html", new PageST3B());
        app.post("/pageST3A.html", new PageST3A());
        app.post("/pageST3B.html", new PageST3B());
    }
}
    