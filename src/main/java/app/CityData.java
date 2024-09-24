package app;

public class CityData {
    private String city;
    private double averageTemperature;
    private double minimumTemperature;
    private double maximumTemperature;
    private String country;
    private int startYear;
    private int endYear;

    public CityData(String city, double averageTemperature, double minimumTemperature, double maximumTemperature, String country) {
        this.city = city;
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.country = country;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    // Getters and Setters

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(double minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public double getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(double maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

   @Override
public String toString() {
    return "startYear=" + startYear +
            ", endYear=" + endYear +
            ", averageTemperature=" + averageTemperature +
            ", minimumTemperature=" + minimumTemperature +
            ", maximumTemperature=" + maximumTemperature +
            ", city='" + city + '\'' +
            ", country='" + country + '\'';
}

}