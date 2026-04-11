package com.rental.Bai3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.rental.CarInsuranceSurcharge.calculateSurcharge;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * All-Uses Coverage (Kiểm thử phủ tất cả các cặp định nghĩa – sử dụng)
 *
 * Mục tiêu: Với MỖI định nghĩa (def) của biến v tại node n, và MỖI sử dụng
 * (use) của v tại node m, tồn tại ít nhất một đường đi def-clear từ n đến m
 * được thực thi bởi một ca kiểm thử.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * Ánh xạ node CFG:
 *   (0)  Entry: calculateSurcharge(age, exp, carType, isHoliday)
 *   (1)  age < 18 ?
 *   (2)  age > 75 ?
 *   (3)  return -1            [age invalid]
 *   (4)  exp < 0 ?
 *   (5)  exp > 50 ?
 *   (6)  return -1            [exp invalid]
 *   (7)  carType == null ?
 *   (8)  !Luxury && !SUV && !Standard ?
 *   (9)  return -1            [carType null]
 *   (10) return -1            [carType invalid]
 *   (11) age < 25 ?
 *   (12) exp < 3 ?
 *   (13) isHighRisk = true
 *   (14) isHighRisk = false
 *   (15) switch(carType)
 *   (16) isHighRisk ?          [Luxury]
 *   (17) return -99
 *   (18) isHoliday ?           [Luxury, !HR]
 *   (19) return 50
 *   (20) return 30
 *   (21) isHighRisk ?          [SUV]
 *   (22) isHoliday ?           [SUV, HR]
 *   (23) return 40
 *   (24) return 25
 *   (25) isHoliday ?           [SUV, !HR]
 *   (26) return 20
 *   (27) return 10
 *   (28) isHighRisk ?          [Standard]
 *   (29) isHoliday ?           [Std, HR]
 *   (30) return 20
 *   (31) return 15
 *   (32) isHoliday ?           [Std, !HR]
 *   (33) return 5
 *   (34) return 0
 *   (35) return -1             [default]
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * BẢNG TẤT CẢ CÁC CẶP DEF-USE (35 cặp)
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Biến: age  (def tại node 0 – tham số)                                 │
 * ├────┬────────────┬──────────────────────────────────────┬───────────────┤
 * │ DU │ (def, use) │  Ý nghĩa                            │ Covered by    │
 * ├────┼────────────┼──────────────────────────────────────┼───────────────┤
 * │  1 │ (0, 1, T)  │  age < 18 = TRUE                    │ AU-01         │
 * │  2 │ (0, 1, F)  │  age < 18 = FALSE → đến node 2      │ AU-02         │
 * │  3 │ (0, 2, T)  │  age > 75 = TRUE                    │ AU-02         │
 * │  4 │ (0, 2, F)  │  age > 75 = FALSE → tiếp tục        │ AU-03         │
 * │  5 │ (0, 11, T) │  age < 25 = TRUE → isHighRisk       │ AU-08         │
 * │  6 │ (0, 11, F) │  age < 25 = FALSE → kiểm tra exp    │ AU-09         │
 * └────┴────────────┴──────────────────────────────────────┴───────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Biến: experienceYears  (def tại node 0 – tham số)                     │
 * ├────┬────────────┬──────────────────────────────────────┬───────────────┤
 * │  7 │ (0, 4, T)  │  exp < 0 = TRUE                     │ AU-03         │
 * │  8 │ (0, 4, F)  │  exp < 0 = FALSE → đến node 5       │ AU-04         │
 * │  9 │ (0, 5, T)  │  exp > 50 = TRUE                    │ AU-04         │
 * │ 10 │ (0, 5, F)  │  exp > 50 = FALSE → tiếp tục        │ AU-05         │
 * │ 11 │ (0, 12, T) │  exp < 3 = TRUE → isHighRisk=true   │ AU-09         │
 * │ 12 │ (0, 12, F) │  exp < 3 = FALSE → isHighRisk=false │ AU-10         │
 * └────┴────────────┴──────────────────────────────────────┴───────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Biến: carType  (def tại node 0 – tham số)                             │
 * ├────┬─────────────────┬─────────────────────────────────┬───────────────┤
 * │ 13 │ (0, 7, T)       │  carType == null = TRUE         │ AU-05         │
 * │ 14 │ (0, 7, F)       │  carType != null                │ AU-06         │
 * │ 15 │ (0, 8, T)       │  carType invalid (không L/S/St) │ AU-06         │
 * │ 16 │ (0, 8, F)       │  carType valid                  │ AU-08         │
 * │ 17 │ (0, 15, Luxury) │  switch → case "Luxury"         │ AU-12         │
 * │ 18 │ (0, 15, SUV)    │  switch → case "SUV"            │ AU-15         │
 * │ 19 │ (0, 15, Std)    │  switch → case "Standard"       │ AU-08         │
 * └────┴─────────────────┴─────────────────────────────────┴───────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Biến: isHoliday  (def tại node 0 – tham số)                          │
 * ├────┬────────────┬──────────────────────────────────────┬───────────────┤
 * │ 20 │ (0, 18, T) │  Luxury, !HR, holiday → 50          │ AU-14         │
 * │ 21 │ (0, 18, F) │  Luxury, !HR, weekday → 30          │ AU-13         │
 * │ 22 │ (0, 22, T) │  SUV, HR, holiday → 40              │ AU-16         │
 * │ 23 │ (0, 22, F) │  SUV, HR, weekday → 25              │ AU-15         │
 * │ 24 │ (0, 25, T) │  SUV, !HR, holiday → 20             │ AU-17         │
 * │ 25 │ (0, 25, F) │  SUV, !HR, weekday → 10             │ AU-07         │
 * │ 26 │ (0, 29, T) │  Std, HR, holiday → 20              │ AU-09         │
 * │ 27 │ (0, 29, F) │  Std, HR, weekday → 15              │ AU-08         │
 * │ 28 │ (0, 32, T) │  Std, !HR, holiday → 5              │ AU-11         │
 * │ 29 │ (0, 32, F) │  Std, !HR, weekday → 0              │ AU-10         │
 * └────┴────────────┴──────────────────────────────────────┴───────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Biến: isHighRisk  (def tại node 13 = true, node 14 = false)          │
 * ├────┬────────────┬──────────────────────────────────────┬───────────────┤
 * │ 30 │ (13,16, T) │  def=true,  Luxury  → -99           │ AU-12         │
 * │ 31 │ (13,21, T) │  def=true,  SUV     → vào node 22   │ AU-15         │
 * │ 32 │ (13,28, T) │  def=true,  Std     → vào node 29   │ AU-08         │
 * │ 33 │ (14,16, F) │  def=false, Luxury  → vào node 18   │ AU-13         │
 * │ 34 │ (14,21, F) │  def=false, SUV     → vào node 25   │ AU-07         │
 * │ 35 │ (14,28, F) │  def=false, Std     → vào node 32   │ AU-10         │
 * └────┴────────────┴──────────────────────────────────────┴───────────────┘
 *
 * Tổng: 35 cặp def-use, phủ bởi 17 ca kiểm thử (AU-01 → AU-17).
 */
