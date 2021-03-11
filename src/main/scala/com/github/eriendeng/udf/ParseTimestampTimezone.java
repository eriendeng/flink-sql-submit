package com.github.eriendeng.udf;

import java.sql.Timestamp;

import org.apache.flink.table.functions.ScalarFunction;


public class ParseTimestampTimezone extends ScalarFunction {

    /**
     * window的时区有问题，parse一个8小时
     */
    public Timestamp eval(Timestamp s) {
        long timestamp = s.getTime() + 28800000;
        return new Timestamp(timestamp);
    }
}