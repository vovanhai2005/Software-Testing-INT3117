package com.rental.Bai1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.rental.CarInsuranceSurcharge.calculateSurcharge;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("BVA — Kiểm thử phân tích giá trị biên")
class BoundaryValueTest {

    // ── BVA-A: Biên biến age (exp=5, Standard, false) ──────────────────
    @Test
    @DisplayName("BVA-A1: age=17 → -1 (dưới biên dưới)")
    void bva_A1() {
        assertEquals(-1, calculateSurcharge(17, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A2: age=18 → 0 (biên dưới hợp lệ)")
    void bva_A2() {
        assertEquals(0, calculateSurcharge(18, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A3: age=19 → 0 (trên biên dưới 1)")
    void bva_A3() {
        assertEquals(0, calculateSurcharge(19, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A4: age=24 → 15 (biên rủi ro cao)")
    void bva_A4() {
        assertEquals(15, calculateSurcharge(24, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A5: age=25 → 0 (thoát nhóm rủi ro)")
    void bva_A5() {
        assertEquals(0, calculateSurcharge(25, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A6: age=74 → 0 (dưới biên trên 1)")
    void bva_A6() {
        assertEquals(0, calculateSurcharge(74, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A7: age=75 → 0 (biên trên hợp lệ)")
    void bva_A7() {
        assertEquals(0, calculateSurcharge(75, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-A8: age=76 → -1 (trên biên trên)")
    void bva_A8() {
        assertEquals(-1, calculateSurcharge(76, 5, "Standard", false));
    }

    // ── BVA-E: Biên biến experienceYears (age=30, Standard, false) ────────
    @Test
    @DisplayName("BVA-E1: exp=-1 → -1 (dưới biên dưới)")
    void bva_E1() {
        assertEquals(-1, calculateSurcharge(30, -1, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E2: exp=0 → 15 (biên dưới, high-risk)")
    void bva_E2() {
        assertEquals(15, calculateSurcharge(30, 0, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E3: exp=1 → 15 (high-risk)")
    void bva_E3() {
        assertEquals(15, calculateSurcharge(30, 1, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E4: exp=2 → 15 (biên cao nhất trong high-risk)")
    void bva_E4() {
        assertEquals(15, calculateSurcharge(30, 2, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E5: exp=3 → 0 (thoát high-risk)")
    void bva_E5() {
        assertEquals(0, calculateSurcharge(30, 3, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E6: exp=49 → 0 (dưới biên trên 1)")
    void bva_E6() {
        assertEquals(0, calculateSurcharge(30, 49, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E7: exp=50 → 0 (biên trên hợp lệ)")
    void bva_E7() {
        assertEquals(0, calculateSurcharge(30, 50, "Standard", false));
    }

    @Test
    @DisplayName("BVA-E8: exp=51 → -1 (trên biên trên)")
    void bva_E8() {
        assertEquals(-1, calculateSurcharge(30, 51, "Standard", false));
    }

    // ── BVA-C: Biên biến carType (age=30, exp=5, false) ───────────────────
    @Test
    @DisplayName("BVA-C1: carType=null → -1")
    void bva_C1() {
        assertEquals(-1, calculateSurcharge(30, 5, null, false));
    }

    @Test
    @DisplayName("BVA-C2: carType=empty → -1")
    void bva_C2() {
        assertEquals(-1, calculateSurcharge(30, 5, "", false));
    }

    @Test
    @DisplayName("BVA-C3: carType=luxury (chữ thường) → -1")
    void bva_C3() {
        assertEquals(-1, calculateSurcharge(30, 5, "luxury", false));
    }

    @Test
    @DisplayName("BVA-C4: carType=Luxury → 30")
    void bva_C4() {
        assertEquals(30, calculateSurcharge(30, 5, "Luxury", false));
    }

    @Test
    @DisplayName("BVA-C5: carType=SUV → 10")
    void bva_C5() {
        assertEquals(10, calculateSurcharge(30, 5, "SUV", false));
    }

    @Test
    @DisplayName("BVA-C6: carType=Standard → 0")
    void bva_C6() {
        assertEquals(0, calculateSurcharge(30, 5, "Standard", false));
    }

    @Test
    @DisplayName("BVA-C7: carType=Economy → -1")
    void bva_C7() {
        assertEquals(-1, calculateSurcharge(30, 5, "Economy", false));
    }
}