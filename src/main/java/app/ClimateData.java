package app;
public class ClimateData {
    private int year;
    private double averageTemperature;
    private double minimumTemperature;
    private double maximumTemperature;
    private String country;

    public ClimateData(int year, double averageTemperature, double minimumTemperature,
                       double maximumTemperature, String country) {
        this.year = year;
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAverage_Temperature() {
        return averageTemperature;
    }

    public void setAverage_Temperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getMinimum_Temperature() {
        return minimumTemperature;
    }

    public void setMinimum_Temperature(double minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public double getMaximum_Temperature() {
        return maximumTemperature;
    }

    public void setMaximum_Temperature(double maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

  

    @Override
    public String toString() {
        return "ClimateData{" +
                "year=" + year +
                ", averageTemperature=" + averageTemperature +
                ", minimumTemperature=" + minimumTemperature +
                ", maximumTemperature=" + maximumTemperature ;
               
    }
}
