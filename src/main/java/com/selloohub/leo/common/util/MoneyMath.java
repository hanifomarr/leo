package com.selloohub.leo.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyMath {

    public static BigDecimal round2(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
