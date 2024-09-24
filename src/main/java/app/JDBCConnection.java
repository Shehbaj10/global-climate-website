package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnection {
    public static final String DATABASE = "jdbc:sqlite:Database/climate.db";
    public List<String> getStatesByCountry;

    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT StudentId, name, email FROM Student";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id = results.getInt("StudentId");
                String name = results.getString("name");
                String email = results.getString("email");

                System.out.println("Retrieved Student: id=" + id + ", name=" + name + ", email=" + email);

                Student student = new Student();
                student.setId(id);
                student.setName(name);
                student.setEmail(email);
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return students;
    }

   public List<TemperatureData> getTemperatureData(int startYear, int endYear, String selectedView, String selectedCountry) {
    List<TemperatureData> temperatureDataList = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE)) {
        PreparedStatement stmt;

        if (selectedView.equals("country")) {
            String countryQuery = "SELECT * FROM country_temp WHERE Year >= ? AND Year <= ? AND Country = ?";
            stmt = connection.prepareStatement(countryQuery);
            stmt.setInt(1, startYear);
            stmt.setInt(2, endYear);
            stmt.setString(3, selectedCountry);
        } else {
            String worldQuery = "SELECT * FROM world WHERE Year >= ? AND Year <= ?";
            stmt = connection.prepareStatement(worldQuery);
            stmt.setInt(1, startYear);
            stmt.setInt(2, endYear);
        }
        
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int year = rs.getInt("Year");
            double averageTemperature = rs.getDouble("Average_Temperature");
            double minimumTemperature = rs.getDouble("Minimum_Temperature");
            double maximumTemperature = rs.getDouble("Maximum_Temperature");

            TemperatureData temperatureData = new TemperatureData(year, year, averageTemperature, minimumTemperature, maximumTemperature);
            temperatureDataList.add(temperatureData);
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }

    return temperatureDataList;
}

    public List<List<String> > getTemperatureArray(List<List<String> > yearRange, List<String> selectedCountry, List<String> selectedCity, List<String> selectedState) 
    {

        List<List<String>> TotalResult = new ArrayList<List<String>>();

        TotalResult.addAll(getTemperatureArray_City(yearRange, selectedCity));
        TotalResult.addAll(getTemperatureArray_Country(yearRange, selectedCountry));
        TotalResult.addAll(getTemperatureArray_State(yearRange, selectedState));
        return TotalResult;
    }

    public List<List<String> > getTemperatureArray_City(List<List<String> > yearRange,List<String> selectedCity)
    { 

    /*
        SELECT q1.c1 AS q1c, q1.t1 AS q1t, q2.t2 AS q2t, q3.t3 AS q3t, q4.t4 AS q4t
        FROM
            ((SELECT "City" AS c1, AVG ("Avg temperature") AS t1
            FROM city_temp
            WHERE ("City" = "Detroit" OR "City" = "Naples" OR "City" = "Ede") AND ("Year" >= "1940" AND "Year" <= "1945")
            GROUP BY c1) AS q1
        FULL OUTER JOIN
            (SELECT "City" AS c2, AVG ("Avg temperature") AS t2
            FROM city_temp
            WHERE ("City" = "Detroit" OR "City" = "Naples" OR "City" = "Ede") AND ("Year" >= "1980" AND "Year" <= "1985")
            GROUP BY c2) AS q2
        ON q1.c1 = q2.c2
        FULL OUTER JOIN  
           (SELECT "City" AS c3, AVG ("Avg temperature") AS t3
           FROM city_temp
           WHERE ("City" = "Detroit" OR "City" = "Naples" OR "City" = "Ede") AND ("Year" >= "1960" AND "Year" <= "1970")
           GROUP BY c3) AS q3
        ON q1.c1 = q3.c3
        FULL OUTER JOIN  
          (SELECT "City" AS c4, AVG ("Avg temperature") AS t4
          FROM city_temp
          WHERE ("City" = "Detroit" OR "City" = "Naples" OR "City" = "Ede") AND ("Year" >= "2000" AND "Year" <= "2010")
          GROUP BY c4) AS q4
        ON q1.c1= q4.c4)
        GROUP BY q1c
        ORDER BY q4t DESC
*/
        StringBuilder Query = new StringBuilder();
        String cityList = (selectedCity.size() > 1) ? (String.join("\" OR \"City\"=\"", selectedCity)) : (selectedCity.get(0));
        List<String> periodList = new ArrayList<String>();

        for (List<String> period : yearRange) {
            periodList.add("\"Year\" >= \"" + period.get(0) + "\" AND \"Year\" <= \"" + period.get(1) + "\"");
        }
        int N = periodList.size();
        Query.append("SELECT q1.c1 AS q1c");

        for (int i = 1; i <= N; ++i) {
            Query.append(String.format(", q%d.t%d AS q%dt",i,i,i));
        }
        Query.append(String.format("""
         
         FROM
            ((SELECT "City" AS c%d, AVG ("Average_temperature") AS t%d
            FROM city_temp
            WHERE ("City"="%s") AND (%s)
            GROUP BY c%d) AS q%d
        """,1,1,cityList,periodList.get(0),1,1));

        for (int i = 1; i < N; ++i) {
            Query.append(String.format("""
            FULL OUTER JOIN
                (SELECT "City" AS c%d, AVG ("Average_temperature") AS t%d
                FROM city_temp
                WHERE ("City"="%s") AND (%s)
                GROUP BY c%d) AS q%d
            ON q1.c1 = q%d.c%d
            """,i+1,i+1,cityList,periodList.get(i),i+1,i+1,i+1,i+1));
        }
        Query.append("""
        )
        GROUP BY q1c
        ORDER BY q1c DESC
        """);

        System.out.println(Query.toString());
        List<List<String> > Result = new ArrayList<List<String> >();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            PreparedStatement stmt = connection.prepareStatement(Query.toString());
            ResultSet rs = stmt.executeQuery(); 
            List<String> Row;
            while (rs.next()) {
                Row = new ArrayList<String>();
                Row.add(rs.getString("q1c"));
                for (int i = 1; i <= N; ++i) {
                    Row.add(String.format("%.2f",rs.getDouble(String.format("q%dt", i))));
                }
                Result.add(Row);
            }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return Result;
    }

   public List<List<String> > getTemperatureArray_Country(List<List<String> > yearRange, List<String> selectedCountry)
    {
        /*
        SELECT q1.c1 AS q1c, q1.t1 AS q1t, q2.t2 AS q2t, q3.t3 AS q3t, q4.t4 AS q4t
        FROM
            ((SELECT "Country" AS c1, AVG ("Average_temperature") AS t1
            FROM country_temp
            WHERE ("Country" = "Detroit" OR "Country" = "Naples" OR "Country" = "Ede") AND ("Year" >= "1940" AND "Year" <= "1945")
            GROUP BY c1) AS q1
        FULL OUTER JOIN
            (SELECT "Country" AS c2, AVG ("Average_temperature") AS t2
            FROM city_temp
            WHERE ("City" = "Detroit" OR "Country" = "Naples" OR "Country" = "Ede") AND ("Year" >= "1980" AND "Year" <= "1985")
            GROUP BY c2) AS q2
        ON q1.c1 = q2.c2
        FULL OUTER JOIN
           (SELECT "Country" AS c3, AVG ("Average_temperature") AS t3
           FROM country_temp
           WHERE ("Country" = "Detroit" OR "Country" = "Naples" OR "Country" = "Ede") AND ("Year" >= "1960" AND "Year" <= "1970")
           GROUP BY c3) AS q3
        ON q1.c1 = q3.c3
        FULL OUTER JOIN
          (SELECT "Country" AS c4, AVG ("Average_temperature") AS t4
          FROM country_temp
          WHERE ("Country" = "Detroit" OR "Country" = "Naples" OR "Country" = "Ede") AND ("Year" >= "2000" AND "Year" <= "2010")
          GROUP BY c4) AS q4
        ON q1.c1= q4.c4)
        GROUP BY q1c
        ORDER BY q4t DESC
*/
    
        StringBuilder Query = new StringBuilder();
        String countryList = (selectedCountry.size() > 1) ? (String.join("\" OR \"Country\"=\"", selectedCountry)) : (selectedCountry.get(0));
        List<String> periodList = new ArrayList<String>();

        for (List<String> period : yearRange) {
            periodList.add("\"Year\" >= \"" + period.get(0) + "\" AND \"Year\" <= \"" + period.get(1) + "\"");
        }
        int N = periodList.size();
        Query.append("SELECT q1.c1 AS q1c");

        for (int i = 1; i <= N; ++i) {
            Query.append(String.format(", q%d.t%d AS q%dt",i,i,i));
        }
        Query.append(String.format("""
         
         FROM
            ((SELECT "Country" AS c%d, AVG ("Average_temperature") AS t%d
            FROM country_temp
            WHERE ("Country"="%s") AND (%s)
            GROUP BY c%d) AS q%d
        """,1,1,countryList,periodList.get(0),1,1));

        for (int i = 1; i < N; ++i) {
            Query.append(String.format("""
            FULL OUTER JOIN
                (SELECT "Country" AS c%d, AVG ("Average_temperature") AS t%d
                FROM country_temp
                WHERE ("Country"="%s") AND (%s)
                GROUP BY c%d) AS q%d
            ON q1.c1 = q%d.c%d
            """,i+1,i+1,countryList,periodList.get(i),i+1,i+1,i+1,i+1));
        }
        Query.append("""
        )
        GROUP BY q1c
        ORDER BY q1c DESC
        """);

        System.out.println(Query.toString());
        List<List<String> > Result = new ArrayList<List<String> >();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            PreparedStatement stmt = connection.prepareStatement(Query.toString());
            ResultSet rs = stmt.executeQuery();
            List<String> Row;
            while (rs.next()) {
                Row = new ArrayList<String>();
                Row.add(rs.getString("q1c"));
                for (int i = 1; i <= N; ++i) {
                    Row.add(String.format("%.2f",rs.getDouble(String.format("q%dt", i))));
                }
                Result.add(Row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Result;
    }

     public List<List<String> > getTemperatureArray_State(List<List<String> > yearRange, List<String> selectedState)
    {
    /*
        SELECT q1.c1 AS q1c, q1.t1 AS q1t, q2.t2 AS q2t, q3.t3 AS q3t, q4.t4 AS q4t
        FROM
            ((SELECT "State" AS c1, AVG ("Avg temperature") AS t1
            FROM state_temp
            WHERE ("State" = "Detroit" OR "State" = "Naples" OR "State" = "Ede") AND ("Year" >= "1940" AND "Year" <= "1945")
            GROUP BY c1) AS q1
        FULL OUTER JOIN
            (SELECT "State" AS c2, AVG ("Avg temperature") AS t2
            FROM state_temp
            WHERE ("State" = "Detroit" OR "State" = "Naples" OR "State" = "Ede") AND ("Year" >= "1980" AND "Year" <= "1985")
            GROUP BY c2) AS q2
        ON q1.c1 = q2.c2
        FULL OUTER JOIN
           (SELECT "State" AS c3, AVG ("Avg temperature") AS t3
           FROM state_temp
           WHERE ("State" = "Detroit" OR "State" = "Naples" OR "State" = "Ede") AND ("Year" >= "1960" AND "Year" <= "1970")
           GROUP BY c3) AS q3
        ON q1.c1 = q3.c3
        FULL OUTER JOIN
          (SELECT "State" AS c4, AVG ("Avg temperature") AS t4
          FROM state_temp
          WHERE ("State" = "Detroit" OR "State" = "Naples" OR "State" = "Ede") AND ("Year" >= "2000" AND "Year" <= "2010")
          GROUP BY c4) AS q4
        ON q1.c1= q4.c4)
        GROUP BY q1c
        ORDER BY q4t DESC
*/      

        StringBuilder Query = new StringBuilder();
        String stateList = (selectedState.size() > 1) ? (String.join("\" OR \"State\"=\"", selectedState)) : (selectedState.get(0));
        List<String> periodList = new ArrayList<String>();

        for (List<String> period : yearRange) {
            periodList.add("\"Year\" >= \"" + period.get(0) + "\" AND \"Year\" <= \"" + period.get(1) + "\"");
        }
        int N = periodList.size();
        Query.append("SELECT q1.c1 AS q1c");

        for (int i = 1; i <= N; ++i) {
            Query.append(String.format(", q%d.t%d AS q%dt",i,i,i));
        }
        Query.append(String.format("""
         
         FROM
            ((SELECT "State" AS c%d, AVG ("Average_temperature") AS t%d
            FROM state_temp
            WHERE ("State"="%s") AND (%s)
            GROUP BY c%d) AS q%d
        """,1,1,stateList,periodList.get(0),1,1));

        for (int i = 1; i < N; ++i) {
            Query.append(String.format("""
            FULL OUTER JOIN
                (SELECT "State" AS c%d, AVG ("Average_temperature") AS t%d
                FROM state_temp
                WHERE ("State"="%s") AND (%s)
                GROUP BY c%d) AS q%d
            ON q1.c1 = q%d.c%d
            """,i+1,i+1,stateList,periodList.get(i),i+1,i+1,i+1,i+1));
        }
        Query.append("""
        )
        GROUP BY q1c
        ORDER BY q1c DESC
        """);

        System.out.println(Query.toString());
        List<List<String> > Result = new ArrayList<List<String> >();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            PreparedStatement stmt = connection.prepareStatement(Query.toString());
            ResultSet rs = stmt.executeQuery();
            List<String> Row;
            while (rs.next()) {
                Row = new ArrayList<String>();
                Row.add(rs.getString("q1c"));
                for (int i = 1; i <= N; ++i) {
                    Row.add(String.format("%.2f",rs.getDouble(String.format("q%dt", i))));
                }
                Result.add(Row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Result;
    }
    public List<PopulationData> getPopulationData(int startYear, int endYear, String selectedCountry) {
    List<PopulationData> populationDataList = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE)) {
        String query = "SELECT * FROM population_global WHERE Country_Name = ? AND Year >= ? AND Year <= ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, selectedCountry);
        stmt.setInt(2, startYear);
        stmt.setInt(3, endYear);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int year = rs.getInt("Year");
            int population = rs.getInt("Population");
            // Retrieve other columns as needed

            PopulationData populationData = new PopulationData(year, population, selectedCountry, population);
            populationDataList.add(populationData);
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }

    return populationDataList;
}

        
        public List<String> getCountries() {
            List<String> countries = new ArrayList<>();
    
            try (Connection conn = DriverManager.getConnection(DATABASE)) {
                String query = "SELECT DISTINCT Country FROM Country_temp";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
    
                while (resultSet.next()) {
                    String country = resultSet.getString("Country");
                    countries.add(country);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return countries;
        }

        public List<String> getCountryYears() {
            List<String> years = new ArrayList<>();
    
            try (Connection conn = DriverManager.getConnection(DATABASE)) {
                String query = "SELECT DISTINCT Year FROM Country_temp";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
    
                while (resultSet.next()) {
                    String year = resultSet.getString("Year");
                    years.add(year);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return years;
        }

        public List<String> getGlobalYears() {
            List<String> years = new ArrayList<>();
    
            try (Connection conn = DriverManager.getConnection(DATABASE)) {
                String query = "SELECT DISTINCT Year FROM World";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
    
                while (resultSet.next()) {
                    String year = resultSet.getString("Year");
                    years.add(year);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return years;
        }

        public List<String> getStateYears() {
            List<String> years = new ArrayList<>();
    
            try (Connection conn = DriverManager.getConnection(DATABASE)) {
                String query = "SELECT DISTINCT Year FROM State_temp";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
    
                while (resultSet.next()) {
                    String year = resultSet.getString("Year");
                    years.add(year);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return years;
        }

        public List<String> getCityYears() {
            List<String> years = new ArrayList<>();
    
            try (Connection conn = DriverManager.getConnection(DATABASE)) {
                String query = "SELECT DISTINCT Year FROM City_temp";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
    
                while (resultSet.next()) {
                    String year = resultSet.getString("Year");
                    years.add(year);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return years;
        }


        public List<TemperatureData> getTemperatureDataByCountry(int selectedYear, String selectedCountry) {
            return null;
        }
    


    public List<StateCountry> getStateCountryDataByCountryAndState(int startYear, int endYear, String selectedState) {
    List<StateCountry> stateCountryDataList = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE)) {
        String query = "SELECT Year, AVG(Average_Temperature) AS Average_Temperature, MIN(Minimum_Temperature) AS Minimum_Temperature, MAX(Maximum_Temperature) AS Maximum_Temperature, Country FROM state_temp WHERE State = ? AND Year >= ? AND Year <= ? GROUP BY Year, Country";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, selectedState);
        stmt.setInt(2, startYear);
        stmt.setInt(3, endYear);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int year = rs.getInt("Year");
            double averageTemperature = rs.getDouble("Average_Temperature");
            double minimumTemperature = rs.getDouble("Minimum_Temperature");
            double maximumTemperature = rs.getDouble("Maximum_Temperature");
            String country = rs.getString("Country");

            StateCountry stateCountry = new StateCountry(year, averageTemperature, minimumTemperature, maximumTemperature, selectedState, country);
            stateCountryDataList.add(stateCountry);
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }

    return stateCountryDataList;
}


    public List<String> getStates() {
    List<String> states = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE)) {
        String query = "SELECT DISTINCT State FROM state_temp";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String state = resultSet.getString("State");
            states.add(state);
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }

    return states;
}

public List<CityData> getCityDataByCity(String selectedCity, int startYear, int endYear) {
    List<CityData> cityDataList = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE)) {
        String query = "SELECT Average_Temperature, Minimum_Temperature, Maximum_Temperature, Country " +
                "FROM city_temp " +
                "WHERE City = ? AND Year >= ? AND Year <= ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        
        stmt.setString(1, selectedCity);
        stmt.setInt(2, startYear);
        stmt.setInt(3, endYear);
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            double averageTemperature = rs.getDouble("Average_Temperature");
            double minimumTemperature = rs.getDouble("Minimum_Temperature");
            double maximumTemperature = rs.getDouble("Maximum_Temperature");
            String country = rs.getString("Country");

            CityData cityData = new CityData(selectedCity, averageTemperature, minimumTemperature, maximumTemperature, country);
            cityData.setStartYear(startYear);
            cityData.setEndYear(endYear);
            cityDataList.add(cityData);
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }

    return cityDataList;
}




  public List<String> getCities() {
    List<String> cities = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE)) {
        String query = "SELECT DISTINCT City FROM city_temp";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String city = resultSet.getString("City");
            cities.add(city);
            //System.out.println("Retrieved city: " + city);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return cities;
}


public List<List<String> > getSimilarTemperatureArray(String start_year,String stop_year,
                                                          String selectedCountry,
                                                          String selectedCity,
                                                          String selectedState,
                                                          int limit)
    {

        List<List<String>> TotalResult = new ArrayList<List<String>>();
        List<List<String>> CityResult= getSimilarTemperatureArray_City(start_year,stop_year, selectedCity,limit);
        List<List<String>> CountryResult= getSimilarTemperatureArray_Country(start_year,stop_year, selectedCountry,limit);
        List<List<String>> StateResult= getSimilarTemperatureArray_State(start_year,stop_year, selectedState,limit);
        List<String> row;
        for (int i = 0;i<limit;++i)
        {   
            if (CityResult.size() > i) {
                row = CityResult.get(i);

            } else {
                row = new ArrayList <String>();
                row.add("");
                row.add("");
            }
            row= CityResult.get(i);
            if (CountryResult.size() > i) {
                row.addAll(CountryResult.get(i)); }

                else {  
                    row.add("");
                    row.add("");
                }
                if (StateResult.size() > i) {
                    row.addAll(StateResult.get(i));
                } else {
                    row.add("");
                    row.add("");
                }
            TotalResult.add(row);
        }
        return TotalResult;
    }

    public List<List<String> > getSimilarTemperatureArray_City(String start_year,String stop_year,
                                                               String selectedCity,
                                                               int limit)
    {
    /*
     SELECT City, Average_Temperature
     FROM city_temp
     WHERE (Year Between 1950 AND 1970) AND Average_temperature like (
     Select Average_temperature
     FROM city_temp
     where City="Naples" and Year Between 1950 AND 1970 )
     Limit 70
    */
        String Query = String.format("""
                SELECT City , Average_Temperature
                FROM city_temp
                WHERE (Year Between %s AND %s) AND Average_temperature like (
                Select Average_temperature
                FROM city_temp
                where City="%s" and Year Between %s AND %s )
                Limit %d
                """,start_year,stop_year,selectedCity,start_year,stop_year,limit);

        System.out.println(Query);
        List<List<String> > Result = new ArrayList<List<String> >();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            PreparedStatement stmt = connection.prepareStatement(Query);
            ResultSet rs = stmt.executeQuery();
            List<String> Row;
            while (rs.next()) {
                Row = new ArrayList<String>();
                Row.add(rs.getString("City"));
                Row.add(String.format("%.2f",rs.getDouble("Average_Temperature")));
                Result.add(Row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Result;
    }
    public List<List<String> > getSimilarTemperatureArray_Country(String start_year,String stop_year,
                                                               String selectedCountry,
                                                               int limit)
    {
    /*
     SELECT Country, Average_Temperature
     FROM city_temp
     WHERE (Year Between 1950 AND 1970) AND Average_temperature like (
     Select Average_temperature
     FROM city_temp
     where City="Naples" and Year Between 1950 AND 1970 )
     Limit 70
    */
        String Query = String.format("""
                SELECT Country , Average_Temperature
                FROM country_temp
                WHERE (Year Between %s AND %s) AND Average_temperature like (
                Select Average_temperature
                FROM country_temp
                where Country="%s" and Year Between %s AND %s )
                Limit %d
                """,start_year,stop_year,selectedCountry,start_year,stop_year,limit);

        System.out.println(Query);
        List<List<String> > Result = new ArrayList<List<String> >();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            PreparedStatement stmt = connection.prepareStatement(Query);
            ResultSet rs = stmt.executeQuery();
            List<String> Row;
            while (rs.next()) {
                Row = new ArrayList<String>();
                Row.add(rs.getString("COuntry"));
                Row.add(String.format("%.2f",rs.getDouble("Average_Temperature")));
                Result.add(Row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Result;
    }
    public List<List<String> > getSimilarTemperatureArray_State(String start_year,String stop_year,
                                                               String selectedState,
                                                               int limit)
    {
    /*
     SELECT State, Average_Temperature
     FROM state_temp
     WHERE (Year Between 1950 AND 1970) AND Average_temperature like (
     Select Average_temperature
     FROM state_temp
     where State="Naples" and Year Between 1950 AND 1970 )
     Limit 70
    */
        String Query = String.format("""
                SELECT State , Average_Temperature
                FROM state_temp
                WHERE (Year Between %s AND %s) AND Average_temperature like (
                Select Average_temperature
                FROM state_temp
                where State="%s" and Year Between %s AND %s )
                Limit %d
                """,start_year,stop_year,selectedState,start_year,stop_year,limit);

        System.out.println(Query);
        List<List<String> > Result = new ArrayList<List<String> >();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            PreparedStatement stmt = connection.prepareStatement(Query);
            ResultSet rs = stmt.executeQuery();
            List<String> Row;
            while (rs.next()) {
                Row = new ArrayList<String>();
                Row.add(rs.getString("State"));
                Row.add(String.format("%.2f",rs.getDouble("Average_Temperature")));
                Result.add(Row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Result;
    }

}



