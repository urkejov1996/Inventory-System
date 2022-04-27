import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        String path = "src/canned_food.csv";
        List<Can> canList = loadCansFromFile(path);

        CannedFoodWarehouse warehouse = new CannedFoodWarehouse(canList); //10 binova u skladistu
        warehouse.chooseAction();

    }

    public static List<Can> loadCansFromFile(String path) {
        List<Can> canList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withSkipLines(1).build();
            reader.forEach(canData -> {
                Can can = new Can();
                can.setId(Integer.parseInt(canData[0]));
                can.setType(canData[1]);
                can.setExpiryDate(LocalDate.parse(canData[2]));
                can.setBinId(Integer.parseInt(canData[3]));
                canList.add(can);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return canList;
    }

}


