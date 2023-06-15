package org.santander.fx.model;

public enum Instrument {

    EURUSD("EUR/USD", 1e4),
    EURJPY( "EUR/JPY", 1e2),
    GBPUSD("GBP/USD",1e4);

    private final String slashedName;
    private final double precision;

    Instrument(String slashedName, double precision) {
        this.slashedName = slashedName;
        this.precision = precision;
    }

    public double getPrecision() {
        return precision;
    }

    public String getSlashedName() {
        return slashedName;
    }
}
