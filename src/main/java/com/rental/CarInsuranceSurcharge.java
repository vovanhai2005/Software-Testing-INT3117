package com.rental;

public class CarInsuranceSurcharge {

    public static int calculateSurcharge(
            int age,
            int experienceYears,
            String carType,
            boolean isHoliday) {

        if (age < 18 || age > 75) return -1;
        if (experienceYears < 0 || experienceYears > 50) return -1;
        if (carType == null) return -1;
        if (!carType.equals("Luxury") && !carType.equals("SUV") && !carType.equals("Standard")) return -1;

        boolean isHighRisk = (age < 25) || (experienceYears < 3);

        switch (carType) {
            case "Luxury":
                if (isHighRisk) return -99;
                return isHoliday ? 50 : 30;
            case "SUV":
                if (isHighRisk) return isHoliday ? 40 : 25;
                return isHoliday ? 20 : 10;
            case "Standard":
                if (isHighRisk) return isHoliday ? 20 : 15;
                return isHoliday ? 5 : 0;
            default:
                return -1;
        }
    }
}