@DisplayName("All-Uses — Kiểm thử phủ tất cả các cặp def-use")
class AllUsesTest {

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 1: Validation – Phủ def-use cho age, exp, carType khi invalid
    // DU #1, #2, #3, #4, #7, #8, #9, #10, #13, #14, #15
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 1 — Validation (def-use khi đầu vào vi phạm)")
    class ValidationDU {

        @Test
        @DisplayName("AU-01: age=17 → -1 | DU #1: (0,1,T) age<18=TRUE")
        void au01_age_lt18_true() {
            // Đường đi: 0 → 1(T) → 3
            // def(age,0) → p-use(age,1,T)
            assertEquals(-1, calculateSurcharge(17, 5, "Standard", false));
        }

        @Test
        @DisplayName("AU-02: age=76 → -1 | DU #2: (0,1,F), DU #3: (0,2,T)")
        void au02_age_gt75_true() {
            // Đường đi: 0 → 1(F) → 2(T) → 3
            // def(age,0) → p-use(age,1,F) rồi p-use(age,2,T)
            assertEquals(-1, calculateSurcharge(76, 5, "Standard", false));
        }

        @Test
        @DisplayName("AU-03: exp=-1 → -1 | DU #4: (0,2,F), DU #7: (0,4,T)")
        void au03_exp_lt0_true() {
            // Đường đi: 0 → 1(F) → 2(F) → 4(T) → 6
            // def(age,0) → p-use(age,2,F); def(exp,0) → p-use(exp,4,T)
            assertEquals(-1, calculateSurcharge(30, -1, "Standard", false));
        }

