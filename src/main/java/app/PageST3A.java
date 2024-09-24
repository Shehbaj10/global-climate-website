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
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    private JDBCConnection jdbcConnection;

    public PageST3A() {
        this.jdbcConnection = new JDBCConnection();
    }

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Temperature Data</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='ST3A.css' />";
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
            <h1>Temperature Data</h1>
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
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding: 10px;
            background-color: #333333;

        }

        #configOptions label {
            margin-right: 10px;
        }

        #filters {
            margin-bottom: 20px;
        }

        #filters label {
            display: block;
            margin-bottom: 5px;
        }

        #results {
            margin-bottom: 20px;
        }

        #sortingForm {
            margin-bottom: 10px;
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
        #selectionForm select[name="startYear"],
        #selectionForm select[name="selected-country"],
        #selectionForm select[name="selected-state"],
        #selectionForm select[name="selected-city"],
        #selectionForm label[for="Global"] {
            background-color: #333333;
        }
    </style>
</head>
<body>
    <div id="configOptions">
        <form id="selectionForm" action="/page3A.html" method="POST">
        <div class="form-row">
            <label for="startYear">Starting Years:</label>
            <select id="startYear" name="startYear" size="5" multiple>
                <!-- Add more starting years as needed -->
                {GLOBAL_YEAR_OPTIONS}
            </select>
            </div>
            <div class="form-row">
            <label for='selected-country'>Select Country:</label>
            <select id='selected-country' name='selected-country' size="5" multiple>
                <!-- Populate dropdown options with countries from the database -->
                {COUNTRY_OPTIONS}
            </select>
            </div>
            <div class="form-row">
            <label for='selected-state'>Select State:</label>
            <select id='selected-state' name='selected-state' size="5" multiple>
                <!-- Populate dropdown options with countries from the database -->
                {STATE_OPTIONS}
            </select>
            </div>
            <div class="form-row">
            <label for='selected-city'>Select City:</label>
            <select id='selected-city' name='selected-city' size="5" multiple>
                <!-- Populate dropdown options with countries from the database -->
                {CITY_OPTIONS}
            </select>
            </div>
            <div class="form-row">
            <label for="yearRange">Year Range:</label>
            <input type='text' id='yearRange' name='yearRange' placeholder='5' required pattern='[0-9]{1,2}'>
            </div>
            <div class="form row">
            <input type="checkbox" id="Global" name="World">
            <label for="Global">The World</label>
            </div>
            <div class="form row">
            Note: Hold CTRL button for multiple selections.
            <button type='submit' style="padding: 6px 12px;">Get My Data</button> <!-- Adjusted button size -->
            </div>
        </form>
    </div>

    <div id="results">
        <h2>Results</h2>

        <form id="sortingForm" action="/pageST3A.html" method="GET">
            <label for="sortColumn">Sort by Column (Category):</label>
            <select id="sortColumn" name="sortColumn">
                <option value="Average_Temperature">Average Temperature</option>
                <option value="Minimum_Temperature">Minimum Temperature</option>
                <option value="Maximum_Temperature">Maximum Temperature</option>
                <!-- Add more columns as needed -->
            </select>

            <label for="sortOrder">Sort By (Best / Worst):</label>
            <select id="sortOrder" name="sortOrder">
                <option value="best">Best</option>
                <option value="worst">Worst</option>
            </select>

            <input type="submit" value="Sort">
        </form>

        <table>
            <thead>
                <tr>
                    {HEADER_DATA}
                </tr>
            </thead>
            <tbody>
                {TABLE_DATA}
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
            List<String> selectedStartYears = context.formParams("startYear");
            if(!selectedStartYears.isEmpty())
            {
                System.out.println("Start Years :-");
                for(String year : selectedStartYears)
                {
                    System.out.println(" " + year);
                }
            } else {
                System.out.println("Selected Start Years is empty");
            }
            List<String> selectedCountry = context.formParams("selected-country");
            if(!selectedCountry.isEmpty())
            {
                System.out.println("Country :-");
                for(String year : selectedCountry)
                {
                    System.out.println(" " + year);
                }
            } else {
                System.out.println("Selected country is empty");
            }
            List<String> selectedCity = context.formParams("selected-city");
            if(!selectedCity.isEmpty())
            {
                System.out.println("selectedCity :-");
                for(String year : selectedCity)
                {
                    System.out.println(" " + year);
                }
            } else {
                System.out.println("Selected selectedCity is empty");
            }
            List<String> selectedState = context.formParams("selected-state");
            if(!selectedState.isEmpty())
            {
                System.out.println("selectedState :-");
                for(String year : selectedState)
                {
                    System.out.println(" " + year);
                }
            } else {
                System.out.println("selectedState is empty");
            }

            if (range_text != null && !range_text.isEmpty() && !selectedStartYears.isEmpty()) {
                List<List<String>> yearRange = new ArrayList<List<String>>();

                int range_int = Integer.parseInt(range_text);
                for (String starYear : selectedStartYears) {
                    int startYear_int = Integer.parseInt(starYear);
                    int stopYear_int = startYear_int + range_int;
                    yearRange.add(List.of(String.valueOf(startYear_int), String.valueOf(stopYear_int)));
                }

                List<List<String>> temperatureList = jdbcConnection.getTemperatureArray(yearRange, selectedCountry, selectedCity, selectedState);

                /*<th>Column 1</th>
                    <th>Column 2</th>
                    <th>Column 3</th>
                    <!-- Add more column headers as needed --> */
                StringBuilder Headers = new StringBuilder();
                Headers.append("<th> City </th>");
                for (List<String> period : yearRange) {
                    String value = "<th>" + period.get(0) + "-" + period.get(1) + "</th>";
                    Headers.append(value);
                }
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