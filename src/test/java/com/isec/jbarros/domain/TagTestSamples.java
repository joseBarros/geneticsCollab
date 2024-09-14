package com.isec.jbarros.domain;

import java.util.UUID;

public class TagTestSamples {

    public static Tag getTagSample1() {
        return new Tag().id("id1").label("label1");
    }

    public static Tag getTagSample2() {
        return new Tag().id("id2").label("label2");
    }

    public static Tag getTagRandomSampleGenerator() {
        return new Tag().id(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
