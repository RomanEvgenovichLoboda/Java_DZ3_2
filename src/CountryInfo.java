public class CountryInfo {
    private String country;
    private String capital;

    public CountryInfo() {
        capital = "";
        country = "";
    }

    public CountryInfo(String country, String capital) {
        this.country = country;
        this.capital = capital;
    }

    public CountryInfo(CountryInfo countryInfo) {
        this.country = countryInfo.getCountry();
        this.capital = countryInfo.getCapital();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Country: " + country + ", Capital: " + capital;
    }
}

