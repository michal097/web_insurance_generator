package pl.mikel.importFile;

import pl.mikel.insurance.dao.InsuranceDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadDataFromCsv {

    public static List<InsuranceDao> readData(String file, String actualUser) {
        String line = "";
        String cvsSplitBy = ";";
        List<InsuranceDao> massUpload = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                count++;           //nie czyta pierwszej linii
                if (count == 1) {  //w pliku, bo jest zarezerwowana
                    continue;      //na nagłówek
                }
                InsuranceDao insuranceDao = new InsuranceDao();

                String[] insuranceData = line.split(cvsSplitBy);
                insuranceDao.setClientName(insuranceData[0]);
                insuranceDao.setClientSurname(insuranceData[1]);
                insuranceDao.setCarModel(insuranceData[2]);
                insuranceDao.setCarSubModel(insuranceData[3]);
                insuranceDao.setYearOfProduction(Integer.valueOf(insuranceData[4]));
                insuranceDao.setFuel(insuranceData[5]);
                insuranceDao.setCapacity(Double.valueOf(insuranceData[6]));
                insuranceDao.setDoors(Integer.valueOf(insuranceData[7]));
                insuranceDao.setMileage(Integer.valueOf(insuranceData[8]));
                insuranceDao.setUsage(insuranceData[9]);
                insuranceDao.setAgeOfUser(Integer.valueOf(insuranceData[10]));
                insuranceDao.setActualUser(actualUser);
                insuranceDao.setDamage(Integer.valueOf(insuranceData[11]));

                massUpload.add(insuranceDao);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return massUpload;
    }

}
