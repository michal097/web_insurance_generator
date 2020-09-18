package pl.mikel.insurance.service;

import org.springframework.stereotype.Service;

@Service
public class InsuranceService {

    private int base = 1000;

    public double fuelPrice(String fuel) {

        double fuelCost = 0;

        if (fuel.equals("diesel")) {
            fuelCost += base * 0.3;
        } else if (fuel.equals("benzyna")) {
            fuelCost += base * 0.2;
        } else {
            fuelCost += base * 0.1;
        }
        return fuelCost;
    }

    public double capacityPrice(Double capacity) {
        double capacityCost = 0;

        if (capacity > 1 && capacity < 1.5) {
            capacityCost = base * 0.1;
        } else if (capacity > 1.5 && capacity < 2) {
            capacityCost = base * 0.2;
        } else if (capacity > 2 && capacity < 2.5) {
            capacityCost = base * 0.23;
        } else if (capacity > 2.5 && capacity < 3) {
            capacityCost = base * 0.3;
        } else if (capacity > 3 && capacity < 4) {
            capacityCost = base * 0.37;
        } else if (capacity > 4 && capacity < 5) {
            capacityCost = base * 0.42;
        } else if (capacity > 5 && capacity < 5.5) {
            capacityCost = base * 0.6;
        } else {
            capacityCost = base * 0.75;
        }
        return capacityCost;
    }

    public double doorsPrice(Integer doors) {
        double doorsCost = 0;
        if (doors == 3) {
            doorsCost = base * 0.21;
        } else {
            doorsCost = base * 0.12;
        }
        return doorsCost;
    }

    public double mileagePrice(Integer mileage) {
        double mileageCost = 0;
        if (mileage < 500) {
            mileageCost = base * 0.01;
        } else if (mileage > 500 && mileage < 5000) {
            mileageCost = base * 0.05;
        } else if (mileage > 5000 && mileage < 50000) {
            mileageCost = base * 0.07;

        } else if (mileage > 50000 && mileage < 100000) {
            mileageCost = base * 0.1;
        } else if (mileage > 100000 && mileage < 120000) {
            mileageCost = base * 0.15;
        } else if (mileage > 120000 && mileage < 250000) {
            mileageCost = base * 0.19;
        } else {
            mileageCost = base * 0.23;
        }
        return mileageCost;
    }

    public double usagePrice(String usage) {
        double usageCost = 0;
        if (usage.equals("prywatnie")) {
            usageCost = base * 0.01;
        } else {
            usageCost = base * 2;
        }
        return usageCost;
    }

    public double userAgePrice(Integer age) {
        double ageCost = 0;

        if (age >= 18 && age < 50) {
            int multipleBy = 50 - age;
            ageCost = multipleBy * 0.015 * base;
        } else {
            int multipleBy = age - 50;
            ageCost = multipleBy * 0.015 * base;
        }
        return ageCost;
    }

    public double damagePrice(Integer damage) {
        double damageCost = 0;
        if (damage == 0) {
            damageCost = 0;
        } else {
            damageCost = damage * base * 0.6;
        }
        return damageCost;
    }

    public double prodYear(Integer year) {

        double prodYearCost = year - 1950;

        return prodYearCost * base * 0.014;

    }


    public Double calculateInsurance(Integer yearOfProd,
                                      String fuel,
                                      Double capacity,
                                      Integer doors,
                                      Integer age,
                                      Integer damage,
                                      Integer mileage,
                                      String usage
    ) {

        return  fuelPrice(fuel)+
                capacityPrice(capacity)+
                doorsPrice(doors)+
                mileagePrice(mileage)+
                usagePrice(usage)+
                userAgePrice(age)+
                damagePrice(damage)+
                prodYear(yearOfProd);

    }

}