package com.jeyadevan.urlshortener.common;

public class SnowflakeIdGenerator {

    private final long EPOCH = 1704067200000L;

    // Bit allocations
    private final long MACHINE_ID_BITS = 10;
    private final long SEQUENCE_BITS = 12;

    private final long MAX_MACHINE_ID = (1L << MACHINE_ID_BITS) - 1;
    private final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    private final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;

    private final long machineId;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public SnowflakeIdGenerator(long machineId) {
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Invalid machine ID");
        }
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();

        // ❗ Clock moved backwards
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;

            // ❗ Sequence overflow
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }
}

