import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyRunnable implements Runnable {
    static CountryInfo countryInfo;
    public static ArrayList<CountryInfo> countryInfos;
    public ArrayList<CountryInfo> getCountryInfos(){
        return countryInfos;
    }
    String path;

    public MyRunnable(String path) {
        this.path = path;
        if (countryInfo == null) countryInfo = new CountryInfo();
        if (countryInfos == null) countryInfos = new ArrayList<>();
    }
    @Override
    public void run(){
        try(FileReader fileReader = new FileReader(path)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            synchronized (countryInfo){
            while ((line = bufferedReader.readLine()) != null){
                Thread.sleep(100);
                if (path.equals("countries.txt")) {
                    System.out.print(line + " --> ");
//                    synchronized (countryInfo){
                        countryInfo.setCountry(line);
                        countryInfo.notify();
                        countryInfo.wait();
//                    }
                } else {
                    System.out.print(line + "\n");
//                    synchronized (countryInfo){
                        countryInfo.setCapital(line);
                        countryInfos.add(new CountryInfo(countryInfo));
                        countryInfo.notify();
                    countryInfo.wait();

//                    }
                }
            }}
        } catch (IOException | InterruptedException e ) {
            System.err.println(e.getMessage());
        }
    }
}
