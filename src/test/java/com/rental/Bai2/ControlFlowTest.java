package com.rental.Week4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.rental.CarInsuranceSurcharge.calculateSurcharge;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * C2 — Condition Coverage (Kiểm thử phủ điều kiện)
 *
 * Mục tiêu: Mỗi điều kiện con trong chương trình phải được thực thi
 * với cả giá trị TRUE và FALSE ít nhất một lần.
 *
 * Các điều kiện con được theo dõi:
 * D1: age < 18
 * D2: age > 75
 * D3: experienceYears < 0
 * D4: experienceYears > 50
 * D5: carType == null || carType không hợp lệ
 * D6: age < 25 (high-risk component)
 * D7: experienceYears < 3 (high-risk component)
 * D8: isHoliday
 *
 * Ma trận phủ:
 * ┌──────┬────┬────┬────┬────┬────┬────┬────┬────┐
 * │ ID │ D1 │ D2 │ D3 │ D4 │ D5 │ D6 │ D7 │ D8 │
 * ├──────┼────┼────┼────┼────┼────┼────┼────┼────┤
 * │ C2-01│ T │ - │ - │ - │ - │ - │ - │ - │ age<18
 * │ C2-02│ F │ T │ - │ - │ - │ - │ - │ - │ age>75
 * │ C2-03│ F │ F │ T │ - │ - │ - │ - │ - │ exp<0
 * │ C2-04│ F │ F │ F │ T │ - │ - │ - │ - │ exp>50
 * │ C2-05│ F │ F │ F │ F │ T │ - │ - │ - │ carType null
 * │ C2-06│ F │ F │ F │ F │ F │ T │ F │ F │ high-risk via age
 * │ C2-07│ F │ F │ F │ F │ F │ F │ T │ T │ high-risk via exp, lễ
 * │ C2-08│ F │ F │ F │ F │ F │ F │ F │ F │ bình thường, weekday
 * │ C2-09│ F │ F │ F │ F │ F │ F │ F │ T │ bình thường, lễ
 * │ C2-10│ F │ F │ F │ F │ F │ T │ T │ F │ cả 2 high-risk, SUV, weekday
 * │ C2-11│ F │ F │ F │ F │ F │ T │ T │ T │ cả 2 high-risk, SUV, lễ
 * │ C2-12│ F │ F │ F │ F │ F │ F │ F │ T │ normal, SUV, lễ
 * │ C2-13│ F │ F │ F │ F │ F │ F │ F │ F │ normal, SUV, weekday
 * │ C2-14│ F │ F │ F │ F │ F │ T │ F │ F │ high-risk, Luxury → từ chối
 * │ C2-15│ F │ F │ F │ F │ F │ F │ F │ F │ normal, Luxury, weekday
 * │ C2-16│ F │ F │ F │ F │ F │ F │ F │ T │ normal, Luxury, lễ
 * │ C2-17│ F │ F │ F │ F │ T │ - │ - │ - │ carType rỗng
 * │ C2-18│ F │ F │ F │ F │ T │ - │ - │ - │ carType sai chữ hoa
 * └──────┴────┴────┴────┴────┴────┴────┴────┴────┘
 */
@DisplayName("C2 — Kiểm thử phủ điều kiện (Condition Coverage)")
class ConditionCoverageTest {

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 1: Kiểm tra vi phạm ràng buộc đầu vào (D1–D5)
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 1 — Vi phạm ràng buộc đầu vào")
    class InputValidation {

        @Test
        @DisplayName("C2-01: age=17 → -1 | D1=T (age<18)")
        void c2_01_age_below_min() {
            // D1=T: kích hoạt điều kiện age<18 là TRUE
            assertEquals(-1, calculateSurcharge(17, 5, "Standard", false));
        }

        @Test
        @DisplayName("C2-02: age=76 → -1 | D1=F, D2=T (age>75)")
        void c2_02_age_above_max() {
            // D1=F (age không <18), D2=T: kích hoạt age>75 là TRUE
            assertEquals(-1, calculateSurcharge(76, 5, "Standard", false));
        }

        @Test
        @DisplayName("C2-03: exp=-1 → -1 | D1=F, D2=F, D3=T (exp<0)")
        void c2_03_exp_below_min() {
            // D3=T: kích hoạt điều kiện exp<0 là TRUE
            assertEquals(-1, calculateSurcharge(30, -1, "Standard", false));
        }

        @Test
        @DisplayName("C2-04: exp=51 → -1 | D1=F, D2=F, D3=F, D4=T (exp>50)")
        void c2_04_exp_above_max() {
            // D4=T: kích hoạt điều kiện exp>50 là TRUE
            assertEquals(-1, calculateSurcharge(30, 51, "Standard", false));
        }

        @Test
        @DisplayName("C2-05: carType=null → -1 | D5=T (null)")
        void c2_05_carType_null() {
            // D5=T: carType null → không hợp lệ
            assertEquals(-1, calculateSurcharge(30, 5, null, false));
        }

        @Test
        @DisplayName("C2-17: carType=\"\" → -1 | D5=T (chuỗi rỗng)")
        void c2_17_carType_empty() {
            // D5=T: carType rỗng → không hợp lệ
            assertEquals(-1, calculateSurcharge(30, 5, "", false));
        }

