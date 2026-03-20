package com.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarRentalSystemTest {

    private CarRentalSystem rentalSystem;

    @BeforeEach
    public void setUp() {
        rentalSystem = new CarRentalSystem();
    }

    @ParameterizedTest(name = "Test age={0}, exp={1} => Expected={2}")
    @CsvSource({
            // 1. KIỂM THỬ BIÊN CHO BIẾN `age` (Giữ experienceYears = 5)
            // Biên dưới của độ tuổi (18)
            "17, 5, -1", // Dưới 18: Lỗi đầu vào
            "18, 5,  3", // 18 tuổi, exp >=3: Phụ phí 50% (Quy tắc 3)
            "19, 5,  3", // 19 tuổi, exp >=3: Phụ phí 50%

            // Biên nghiệp vụ của độ tuổi (25)
            "24, 5,  3", // 24 tuổi, exp >=3: Phụ phí 50%
            "25, 5,  1", // 25 tuổi, exp >=3: Cơ bản (Quy tắc 1)
            "26, 5,  1", // 26 tuổi, exp >=3: Cơ bản

            // Biên trên của độ tuổi (75)
            "74, 5,  1", // 74 tuổi, exp >=3: Cơ bản
            "75, 5,  1", // 75 tuổi, exp >=3: Cơ bản
            "76, 5, -1", // Trên 75: Lỗi đầu vào

            // 2. KIỂM THỬ BIÊN CHO BIẾN `experienceYears` (Giữ age = 30)
            // Biên dưới của kinh nghiệm (0)
            "30, -1, -1", // Kinh nghiệm âm: Lỗi đầu vào
            "30,  0,  2", // Tuổi >=25, exp = 0: Phụ phí 20% (Quy tắc 2)
            "30,  1,  2", // Tuổi >=25, exp = 1: Phụ phí 20%

            // Biên nghiệp vụ của kinh nghiệm (3)
            "30,  2,  2", // Tuổi >=25, exp = 2: Phụ phí 20%
            "30,  3,  1", // Tuổi >=25, exp = 3: Cơ bản (Quy tắc 1)
            "30,  4,  1", // Tuổi >=25, exp = 4: Cơ bản

            // Biên trên của kinh nghiệm (50)
            "30, 49,  1", // Tuổi >=25, exp = 49: Cơ bản
            "30, 50,  1", // Tuổi >=25, exp = 50: Cơ bản
            "30, 51, -1"  // Trên 50 năm exp: Lỗi đầu vào
    })
    @DisplayName("Boundary Value Analysis for Age and Experience")
    public void testBoundaryValues(int age, int experienceYears, int expectedResult) {
        int actualResult = rentalSystem.calculateInsurancePremium(age, experienceYears);
        assertEquals(expectedResult, actualResult,
                "Failed at boundary - age: " + age + ", exp: " + experienceYears);
    }
}