        @Test
        @DisplayName("AU-04: exp=51 → -1 | DU #8: (0,4,F), DU #9: (0,5,T)")
        void au04_exp_gt50_true() {
            // Đường đi: 0 → 1(F) → 2(F) → 4(F) → 5(T) → 6
            // def(exp,0) → p-use(exp,4,F) rồi p-use(exp,5,T)
            assertEquals(-1, calculateSurcharge(30, 51, "Standard", false));
        }

        @Test
        @DisplayName("AU-05: carType=null → -1 | DU #10: (0,5,F), DU #13: (0,7,T)")
        void au05_carType_null() {
            // Đường đi: 0 → ... → 5(F) → 7(T) → 9
            // def(exp,0) → p-use(exp,5,F); def(carType,0) → p-use(carType,7,T)
            assertEquals(-1, calculateSurcharge(30, 5, null, false));
        }

        @Test
        @DisplayName("AU-06: carType=\"Economy\" → -1 | DU #14: (0,7,F), DU #15: (0,8,T)")
        void au06_carType_invalid() {
            // Đường đi: 0 → ... → 7(F) → 8(T) → 10
            // def(carType,0) → p-use(carType,7,F) rồi p-use(carType,8,T)
            assertEquals(-1, calculateSurcharge(30, 5, "Economy", false));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 2: Standard – Phủ def-use cho isHighRisk, isHoliday trên Standard
    // DU #5, #6, #11, #12, #16, #19, #26, #27, #28, #29, #32, #35
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 2 — Standard (def-use trên phân khúc tiêu chuẩn)")
    class StandardDU {

        @Test
        @DisplayName("AU-07: age=30, exp=5, SUV, F → 10 | DU #25: (0,25,F), DU #34: (14,21,F)")
        void au07_suv_normal_weekday() {
            // Đường đi: 0 → ... → 11(F) → 12(F) → 14 → 15(SUV) → 21(F) → 25(F) → 27
            // def(isHoliday,0) → p-use(isHoliday,25,F)
            // def(isHighRisk,14) → p-use(isHighRisk,21,F)
            assertEquals(10, calculateSurcharge(30, 5, "SUV", false));
        }

        @Test
        @DisplayName("AU-08: age=22, exp=5, Standard, F → 15 | DU #5,16,19,27,32: age<25 HR+Std")
        void au08_standard_hr_via_age_weekday() {
            // Đường đi: 0 → ... → 8(F) → 11(T) → 13 → 15(Standard) → 28(T) → 29(F) → 31
            // def(age,0) → p-use(age,11,T)         — DU #5
            // def(carType,0) → p-use(carType,8,F)   — DU #16
            // def(carType,0) → p-use(carType,15,Std) — DU #19
            // def(isHoliday,0) → p-use(isHoliday,29,F) — DU #27
            // def(isHighRisk,13) → p-use(isHighRisk,28,T) — DU #32
            assertEquals(15, calculateSurcharge(22, 5, "Standard", false));
        }

        @Test
        @DisplayName("AU-09: age=30, exp=1, Standard, T → 20 | DU #6,11,26: exp<3 HR+Std+holiday")
        void au09_standard_hr_via_exp_holiday() {
            // Đường đi: 0 → ... → 11(F) → 12(T) → 13 → 15(Standard) → 28(T) → 29(T) → 30
            // def(age,0) → p-use(age,11,F)          — DU #6
            // def(exp,0) → p-use(exp,12,T)           — DU #11
            // def(isHoliday,0) → p-use(isHoliday,29,T) — DU #26
            assertEquals(20, calculateSurcharge(30, 1, "Standard", true));
        }

        @Test
        @DisplayName("AU-10: age=30, exp=5, Standard, F → 0 | DU #12,29,35: !HR+Std+weekday")
        void au10_standard_normal_weekday() {
            // Đường đi: 0 → ... → 11(F) → 12(F) → 14 → 15(Standard) → 28(F) → 32(F) → 34
            // def(exp,0) → p-use(exp,12,F)             — DU #12
            // def(isHoliday,0) → p-use(isHoliday,32,F) — DU #29
            // def(isHighRisk,14) → p-use(isHighRisk,28,F) — DU #35
            assertEquals(0, calculateSurcharge(30, 5, "Standard", false));
        }

        @Test
        @DisplayName("AU-11: age=30, exp=5, Standard, T → 5 | DU #28: (0,32,T) !HR+Std+holiday")
        void au11_standard_normal_holiday() {
            // Đường đi: 0 → ... → 14 → 15(Standard) → 28(F) → 32(T) → 33
            // def(isHoliday,0) → p-use(isHoliday,32,T) — DU #28
            assertEquals(5, calculateSurcharge(30, 5, "Standard", true));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 3: Luxury – Phủ def-use cho isHighRisk, isHoliday trên Luxury
    // DU #17, #20, #21, #30, #33
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 3 — Luxury (def-use trên phân khúc xe sang)")
    class LuxuryDU {

        @Test
        @DisplayName("AU-12: age=22, exp=5, Luxury, F → -99 | DU #17,30: HR+Luxury → từ chối")
        void au12_luxury_hr_rejected() {
            // Đường đi: 0 → ... → 11(T) → 13 → 15(Luxury) → 16(T) → 17
            // def(carType,0) → p-use(carType,15,Luxury) — DU #17
            // def(isHighRisk,13) → p-use(isHighRisk,16,T) — DU #30
            assertEquals(-99, calculateSurcharge(22, 5, "Luxury", false));
        }

        @Test
        @DisplayName("AU-13: age=30, exp=5, Luxury, F → 30 | DU #21,33: !HR+Luxury+weekday")
        void au13_luxury_normal_weekday() {
            // Đường đi: 0 → ... → 14 → 15(Luxury) → 16(F) → 18(F) → 20
            // def(isHoliday,0) → p-use(isHoliday,18,F) — DU #21
            // def(isHighRisk,14) → p-use(isHighRisk,16,F) — DU #33
            assertEquals(30, calculateSurcharge(30, 5, "Luxury", false));
        }

        @Test
        @DisplayName("AU-14: age=30, exp=5, Luxury, T → 50 | DU #20: (0,18,T) !HR+Luxury+holiday")
        void au14_luxury_normal_holiday() {
            // Đường đi: 0 → ... → 14 → 15(Luxury) → 16(F) → 18(T) → 19
            // def(isHoliday,0) → p-use(isHoliday,18,T) — DU #20
            assertEquals(50, calculateSurcharge(30, 5, "Luxury", true));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Nhóm 4: SUV – Phủ def-use cho isHighRisk, isHoliday trên SUV
    // DU #18, #22, #23, #24, #25, #31, #34
    // ══════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("Nhóm 4 — SUV (def-use trên phân khúc xe gầm cao)")
    class SUVDU {

        @Test
        @DisplayName("AU-15: age=22, exp=5, SUV, F → 25 | DU #18,23,31: HR+SUV+weekday")
        void au15_suv_hr_weekday() {
            // Đường đi: 0 → ... → 11(T) → 13 → 15(SUV) → 21(T) → 22(F) → 24
            // def(carType,0) → p-use(carType,15,SUV)       — DU #18
            // def(isHoliday,0) → p-use(isHoliday,22,F)     — DU #23
            // def(isHighRisk,13) → p-use(isHighRisk,21,T)  — DU #31
            assertEquals(25, calculateSurcharge(22, 5, "SUV", false));
        }

        @Test
        @DisplayName("AU-16: age=22, exp=5, SUV, T → 40 | DU #22: (0,22,T) HR+SUV+holiday")
        void au16_suv_hr_holiday() {
            // Đường đi: 0 → ... → 13 → 15(SUV) → 21(T) → 22(T) → 23
            // def(isHoliday,0) → p-use(isHoliday,22,T) — DU #22
            assertEquals(40, calculateSurcharge(22, 5, "SUV", true));
        }

        @Test
        @DisplayName("AU-17: age=30, exp=5, SUV, T → 20 | DU #24: (0,25,T) !HR+SUV+holiday")
        void au17_suv_normal_holiday() {
            // Đường đi: 0 → ... → 14 → 15(SUV) → 21(F) → 25(T) → 26
            // def(isHoliday,0) → p-use(isHoliday,25,T) — DU #24
            assertEquals(20, calculateSurcharge(30, 5, "SUV", true));
        }
    }
}
