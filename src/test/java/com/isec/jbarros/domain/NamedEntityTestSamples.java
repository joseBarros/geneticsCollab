package com.isec.jbarros.domain;

import java.util.UUID;

public class NamedEntityTestSamples {

    public static NamedEntity getNamedEntitySample1() {
        return new NamedEntity().id("id1").text("text1").startChar("startChar1").endChar("endChar1");
    }

    public static NamedEntity getNamedEntitySample2() {
        return new NamedEntity().id("id2").text("text2").startChar("startChar2").endChar("endChar2");
    }

    public static NamedEntity getNamedEntityRandomSampleGenerator() {
        return new NamedEntity()
            .id(UUID.randomUUID().toString())
            .text(UUID.randomUUID().toString())
            .startChar(UUID.randomUUID().toString())
            .endChar(UUID.randomUUID().toString());
    }
}
