package com.selloohub.leo.order.service;

import com.selloohub.leo.common.counter.SequenceGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class OrderNumberGenerator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final SequenceGenerator sequenceGenerator;

    public OrderNumberGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public String generate() {
        String datePart = LocalDate.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DATE_FORMAT);
        long seq = sequenceGenerator.nextValue("order-" + datePart);

        return "ORD-" + datePart + "-" + String.format("%05d", seq);
    }
}
