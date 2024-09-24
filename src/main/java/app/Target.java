package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;


/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 * 
 * Authors:
 * Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Target implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/Target.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our website target</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='Target.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        
        html = html + """
            <div class='topnav'>
            <a href='/'>Homepage</a>
            <a href='mission.html'>Our Mission</a>
            <a href='Learning.html'>LearningandAwareness</a>
       
            
            
            </div>
        """;
         // Add header content block
        html = html + """
            <div class='header'>
                <h1>Our website target</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

       
        html = html + """
           

   
    <h2>Persona 1 - Sarah Zayn</h2>
    <p> Sarah Zayn: "I need accessible educational materials and interactive content to enhance my understanding of climate change." </p>
     <img src='sarah.png' alt='Sarah Zayn image' style='float: right;'>


    <h3>Description:</h3>
    <p>
        Sarah Zayn is looking for interactive climate change education resources and materials that are accessible.
    </p>
    <h3>Attributes/Background:</h3>
    <ul>
        <li>35 years old, single</li>
        <li>Moderately educated</li>
        <li>Climate change novice</li>
        <li>Collaborative and team-oriented</li>
    </ul>
    <h3>Needs and Goals:</h3>
    <ul>
        <li>Sarah is interested in being more informed about the causes, effects, and potential solutions to climate change. She is looking for educational resources that are simple to obtain and interactive so that she can better understand them.</li>
        <li>Sarah is looking for specific recommendations on how she might live her life in a way that has a beneficial impact on the environment. She seeks advice on developing eco-friendly behaviors and lowering her carbon impact.</li>
        <li>Sarah wants to be informed about the most recent developments in climate change-related news, science, and technology. She looks for a platform that offers pertinent articles, publications, and announcements to stay informed.</li>
    </ul>
    <h3>Skills and Experiences:</h3>

    <ul>
        <li>Basic understanding of climate change and its broad effects</li>
        <li>Limited knowledge about how to incorporate sustainable practices into daily life</li>
        <li>Limited access to climate change education resources and materials</li>
    </ul>
    <h3>Pain Points:</h3>
    <ul>
        <li>Limited availability of educational resources: Sarah might have trouble locating learning tools that are both simple to obtain and appropriate for her level of comprehension. Her ability to learn could be hampered by the lack of available materials.</li>
        <li>Overwhelmed by complex information: Due to the quantity of technical and scientific terminology, Sarah may find it difficult to understand the complexity of climate change. She would better understand the concepts if there were clearer explanations and engaging content.</li>
        <li>Lack of ability to apply knowledge in practical ways: Sarah could find it challenging to comprehend how she can individually combat climate change. This pain point would be relieved by suggestions and advice that were realistic and applicable to her daily life.</li>
    </ul>
    


  <p> Image reference 
  https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.bigstockphoto.com%2Fimage-198167599%2Fstock-photo-girl-young-student-teenage-girl-college-student-high-school-student-isolated&psig=AOvVaw3rF4Tkor4MKVAReHSwpjM6&ust=1687138617448000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCICzuKDXy_8CFQAAAAAdAAAAABAE</p>;
 
    <h2>Persona 2 - Mike Reigns</h2>
    <p>
        Mike Reigns: "I want to compare temperature trends and identify regions that have experienced significant changes."
    </p>
     <img src='mike.png' alt='Mike Reigns image' style='float: right;'>

    <h3>Description:</h3>
    <p>
        Mike Reigns, an analytical data analyst, is looking for comparisons of temperature trends.
    </p>
    <h3>Attributes/Background:</h3>
    <ul>
        <li>28 years old, married</li>
        <li>Data analyst</li>
        <li>Strong interest in climate change</li>
        <li>Analytical and data-driven</li>
    </ul>
    <h3>Needs and Goals:</h3>
    <ul>
        <li>Mike is looking for an interactive application that will let him compare the temperature in various cities or states between two user-selected years. He is looking to examine temperature patterns and find areas that have changed significantly.</li>
        <li>To better understand the temperature variations, Mike likes visual representations of the data, such as charts, graphs, and maps. He appreciates being able to tailor the data display to suit his tastes.</li>
        <li>Mike requires proper temperature data in order to do his analysis. He counts on the tool to deliver accurate and current information that has been sourced from reliable sources.</li>
    </ul>
    <h3>Skills and Experiences:</h3>
    <ul>
        <li>Strong analytical capabilities and data analysis expertise.</li>
        <li>Working knowledge of data visualization methods and technologies.</li>
        <li>Familiarity with sources and datasets relating to climate.</li>
        <li>Basic knowledge of the terms used in climate science.</li>
    </ul>
    <h3>Pain Points:</h3>
    <ul>
        <li>Complexity of data analysis: Handling sizable datasets and comprehending statistical methods are required while analyzing temperature patterns. Mike might run into difficulties managing and analyzing the data to get reliable findings.</li>
        <li>Reliability of the data source: Finding and relying on trustworthy sources of temperature information can be difficult. To make sure the data he utilizes for his research is reliable and accurate, Mike may need to search through several sources.</li>
    </ul>
      <p> Image reference
      https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.alamy.com%2Fstock-photo%2Fbearded-man-50s.html&psig=AOvVaw2poZ0CEoi3yQrYEfpLNkgc&ust=1687139030740000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCNjbteXYy_8CFQAAAAAdAAAAABAQ</p>;
  
    <h2>Persona 3 - Emma Mac</h2>
    <p>
        Emma Mac: "I want to study historical eras with similar climatic conditions and population dynamics for research purposes."
    </p>
     <img src='emma.png' alt='emma image' style='float: right;'>

    <h3>Description:</h3>
    <p>
        Emma Mac, a historian specializing in climate history, is looking to study historical eras with comparable climatic conditions and population dynamics for her research.
    </p>
    <h3>Attributes/Background:</h3>
    <ul>
        <li>42 years old, married</li>
        <li>Historian specializing in climate history</li>
        <li>Worked with the government agency to create awareness</li>
    </ul>
    <h3>Needs and Goals:</h3>
    <ul>
        <li>Emma is looking for a program that would allow her to compare historical eras using population and temperature information. She wants to conduct research on historical periods with comparable climatic circumstances and population dynamics.</li>
        <li>Emma appreciates well-organized, systematic data presentations that let her contrast various historical eras. She looks for information that is organized and has clear visualizations to make her research and analysis easier.</li>
        <li>Emma bases her investigation on complete and reliable historical information. She anticipates that the tool will offer trustworthy and carefully chosen data sources that she may use as a resource for her research.</li>
    </ul>
    <h3>Skills and Experiences:</h3>
    <ul>
        <li>Emma has expertise in climate history and a thorough understanding of how climate changes affect different historical eras. Her knowledge enables her to examine how societal changes and population dynamics have been influenced by climate across time.</li>
        <li>Emma is quite adept at undertaking historical research, acquiring pertinent data, and analyzing information from a variety of sources. She excels at finding trends, making connections, and setting her studies in a historical framework.</li>
        <li>Flexibility and lifelong learning: Emma upholds a dedication to continuing education and keeps current with developments in the study of climate history. As new information and technologies become available, she modifies her research techniques and strategies.</li>
    </ul>
    <h3>Pain Points:</h3>
    <ul>
        <li>Limited access to comparable historical data: Emma may have trouble locating trustworthy and complete data sets for historical periods with comparable climate conditions and population dynamics. The lack of readily available data can impede her research's advancement.</li>
        <li>Analyzing and interpreting historical data can be a challenging undertaking that calls for careful evaluation of numerous elements and potential confounding variables. Emma might struggle to make sense of the complex data and develop conclusions that are useful.</li>
    </ul>
    <p> image reference 
    https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.discovermagazine.com%2Fthe-sciences%2Fmeet-10-women-in-science-who-changed-the-world&psig=AOvVaw0Nwes7mhz9EurDeL0-wCOz&ust=1687139214459000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCNCghL3Zy_8CFQAAAAAdAAAAABAF</P>;
""";

 html = html + """
            <div class='footer'>
                <p> Climate voice(Apr23)</p>
            </div>
        """;

        // Close Content div
        html = html + "</div>";

        // Close body and HTML tags
        html = html + "</body>" + "</html>";

        // Makes Javalin render the webpage
        context.html(html);
    }
}