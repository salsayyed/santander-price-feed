package org.santander.fx.model;

public class Adjustment {

    private final double percentBid;
    private final double percentAsk;


    public Adjustment(double percentBid, double percentAsk) {
        this.percentBid = percentBid;
        this.percentAsk = percentAsk;
    }

    public double getPercentBid() {
        return percentBid;
    }

    public double getPercentAsk() {
        return percentAsk;
    }
}
