package app;
import java.util.List;

import org.eclipse.jetty.websocket.api.SuspendToken;

import java.util.ArrayList;
import java.util.Collections;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST2B implements Handler {

    public static final String URL = "/page2B.html";
    private JDBCConnection jdbcConnection;
    private static final String DATABASE = "jdbc:sqlite:Database/climate.db";

    public PageST2B() {
        this.jdbcConnection = new JDBCConnection();
    }

    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";

        html += "<head>" +
                "<title>State and City Data Explorer</title>" +
                "<link rel='stylesheet' type='text/css' href='ST2B.css' />" +
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
                <h1>state and city data explorer.</h1>
            </div>
        """;

        html += "<div class='content'>";

        html += """
    <div id='State-data'>
        <h3>State Data</h3>
        <form action='/page2B.html' method='post'>
            <label for='start_year'>Select Start Year:</label>
            <input type='text' id='start_year' name='start_year' placeholder='YYYY' required pattern='[0-9]{4}'>
            <br><br>
            <label for='end_year'>Select End Year:</label>
            <input type='text' id='end_year' name='end_year' placeholder='YYYY' required pattern='[0-9]{4}'>
            <br><br>
            <label for='selected_state'>Select State:</label>
            <select id='selected_state' name='selected_state'>
                <!-- Populate dropdown options with states from the database -->
                {STATE_OPTIONS}
            </select>
            <br><br>
            <button type='submit'>Get State Data</button>
        </form>
        <br><br>
        <div id='State-data-container'>
            <!-- Display the retrieved temperature data here -->
        </div>
        <div id='state-error' style='color: red;'>
            <!-- Display error message here -->
        </div>
    </div>
    <li>State Data available from 1750 to 2013.</li>
    <br><br>
""";

// Retrieve the list of states from the database
List<String> stateOptions = jdbcConnection.getStates();
Collections.sort(stateOptions); // Sort states in alphabetical order

// Generate the HTML options for the state dropdown menu
StringBuilder stateOptionsHtml = new StringBuilder();
for (String stateOption : stateOptions) {
    stateOptionsHtml.append("<option value='").append(stateOption).append("'>").append(stateOption).append("</option>");
}

// Update the HTML code with the state options
html = html.replace("{STATE_OPTIONS}", stateOptionsHtml.toString());

// Handle state form submission
if (context.method().equalsIgnoreCase("POST")) {
    String startYearStr = context.formParam("start_year");
    String endYearStr = context.formParam("end_year");
    String selectedState = context.formParam("selected_state");

    if (startYearStr != null && !startYearStr.isEmpty() && endYearStr != null && !endYearStr.isEmpty()) {
        if (startYearStr.matches("[0-9]{4}") && endYearStr.matches("[0-9]{4}")) {
            int startYear = Integer.parseInt(startYearStr);
            int endYear = Integer.parseInt(endYearStr);

            // Validate start year and end year
            if (endYear >= startYear) {
                // Retrieve the temperature data based on the selected parameters
                List<StateCountry> stateCountryDataList = jdbcConnection.getStateCountryDataByCountryAndState(startYear, endYear, selectedState);

                html += "<h3>Temperature Data - State</h3>";
                html += "<table>";
                html += "<tr><th>Start Year</th><th>Average Temperature</th><th>Minimum Temperature</th><th>Maximum Temperature</th><th>State</th><th>Country</th></tr>";
                for (StateCountry stateCountry : stateCountryDataList) {
                    html += "<tr>";
                    html += "<td>" + stateCountry.getStartYear() + "</td>";
                    html += "<td>" + stateCountry.getAverageTemperature() + "</td>";
                    html += "<td>" + stateCountry.getMinimumTemperature() + "</td>";
                    html += "<td>" + stateCountry.getMaximumTemperature() + "</td>";
                    html += "<td>" + stateCountry.getState() + "</td>";
                    html += "<td>" + stateCountry.getCountry() + "</td>";
                    html += "</tr>";
                }
                html += "</table>";
            } else {
                html = html.replace("{STATE_ERROR}", "End year must be greater than or equal to start year.");
            }
        } else {
            html = html.replace("{STATE_ERROR}", "Please enter valid year values in the format YYYY.");
        }
    } else {
        html = html.replace("{STATE_ERROR}", "Please enter start year and end year.");
    }
} else {
    // Clear error message if form is not submitted
    html = html.replace("{STATE_ERROR}", "");
}




html += """
    <div id='City-data'>
        <h3>City Data</h3>
        <form action='/page2B.html' method='post'>
            <label for='startYearCity'>Select start year:</label>
            <input type='text' id='startYearCity' name='startYearCity' placeholder='YYYY' required pattern='[0-9]{4}'>
            <br><br>
            <label for='endYearCity'>Select end year:</label>
            <input type='text' id='endYearCity' name='endYearCity' placeholder='YYYY' required pattern='[0-9]{4}'>
            <br><br>
            <label for='selected_city'>Select a city:</label>
            <select id='selected_city' name='selected_city'>
                <!-- Populate dropdown options with cities from the database -->
                {CITY_OPTIONS}
            </select>
            <br><br>
            <button type='submit'>Get City Data</button>
        </form>
        <br><br>
        <div id='City-data-container'>
            <!-- Display the retrieved temperature data here -->
        </div>
        <div id='city-error' style='color: red;'>
            <!-- Display error message here -->
        </div>
    </div>
     <li>City Data available from 1750 to 2013.</li>
    <br><br>
""";

// Retrieve the list of cities from the database
List<String> cities = jdbcConnection.getCities();
Collections.sort(cities); // Sort cities in alphabetical order

// Generate the HTML options for the city dropdown menu
StringBuilder cityOptionsHtml = new StringBuilder();
for (String city : cities) {
    cityOptionsHtml.append("<option value='").append(city).append("'>").append(city).append("</option>");
}

// Update the HTML code with the city options
html = html.replace("{CITY_OPTIONS}", cityOptionsHtml.toString());

// Handle city form submission
if (context.method().equalsIgnoreCase("POST")) {
    String startYearCityStr = context.formParam("startYearCity");
    String endYearCityStr = context.formParam("endYearCity");
    String selectedCity = context.formParam("selected_city");

    if (startYearCityStr != null && !startYearCityStr.isEmpty() && endYearCityStr != null && !endYearCityStr.isEmpty()) {
        if (startYearCityStr.matches("[0-9]{4}") && endYearCityStr.matches("[0-9]{4}")) {
            int startYearCity = Integer.parseInt(startYearCityStr);
            int endYearCity = Integer.parseInt(endYearCityStr);

            // Retrieve the temperature data based on the selected parameters
            List<CityData> cityDataList = jdbcConnection.getCityDataByCity(selectedCity, startYearCity, endYearCity);

            html += "<h3>Temperature Data - City</h3>";
            html += "<table>";
            html += "<tr><th>Start Year</th><th>End Year</th><th>Average Temperature</th><th>Minimum Temperature</th><th>Maximum Temperature</th><th>City</th><th>Country</th></tr>";
            for (CityData cityData : cityDataList) {
                html += "<tr>";
                html += "<td>" + cityData.getStartYear() + "</td>";
                html += "<td>" + cityData.getEndYear() + "</td>";
                html += "<td>" + cityData.getAverageTemperature() + "</td>";
                html += "<td>" + cityData.getMinimumTemperature() + "</td>";
                html += "<td>" + cityData.getMaximumTemperature() + "</td>";
                html += "<td>" + cityData.getCity() + "</td>";
                html += "<td>" + cityData.getCountry() + "</td>";
                html += "</tr>";
            }
            html += "</table>";
        } else {
            html = html.replace("<div id='city-error' style='color: red;'>", "<div id='city-error' style='color: red;'>Invalid year format. Please enter a valid year in the format YYYY.");
        }
    } else {
        html = html.replace("<div id='city-error' style='color: red;'>", "<div id='city-error' style='color: red;'>Please enter start year and end year.");
    }
} else {
    // Clear error message if form is not submitted
    html = html.replace("<div id='city-error' style='color: red;'>", "<div id='city-error' style='color: red; display: none;'>");
}





html += "</form>";



        
        html += "</div>"; // Close content div

        html += "<div class='footer'>";
        html += "<p>Climate Voice</p>";
        html += "</div>";

        html += "</body>";
        html += "</html>";

        context.html(html);
    }
}