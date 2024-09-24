package app;

public class StateCountry {
    private int startYear;
    private int endYear;
    private double averageTemperature;
    private double minimumTemperature;
    private double maximumTemperature;
    private String state;
    private String country;

    public StateCountry(int startYear, double averageTemperature, double minimumTemperature, double maximumTemperature, String state, String country) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

   @Override
public String toString() {
    return "StateCountry{" +
            "startYear=" + startYear +
            ", endYear=" + endYear +
            ", averageTemperature=" + averageTemperature +
            ", minimumTemperature=" + minimumTemperature +
            ", maximumTemperature=" + maximumTemperature +
            ", state='" + state + '\'' +
            ", country='" + country + '\'' +
            '}';
}

}
