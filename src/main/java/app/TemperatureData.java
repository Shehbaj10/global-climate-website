package app;

public class TemperatureData {
    private int startYear;
    private int endYear;
    private double averageTemperature;
    private double minimumTemperature;
    private double maximumTemperature;

    public TemperatureData() {
    }

    public TemperatureData(int startYear, int endYear, double averageTemperature, double minimumTemperature, double maximumTemperature) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
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

    @Override
    public String toString() {
        return "{" +
                "startYear=" + startYear +
                ", endYear=" + endYear +
                ", averageTemperature=" + averageTemperature +
                ", minimumTemperature=" + minimumTemperature +
                ", maximumTemperature=" + maximumTemperature +
                '}';
    }
}
