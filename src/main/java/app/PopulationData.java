package app;

public class PopulationData {
    private String countryName;
    private int startYear;
    private int endYear;
    private int population;

    public PopulationData(int startYear, int endYear, String countryName, int population) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.countryName = countryName;
        this.population = population;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "(" +
                "startYear=" + startYear +
                ", endYear=" + endYear +
                ", countryName='" + countryName + '\'' +
                ", population=" + population +
                ')';
    }
}
