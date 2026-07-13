package com.selloohub.leo.common.counter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("counters")
public class Counter {

    @Id
    private String id;
    private long seq;

    public Counter() {
    }

    public String getId() {
        return id;
    }

    public long getSeq() {
        return seq;
    }
}
