package app;

import java.util.ArrayList;
import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    private JDBCConnection jdbcConnection;

    public PageST3B() {
        this.jdbcConnection = new JDBCConnection();
    }

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Climate Data Similarity Explorer</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='ST3B.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Global Data Explorer</a>
                <a href='page2B.html'>Temperature Variation Explorer</a>
                <a href='pageST3A.html'>Temperature Data</a>
                <a href='pageST3B.html'>Climate Data Similarity Explorer</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
            <h1>Climate Data Similarity Explorer</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <!DOCTYPE html>
<html>
<head>
    <title>Temperature Data</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
        }

        #configOptions {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding: 10px;
            background-color: #333333;
        }

        #configOptions label {
            margin-right: 10px;
        }

        #configOptions > div {
            margin-bottom: 10px;
        }

        #results {
            margin-bottom: 20px;
        }

        #results h2 {
            margin-top: 0;
        }

        #results table {
            width: 100%;
            border-collapse: collapse;
        }

        #results th,
        #results td {
            padding: 8px;
            border: 1px solid #ddd;
            text-align: left;
        }

        #results th {
            background-color: #333333;
        }

        #results td {
            vertical-align: top;
        }

        #selectionForm {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
        }

        #selectionForm label,
        #selectionForm select,
        #selectionForm input[type="text"],
        #selectionForm input[type="checkbox"],
        #selectionForm button {
            margin: 5px;
        }

        #selectionForm button {
            padding: 6px 12px; /* Adjust the padding for smaller button size */
        }

        /* Remove background color */
        #selectionForm select[name="startingYear"],
        #selectionForm select[name="selected-country"],
        #selectionForm select[name="selected-state"],
        #selectionForm select[name="selected-city"],
        #selectionForm label[for="Global"] {
            background-color: transparent;
        }

        /* Adjust label width for consistency */
        #selectionForm label[for="startingYear"],
        #selectionForm label[for="timePeriod"],
        #selectionForm label[for="region"],
        #similarityOptions label[for="similarity"],
        #similarityType label[for="similarityType"],
        #results label[for="numberOfRegions"] {
            width: 250px;
        }

        /* Adjust input width for consistency */
        #selectionForm input[type="number"],
        #selectionForm input[type="text"] {
            width: 100px;
        }

        /* Adjust select width for consistency */
        #selectionForm select {
            width: 150px;
        }

        /* Adjust table width for consistency */
        #resultTable {
            width: 100%;
        }
    </style>
</head>
<body>
    <div id="configOptions">
        <div>
            <label for="startingYear">Starting Year:</label>
            <select id="startingYear" name="startingYear" min="1800" max="2100">
            {GLOBAL_YEAR_OPTIONS}
            </select>
        </div>

        <div class="form-row">
            <label for="yearRange">Year Range:</label>
            <input type='text' id='yearRange' name='yearRange' placeholder='5' required pattern='[0-9]{1,2}'>
            </div>

            <div class="form-row">
            <label for="similarLimit">Similar Limit:</label>
            <input type='text' id='similarLimit' name='similarLimit' placeholder='5' required pattern='[0-9]{1,2}'>
            </div>

        <div class="form-row">
            <label for='selected-country'>Select Country:</label>
            <select id='selected-country' name='selected-country' size="1">
                <!-- Populate dropdown options with countries from the database -->
                {COUNTRY_OPTIONS}
            </select>
            </div>
            <div class="form-row">
            <label for='selected-state'>Select State:</label>
            <select id='selected-state' name='selected-state' size="1">
                <!-- Populate dropdown options with countries from the database -->
                {STATE_OPTIONS}
            </select>
            </div>
            <div class="form-row">
            <label for='selected-city'>Select City:</label>
            <select id='selected-city' name='selected-city' size="1">
                <!-- Populate dropdown options with countries from the database -->
                {CITY_OPTIONS}
            </select>
            </div>

        <div>
            <label for="numberOfRegions">Number of similar regions to find:</label>
            <input type="number" id="numberOfRegions" name="numberOfRegions" min="1">
        </div>

        <div>

        <form id="selectionForm" action="/pageST3B.html" method="POST">
            <button type="submit">Get My Data</button> <!-- Adjusted button size -->
        </form>
    </div>

    <div id="results">
        <h2>Results</h2>

        <table id="resultTable">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Start Year</th>
                    <th>End Year</th>
                    <th>Value(s)</th>
                    <th>Similarity Score</th>
                </tr>
            </thead>
            <tbody>
                <!-- Results to be populated here -->
            </tbody>
        </table>
    </div>
