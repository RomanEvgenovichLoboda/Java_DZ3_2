//Создайте два текстовых файла с именами, например, countries.txt и capitals.txt. В первом файле должны быть перечислены названия стран,
//во втором — названия их столиц в той очередности, в которой в первом файле перечислены страны. Например:
//        countries.txt
//        Austria China Brazil Netherlands Ukraine Canada
//        capitals.txt
//        Vienna Beijing Brasilia Amsterdam Kyiv Ottawa
//Не поленитесь и занесите в файлы не менее десяти значений. Разделитель выберите по своему усмотрению, можете даже заносить каждое значение в отдельной строке.
//Теперь вам надо создать приложение, в котором будет два отдельных потока для чтения каждого из файлов. Причем потоки должны работать таким образом. Первый поток читает название
//первой страны из своего файла и останавливается, передав второму потоку эстафету чтения. Второй поток читает название очередной столицы. Когда прочитано и название страны и название ее столицы,
//приложение должно создать объект класса CountryInfo.
//        Class CountryInfo
//        {
//            private String country;
//            private String capital;
//            . . .
//        }
//созданный объект добавляется в коллекцию. Таким образом должны быть прочитаны оба файла и создана коллекция объектов CountryInfo.
//После этого коллекция должна быть отсортирована по полю country и выведена на экран. Затем коллекция должна быть отсортирована по полю capital и тоже выведена на экран.
//Потоки ввода-вывода, вид коллекции, способы синхронизации потоков и способ сортировки выберите самостоятельно. Использование лямбда-выражений и анонимных классов приветствуется.

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        writeFiles();
        System.out.println("\n\tFrom Files :");
        MyRunnable runCountries = new MyRunnable("countries.txt");
        MyRunnable runCapitals = new MyRunnable("capitals.txt");
        Thread threadCountries = new Thread(runCountries);
        Thread threadCapitals = new Thread(runCapitals);
        threadCapitals.setDaemon(true);
        threadCountries.setDaemon(true);

        threadCountries.start();
        threadCapitals.start();
//        try{
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        try{
            threadCountries.join(2000);
            threadCapitals.join(2000);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        System.out.println("\n\tFrom ArrayList :");
        MyRunnable.countryInfos.forEach(System.out::println);
        System.out.println("\n\tSorted by Countries :");
        MyRunnable.countryInfos.sort(Comparator.comparing(CountryInfo::getCountry));
        MyRunnable.countryInfos.forEach(System.out::println);
        System.out.println("\n\tSorted by Capitals :");
        MyRunnable.countryInfos.sort(Comparator.comparing(CountryInfo::getCapital));
        MyRunnable.countryInfos.forEach(System.out::println);

    }

    public static void writeFiles() {
        if (!Files.exists(Paths.get("countries.txt"))) {
            String countries = "Austria\nChina\nBrazil\nNetherlands\nUkraine\nCanada\nNepal\nBangladesh\nPakistan\nMaldives";
            try (FileWriter fileWriter = new FileWriter("countries.txt", false)) {
                fileWriter.write(countries);
                fileWriter.flush();
                System.out.println("Writed countries.txt");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        if (!Files.exists(Paths.get("capitals.txt"))) {
            String capitals = "Vienna\nBeijing\nBrasilia\nAmsterdam\nKyiv\nOttawa\nKathmandu\nDhaka\nIslamabad\nMale";
            try (FileWriter fileWriter = new FileWriter("capitals.txt", false)) {
                fileWriter.write(capitals);
                fileWriter.flush();
                System.out.println("Writed capitals.txt");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