        @Test
        @DisplayName("C2-18: carType=\"luxury\" → -1 | D5=T (sai chữ hoa)")
        void c2_18_carType_wrong_case() {
            // D5=T: "luxury" không khớp case-sensitive với "Luxury"
            assertEquals(-1, calculateSurcharge(30, 5, "luxury", false));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 2: Phủ điều kiện D6 (age<25) và D7 (exp<3)
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 2 — Phủ điều kiện high-risk (D6, D7)")
    class HighRiskConditions {

        @Test
        @DisplayName("C2-06: age=22, exp=5 → 15 | D6=T (age<25), D7=F (exp>=3)")
        void c2_06_highRisk_via_age_only() {
            // D6=T, D7=F: high-risk CHỈ do age < 25
            // Standard + high-risk + weekday = 15
            assertEquals(15, calculateSurcharge(22, 5, "Standard", false));
        }

        @Test
        @DisplayName("C2-07: age=30, exp=1 → 20 | D6=F (age>=25), D7=T (exp<3), D8=T")
        void c2_07_highRisk_via_exp_only_holiday() {
            // D6=F, D7=T: high-risk CHỈ do exp < 3
            // D8=T: ngày lễ
            // Standard + high-risk + holiday = 20
            assertEquals(20, calculateSurcharge(30, 1, "Standard", true));
        }

        @Test
        @DisplayName("C2-08: age=30, exp=5 → 0 | D6=F, D7=F (bình thường), D8=F")
        void c2_08_normal_both_false_weekday() {
            // D6=F, D7=F: không high-risk; D8=F: ngày thường
            // Standard + normal + weekday = 0
            assertEquals(0, calculateSurcharge(30, 5, "Standard", false));
        }

        @Test
        @DisplayName("C2-09: age=30, exp=5 → 5 | D6=F, D7=F (bình thường), D8=T")
        void c2_09_normal_both_false_holiday() {
            // D6=F, D7=F; D8=T: ngày lễ → phủ D8=TRUE độc lập
            // Standard + normal + holiday = 5
            assertEquals(5, calculateSurcharge(30, 5, "Standard", true));
        }

        @Test
        @DisplayName("C2-10: age=20, exp=1 → 25 | D6=T, D7=T, D8=F (cả 2 high-risk)")
        void c2_10_highRisk_both_true_weekday() {
            // D6=T, D7=T: high-risk do cả 2 điều kiện; D8=F
            // SUV + high-risk + weekday = 25
            assertEquals(25, calculateSurcharge(20, 1, "SUV", false));
        }

        @Test
        @DisplayName("C2-11: age=22, exp=2 → 40 | D6=T, D7=T, D8=T")
        void c2_11_highRisk_both_true_holiday() {
            // D6=T, D7=T: high-risk; D8=T: ngày lễ
            // SUV + high-risk + holiday = 40
            assertEquals(40, calculateSurcharge(22, 2, "SUV", true));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 3: Phủ tất cả nhánh carType × isHoliday khi bình thường
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 3 — Phủ nhánh carType cho khách bình thường")
    class NormalDriverCarType {

        @Test
        @DisplayName("C2-12: SUV, normal, holiday → 20 | D8=T")
        void c2_12_suv_normal_holiday() {
            assertEquals(20, calculateSurcharge(30, 5, "SUV", true));
        }

        @Test
        @DisplayName("C2-13: SUV, normal, weekday → 10 | D8=F")
        void c2_13_suv_normal_weekday() {
            assertEquals(10, calculateSurcharge(30, 5, "SUV", false));
        }

        @Test
        @DisplayName("C2-15: Luxury, normal, weekday → 30 | D8=F")
        void c2_15_luxury_normal_weekday() {
            assertEquals(30, calculateSurcharge(30, 5, "Luxury", false));
        }

        @Test
        @DisplayName("C2-16: Luxury, normal, holiday → 50 | D8=T")
        void c2_16_luxury_normal_holiday() {
            assertEquals(50, calculateSurcharge(30, 5, "Luxury", true));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 4: Trường hợp từ chối và biên giới giữa 2 nhóm rủi ro
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 4 — Từ chối cho thuê và biên nhóm rủi ro")
    class RejectAndBoundary {

        @Test
        @DisplayName("C2-14: Luxury + high-risk (age=22) → -99 | D6=T")
        void c2_14_luxury_high_risk_rejected() {
            // Luxury + high-risk = từ chối, bất kể isHoliday
            assertEquals(-99, calculateSurcharge(22, 5, "Luxury", false));
        }

        @Test
        @DisplayName("Biên age=24 → vẫn high-risk, Standard, weekday → 15")
        void boundary_age24_still_high_risk() {
            // age=24 < 25 → D6=T → high-risk
            assertEquals(15, calculateSurcharge(24, 5, "Standard", false));
        }

        @Test
        @DisplayName("Biên age=25 → thoát high-risk (nếu exp>=3), Standard → 0")
        void boundary_age25_exit_high_risk() {
            // age=25 → D6=F; exp=3 → D7=F → không high-risk
            assertEquals(0, calculateSurcharge(25, 3, "Standard", false));
        }

        @Test
        @DisplayName("Biên exp=2 → vẫn high-risk (age>=25), Standard, holiday → 20")
        void boundary_exp2_still_high_risk() {
            // exp=2 < 3 → D7=T → high-risk dù age=25
            assertEquals(20, calculateSurcharge(25, 2, "Standard", true));
        }

        @Test
        @DisplayName("Biên exp=3 + age=25 → thoát hoàn toàn, Standard → 0")
        void boundary_both_exit_normal() {
            assertEquals(0, calculateSurcharge(25, 3, "Standard", false));
        }

        @Test
        @DisplayName("Biên trên hợp lệ: age=75, exp=50, Luxury, holiday → 50")
        void boundary_max_valid_all() {
            // age=75 ≤ 75 → D2=F; exp=50 ≤ 50 → D4=F; D6=F; D7=F
            assertEquals(50, calculateSurcharge(75, 50, "Luxury", true));
        }
    }
}