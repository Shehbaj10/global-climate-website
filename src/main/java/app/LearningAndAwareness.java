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
public class LearningAndAwareness implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/Learning.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Learning and awareness</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='learning and awareness.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        
        html = html + """
            <div class='topnav'>
            <a href='/'>Homepage</a>
            <a href='mission.html'>Our Mission</a>
            <a href='Target.html'> Website Target</a>
            </div>
        """;
        
                
            


                

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Learning hub</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Content for learning and awareness
        html = html + """
            <h2>Understanding Climate Change</h2>
            <p>Climate change is a complex issue with far-reaching implications. 
            Mainly Climate change refers to long-term shifts in weather patterns and temperatures on Earth due to human activities, primarily the emission of greenhouse gases. The burning of fossil fuels, deforestation, and industrial processes have significantly increased the concentration 
            of greenhouse gases in the atmosphere, trapping heat and causing the planet to warm.This warming leads to a wide range of impacts, including rising sea levels, melting ice caps, more frequent and intense heatwaves, changes in precipitation patterns, and shifts in ecosystems.</p>
          
            <h3>The Science of Climate Change</h3>
            <p>Understanding the causes of climate change involves grasping the concept of the greenhouse effect. 
            Certain gases, such as carbon dioxide (CO2), methane (CH4), and nitrous oxide (N2O), act as a "blanket" in the atmosphere, allowing sunlight to pass through but trapping heat radiating from the Earth's surface. 
            Human activities, particularly the burning of fossil fuels like coal, oil, and natural gas for energy production, release vast amounts of CO2 into the atmosphere, contributing to the greenhouse effect. Deforestation also plays a significant role, as trees absorb
             CO2 and their removal reduces the planet's capacity to regulate the climate</p>

            <h3>Impacts of Climate Change</h3>
            <p>The consequences of climate change are far-reaching. 
            Rising global temperatures lead to the loss of glaciers and ice caps, resulting in rising sea levels that threaten coastal communities and ecosystems. 
            Extreme weather events, such as hurricanes, droughts, and floods, are becoming more frequent and intense, endangering lives, damaging infrastructure, and disrupting agriculture. 
            Changes in rainfall patterns can lead to water scarcity, affecting both human populations and agricultural productivity. Biodiversity loss is another consequence, as ecosystems struggle to adapt to the rapidly changing conditions.</p>

            <h3>Solutions and Mitigation Strategies</h3>
            <p>Addressing climate change requires a multi-faceted approach. 
            Mitigation efforts aim to reduce greenhouse gas emissions through transitioning to renewable energy sources, improving energy efficiency, promoting sustainable transportation, and implementing policies to limit emissions. 
            Adaptation measures focus on building resilience to the impacts of climate change, such as developing drought-resistant crops, implementing flood protection measures, and creating robust disaster response systems. 
            International cooperation and policy frameworks, such as the Paris Agreement, play a crucial role in coordinating global efforts to combat climate change.</p>

            <h3>Climate Change Adaptation</h3>
            <p>Climate change adaptation refers to the actions taken to minimize the negative impacts of climate change and enhance resilience to its effects. 
            As the Earth's climate continues to change, societies and ecosystems must adapt to the challenges it presents. 
            This involves implementing measures such as developing climate-resilient infrastructure, enhancing water management systems, promoting sustainable agriculture practices, and implementing early warning systems for extreme weather events. 
            Additionally, effective adaptation strategies require collaboration between governments, communities, businesses, and individuals to ensure that vulnerable populations are protected and that long-term sustainability is prioritized. 
            By focusing on adaptation, we can minimize the adverse effects of climate change and build a more resilient future for all.</p>

            <h3>Taking Climate Action</h3>
            <p>Taking climate action is essential to address the urgent and far-reaching challenges posed by climate change. 
            It involves individual and collective efforts to reduce greenhouse gas emissions, promote sustainable practices, and advocate for 
            policies that prioritize environmental stewardship. One crucial aspect of climate action is transitioning to renewable energy sources, 
            such as solar and wind power, to decrease reliance on fossil fuels. Additionally, adopting energy-efficient technologies, promoting sustainable 
            transportation options, and implementing waste reduction and recycling initiatives contribute to mitigating climate change. Climate action also involves 
            supporting policies and initiatives that promote sustainability, such as carbon pricing, reforestation projects, and the protection of natural habitats. 
            By engaging in climate action, we can contribute to the global movement towards a more sustainable and resilient future for generations to come.</p>
        """;
        html = html + """
            <div class='footer'>
                <p>Climate voice (Apr23)</p>
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
