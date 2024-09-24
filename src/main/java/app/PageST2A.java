package app;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST2A implements Handler {

    public static final String URL = "/page2A.html";
    private static final String DATABASE = "jdbc:sqlite:Database/climate.db";

    private JDBCConnection jdbcConnection;

    public PageST2A() {
        this.jdbcConnection = new JDBCConnection();
    }

    @Override
    public void handle(Context context) throws Exception {
        
        String html = "<html>";

        html += "<head>" +
                "<title>State and City Data Explorer</title>" +
                "<link rel='stylesheet' type='text/css' href='st2a.css' />" +
                "</head>";

        html += "<body>";

        html += """
            <div class='topnav'>
            <a href='/'>Homepage</a>
            <a href='mission.html'>Climate Action Hub</a>
            <a href='page2A.html'>Global Data Explorer</a>
            <a href='page2B.html'>State and City Data Explorer</a>
            <a href='pageST3A.html'>Temperature Data</a>
            <a href='pageST3B.html'>Climate Data Similarity Explorer</a>
            </div>
        """;

        html += """
            <div class='header'>
                <h1>Global Data Explorer</h1>
            </div>
        """;

        html += "<div class='content'>";
        html += "<h2>Data Selection</h2>";

     html += """
    <div id='temperature-data'>
    <h3>Temperature Data</h3>
    <form action='/pageST2A.html' method='post'>
        <label for='temperature-start-year'>Select Start Year:</label>
        <input type='text' id='temperature-start-year' name='temperature-start-year' placeholder='YYYY' required pattern='[0-9]{4}'>
        <br><br>
        <label for='temperature-end-year'>Select End Year:</label>
        <input type='text' id='temperature-end-year' name='temperature-end-year' placeholder='YYYY' required pattern='[0-9]{4}'>
        <br><br>
        <label for='temperature-view'>Select View:</label>
        <input type='radio' id='temperature-view-country' name='temperature-view' value='country' checked>
        <label for='temperature-view-country'>Country</label>
        <input type='radio' id='temperature-view-world' name='temperature-view' value='world'>
        <label for='temperature-view-world'>World</label>
        <br><br>
        <label for='selected-country'>Select Country:</label>
        <select id='selected-country' name='selected-country'>
            <!-- Populate dropdown options with countries from the database -->
            {COUNTRY_OPTIONS}
        </select>
        <br><br>
        <button type='submit'>Get Data</button>
    </form>
    <br><br>
    <div id='temperature-data-container'>
        <!-- Display the retrieved temperature data here -->
        {TEMPERATURE_DATA}
    </div>
    <div id='temperature-error' style='color: red;'></div>
</div>
<br><br>
<div id='data-availability'>
    <p>Data Availability:</p>
    <ul>
        <li>World View: Data available from 1750 to 2015.</li>
        <li>Country View: Data available from 1750 to 2013.</li>
    </ul>
</div>
""";



  html += """
    <div id='population-data'>
        <h3>Population Data</h3>
        <form action='/pageST2A.html' method='post'>
            <label for='population-start-year'>Select Start Year:</label>
            <input type='text' id='population-start-year' name='population-start-year' placeholder='YYYY' required pattern='[0-9]{4}'>
            <br><br>
            <label for='population-end-year'>Select End Year:</label>
            <input type='text' id='population-end-year' name='population-end-year' placeholder='YYYY' required pattern='[0-9]{4}'>
            <br><br>
            <label for='selected-country-population'>Select Country:</label>
            <select id='selected-country-population' name='selected-country-population'>
                <!-- Populate dropdown options with countries from the database -->
                {COUNTRY_OPTIONS}
            </select>
            <br><br>
            <button type='submit'>Get Data</button>
        </form>
        <br><br>
        <div id='population-data-container'>
            <!-- Display the retrieved population data here -->
            {POPULATION_DATA}
        </div>
        <div id='population-error' style='color: red;'></div>
    </div>
    <br><br>
    <div id='data-availability-population'>
        <p>Data Availability:</p>
        <ul>
            <li>Population Data available from 1960 to 2013.</li>
        </ul>
    </div>
""";


if (context.method().equalsIgnoreCase("POST")) {
    String startYearStr = context.formParam("temperature-start-year");
    String endYearStr = context.formParam("temperature-end-year");
    String temperatureView = context.formParam("temperature-view");
    String selectedCountry = context.formParam("selected-country");

    if (startYearStr != null && !startYearStr.isEmpty() && endYearStr != null && !endYearStr.isEmpty()) {
        int startYear = Integer.parseInt(startYearStr);
        int endYear = Integer.parseInt(endYearStr);
        List<TemperatureData> temperatureDataList = jdbcConnection.getTemperatureData(startYear, endYear, temperatureView, selectedCountry);

        // Generate HTML for temperature data
        StringBuilder temperatureDataHtml = new StringBuilder();
        for (TemperatureData temperatureData : temperatureDataList) {
            temperatureDataHtml.append("<p>").append(temperatureData.toString()).append("</p>");
        }

        // Replace placeholder with actual temperature data HTML
        html = html.replace("{TEMPERATURE_DATA}", temperatureDataHtml.toString());
    } else {
        // Replace placeholder with empty string if no temperature data available
        html = html.replace("{TEMPERATURE_DATA}", "");
    }
}

  // Handle population data retrieval
if (context.method().equalsIgnoreCase("POST")) {
    String populationStartYear = context.formParam("population-start-year");
    String populationEndYear = context.formParam("population-end-year");
    String selectedCountry = context.formParam("selected-country-population");

    if (populationStartYear != null && !populationStartYear.isEmpty()
            && populationEndYear != null && !populationEndYear.isEmpty()) {
        int startYear = Integer.parseInt(populationStartYear);
        int endYear = Integer.parseInt(populationEndYear);
        List<PopulationData> populationDataList = jdbcConnection.getPopulationData(startYear, endYear, selectedCountry);

        // Generate HTML for population data
        StringBuilder populationDataHtml = new StringBuilder();
        for (PopulationData populationData : populationDataList) {
            populationDataHtml.append("<p>").append(populationData.toString()).append("</p>");
        }

        // Replace placeholder with actual population data HTML
        html = html.replace("{POPULATION_DATA}", populationDataHtml.toString());
    }
} else {
    // Replace placeholder with empty string if no population data available
    html = html.replace("{POPULATION_DATA}", "");
}


    // Retrieve countries from the database and populate the dropdown options
   List<String> countries = jdbcConnection.getCountries();
Collections.sort(countries); // Sort the countries alphabetically
StringBuilder countryOptions = new StringBuilder();
for (String country : countries) {
    countryOptions.append("<option value='").append(country).append("'>").append(country).append("</option>");
}
html = html.replace("{COUNTRY_OPTIONS}", countryOptions.toString());


    html += "</div>"; // Close content div
    html += "</body>";
    html += "</html>";

    context.html(html);
}

}