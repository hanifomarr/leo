package com.selloohub.leo.common.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneNormalizerTest {

    @ParameterizedTest
    @CsvSource({
            "013-2850 769, +60132850769",
            "+60132850769, +60132850769",
            "60132850769, +60132850769",
            "132850769, +60132850769"
    })
    void normalizesToE164(String input, String expected) {
        assertEquals(expected, PhoneNormalizer.normalize(input));
    }
}