</body>
</html>

            """;

        // Close Content div
        html = html + "</div>";

        // Footer

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";

        if (context.method().equalsIgnoreCase("POST")) {
            String range_text  = context.formParam("yearRange");
            if (range_text == null) {
                System.out.println(" range_text is null");
            } else if (range_text.isEmpty()) {
                System.out.print("range_text is empty");
            } else {
                System.out.println(range_text);
            }
            String selectedStartYear = context.formParam("startYear");
            System.out.println(selectedStartYear);
          
            String selectedCountry = context.formParam("selected-country");
            System.out.println(selectedCountry);
            
            String selectedCity = context.formParam("selected-city");
            System.out.println(selectedCity);
                
                 
            String selectedState = context.formParam("selected-state");
            System.out.println(selectedState);
                
            String similarLimitString = context.formParam("similarLimit");
            System.out.println(selectedState);

            if (range_text != null) {

                int range_int = Integer.parseInt(range_text); 
                int startYear_int = Integer.parseInt(selectedStartYear);
                int stopYear_int = startYear_int + range_int;
                int similarLimit = Integer.parseInt(similarLimitString);


                List<List<String>> temperatureList = jdbcConnection.getSimilarTemperatureArray(String.valueOf(startYear_int), String.valueOf(stopYear_int), selectedCountry, selectedCity, selectedState, similarLimit);

                /*<th>Column 1</th>
                    <th>Column 2</th>
                    <th>Column 3</th>
                    <!-- Add more column headers as needed --> */
                StringBuilder Headers = new StringBuilder();
                Headers.append("<th> City </th> <th> City Temperature </th> <th> Country </th> <th> Country Temperature </th> <th> State </th> <th> State Temperature </th>");

                html = html.replace("{HEADER_DATA}", Headers.toString());

                /* <tr>
                    <td>Data 1</td>
                    <td>Data 2</td>
                    <td>Data 3</td>
                    <!-- Add more table rows and data as needed -->
                </tr> */
                StringBuilder tableData = new StringBuilder();
                for (List<String> Row : temperatureList) {
                    tableData.append("<tr>");
                    for (String item : Row) {
                        String value = "<td>" + item + "</td>";
                        tableData.append(value);
                    }
                    tableData.append("</tr>");
                }
                // Replace placeholder with actual population data HTML
                html = html.replace("{TABLE_DATA}", tableData.toString());
            }
        }

        {
        List<String> years = jdbcConnection.getGlobalYears();
            StringBuilder yearOptions = new StringBuilder();
            String selection = "1940 1980 1960 2000";
            for (String year : years) {
                if (selection.contains(year)) {
                    yearOptions.append("<option value='").append(year).append("' selected>").append(year).append("</option>");
                } else {
                    yearOptions.append("<option value='").append(year).append("'>").append(year).append("</option>");
                }
            }
            html = html.replace("{GLOBAL_YEAR_OPTIONS}", yearOptions.toString());
        }
        {
            List<String> countries = jdbcConnection.getCountries();
            StringBuilder countryOptions = new StringBuilder();
            String selection = "Italy Monaco Romania";
            for (String country : countries) {
                if (selection.contains(country)) {
                    countryOptions.append("<option value='").append(country).append("' selected>").append(country).append("</option>");
                } else {
                    countryOptions.append("<option value='").append(country).append("'>").append(country).append("</option>");
                }
            }
            html = html.replace("{COUNTRY_OPTIONS}", countryOptions.toString());
        }
        {
            List<String> states = jdbcConnection.getStates();
            StringBuilder stateOptions = new StringBuilder();
            String selection = "Alabama Florida Kirov";
            for (String state : states) {
                if (selection.contains(state)) {
                    stateOptions.append("<option value='").append(state).append("' selected>").append(state).append("</option>");
                } else {
                    stateOptions.append("<option value='").append(state).append("'>").append(state).append("</option>");
                }
            }
            html = html.replace("{STATE_OPTIONS}", stateOptions.toString());
        }
        {
            List<String> cities = jdbcConnection.getCities();
            StringBuilder cityOptions = new StringBuilder();
            String selection = "Detroit Naples Ede";
            for (String city : cities) {
                if (selection.contains(city)) {
                    cityOptions.append("<option value='").append(city).append("' selected>").append(city).append("</option>");
                } else {
                    cityOptions.append("<option value='").append(city).append("'>").append(city).append("</option>");
                }
            }
            html = html.replace("{CITY_OPTIONS}", cityOptions.toString());
        }


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
