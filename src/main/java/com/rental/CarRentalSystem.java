package com.rental;

public class CarRentalSystem {

    /**
     * Hàm đánh giá điều kiện thuê xe và tính phí bảo hiểm.
     * * @param age Độ tuổi người thuê (18 - 75)
     * @param experienceYears Số năm kinh nghiệm (0 - 50)
     * @return 1 (Cơ bản), 2 (Phụ phí 20%), 3 (Phụ phí 50%), 0 (Từ chối), hoặc -1 (Lỗi đầu vào)
     */
    public int calculateInsurancePremium(int age, int experienceYears) {
        // 1. Kiểm tra giới hạn hợp lệ của đầu vào (Domain constraints)
        if (age < 18 || age > 75 || experienceYears < 0 || experienceYears > 50) {
            return -1; // Lỗi dữ liệu
        }

        // 2. Áp dụng các quy tắc nghiệp vụ (Business Rules)
        boolean isAgeValid = age >= 25;
        boolean isExpValid = experienceYears >= 3;

        if (isAgeValid && isExpValid) {
            return 1; // Quy tắc 1: Phí cơ bản
        } else if (isAgeValid && !isExpValid) {
            return 2; // Quy tắc 2: Phụ phí 20%
        } else if (!isAgeValid && isExpValid) {
            return 3; // Quy tắc 3: Phụ phí 50%
        } else {
            return 0; // Quy tắc 4: Từ chối cho thuê
        }
    }
}