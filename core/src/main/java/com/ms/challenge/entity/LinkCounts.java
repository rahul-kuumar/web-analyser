package com.ms.challenge.entity;

public final class LinkCounts {
    private final int internal;
    private final int external;


    public LinkCounts(int internal, int external) {
        this.internal = internal;
        this.external = external;
    }

    public int getInternal() {
        return internal;
    }


    public int getExternal() {
        return external;
    }


}
