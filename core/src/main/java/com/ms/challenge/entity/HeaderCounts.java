package com.ms.challenge.entity;

public final class HeaderCounts {

    private final int h1;
    private final int h2;
    private final int h3;
    private final int h4;
    private final int h5;
    private final int h6;

    public HeaderCounts(int h1, int h2, int h3, int h4, int h5, int h6) {
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.h4 = h4;
        this.h5 = h5;
        this.h6 = h6;
    }

    public int getH1() {
        return h1;
    }

    public int getH2() {
        return h2;
    }

    public int getH3() {
        return h3;
    }

    public int getH4() {
        return h4;
    }

    public int getH5() {
        return h5;
    }

    public int getH6() {
        return h6;
    }
}
