package com.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.rental.CarInsuranceSurcharge.calculateSurcharge;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DT — Kiểm thử bảng quyết định")
class DecisionTableTest {

    // ── Nhóm 1: Luxury ────────────────────────────────────────────────────
    @Nested @DisplayName("Luxury")
    class Luxury {
        @Test @DisplayName("DT-L1: high-risk + ngày thường → -99")
        void dt_L1() { assertEquals(-99, calculateSurcharge(22, 5, "Luxury", false)); }

        @Test @DisplayName("DT-L2: high-risk + ngày lễ → -99")
        void dt_L2() { assertEquals(-99, calculateSurcharge(30, 1, "Luxury", true)); }

        @Test @DisplayName("DT-L3: bình thường + ngày thường → 30")
        void dt_L3() { assertEquals(30, calculateSurcharge(30, 5, "Luxury", false)); }

        @Test @DisplayName("DT-L4: bình thường + ngày lễ → 50")
        void dt_L4() { assertEquals(50, calculateSurcharge(30, 5, "Luxury", true)); }
    }

    // ── Nhóm 2: SUV ───────────────────────────────────────────────────────
    @Nested @DisplayName("SUV")
    class SUV {
        @Test @DisplayName("DT-S1: high-risk + ngày thường → 25")
        void dt_S1() { assertEquals(25, calculateSurcharge(20, 5, "SUV", false)); }

        @Test @DisplayName("DT-S2: high-risk + ngày lễ → 40")
        void dt_S2() { assertEquals(40, calculateSurcharge(20, 5, "SUV", true)); }

        @Test @DisplayName("DT-S3: bình thường + ngày thường → 10")
        void dt_S3() { assertEquals(10, calculateSurcharge(30, 5, "SUV", false)); }

        @Test @DisplayName("DT-S4: bình thường + ngày lễ → 20")
        void dt_S4() { assertEquals(20, calculateSurcharge(30, 5, "SUV", true)); }
    }

    // ── Nhóm 3: Standard ──────────────────────────────────────────────────
    @Nested @DisplayName("Standard")
    class Standard {
        @Test @DisplayName("DT-T1: high-risk + ngày thường → 15")
        void dt_T1() { assertEquals(15, calculateSurcharge(19, 0, "Standard", false)); }

        @Test @DisplayName("DT-T2: high-risk + ngày lễ → 20")
        void dt_T2() { assertEquals(20, calculateSurcharge(19, 0, "Standard", true)); }

        @Test @DisplayName("DT-T3: bình thường + ngày thường → 0")
        void dt_T3() { assertEquals(0, calculateSurcharge(30, 5, "Standard", false)); }

        @Test @DisplayName("DT-T4: bình thường + ngày lễ → 5")
        void dt_T4() { assertEquals(5, calculateSurcharge(30, 5, "Standard", true)); }
    }

    // ── Nhóm 4: Ca đặc biệt (biên high-risk kết hợp) ─────────────────────
    @Nested @DisplayName("Ca đặc biệt — biên nhóm rủi ro")
    class EdgeCases {
        @Test @DisplayName("DT-X1: Cả 2 điều kiện high-risk → SUV 25")
        void dt_X1() { assertEquals(25, calculateSurcharge(22, 1, "SUV", false)); }

        @Test @DisplayName("DT-X2: Đúng biên thoát (age=25, exp=3) → 0")
        void dt_X2() { assertEquals(0, calculateSurcharge(25, 3, "Standard", false)); }

        @Test @DisplayName("DT-X3: age=24 (age<25) → vẫn high-risk → 20")
        void dt_X3() { assertEquals(20, calculateSurcharge(24, 3, "Standard", true)); }

        @Test @DisplayName("DT-X4: exp=2 (exp<3) → vẫn high-risk → 20")
        void dt_X4() { assertEquals(20, calculateSurcharge(25, 2, "Standard", true)); }

        @Test @DisplayName("DT-X5: Biên trên tất cả → Luxury holiday → 50")
        void dt_X5() { assertEquals(50, calculateSurcharge(75, 50, "Luxury", true)); }

        @Test @DisplayName("DT-X6: Biên dưới + high-risk + Luxury → -99")
        void dt_X6() { assertEquals(-99, calculateSurcharge(18, 0, "Luxury", true)); }
    }
}