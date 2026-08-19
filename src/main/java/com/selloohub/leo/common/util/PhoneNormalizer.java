package com.selloohub.leo.common.util;

public class PhoneNormalizer {

    private static final String COUNTRY_CODE = "60";

    public static String normalize(String phone) {
        String digits = phone.replaceAll("[^0-9]", "");

        if (digits.startsWith(COUNTRY_CODE)) {
            return "+" + digits;
        }
        if (digits.startsWith("0")) {
            return "+" + COUNTRY_CODE + digits.substring(1);
        }
        return "+" + COUNTRY_CODE + digits;
    }
}
