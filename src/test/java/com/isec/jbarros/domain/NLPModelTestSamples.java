package com.isec.jbarros.domain;

import java.util.UUID;

public class NLPModelTestSamples {

    public static NLPModel getNLPModelSample1() {
        return new NLPModel().id("id1").name("name1").framework("framework1").url("url1").notes("notes1");
    }

    public static NLPModel getNLPModelSample2() {
        return new NLPModel().id("id2").name("name2").framework("framework2").url("url2").notes("notes2");
    }

    public static NLPModel getNLPModelRandomSampleGenerator() {
        return new NLPModel()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .framework(UUID.randomUUID().toString())
            .url(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
