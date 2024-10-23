package com.isec.jbarros.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedEntityTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NamedEntity getNamedEntitySample1() {
        return new NamedEntity().id("id1").text("text1").startChar(1).endChar(1);
    }

    public static NamedEntity getNamedEntitySample2() {
        return new NamedEntity().id("id2").text("text2").startChar(2).endChar(2);
    }

    public static NamedEntity getNamedEntityRandomSampleGenerator() {
        return new NamedEntity()
            .id(UUID.randomUUID().toString())
            .text(UUID.randomUUID().toString())
            .startChar(intCount.incrementAndGet())
            .endChar(intCount.incrementAndGet());
    }
}
