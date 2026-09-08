package com.selloohub.leo.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MoneyMathTest {

    @Test
    void roundsHalfUp_atBoundary() {
        BigDecimal result = MoneyMath.round2(new BigDecimal("2.005"));
        assertEquals(new BigDecimal("2.01"), result);
    }

    @Test
    void noOp_whenAlreadyTwoDecimalPlaces() {
        BigDecimal result = MoneyMath.round2(new BigDecimal("2.50"));
        assertEquals(new BigDecimal("2.50"), result);
    }

    @Test
    void truncatesDown_belowHalfBoundary() {
        BigDecimal result = MoneyMath.round2(new BigDecimal("2.004"));
        assertEquals(new BigDecimal("2.00"), result);
    }

    @Test
    void roundsHalfUp_negativeAwayFromZero() {
        BigDecimal result = MoneyMath.round2(new BigDecimal("-2.005"));
        assertEquals(new BigDecimal("-2.01"), result);
    }
}
