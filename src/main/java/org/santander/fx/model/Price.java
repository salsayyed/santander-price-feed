package org.santander.fx.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Price {

    private long uniqueId;
    private Instrument instrument;
    private double bid;
    private double ask;
    private LocalDateTime timestamp;

    public Price(long uniqueId, Instrument instrument, double bid, double ask, LocalDateTime timestamp) {
        this.uniqueId = uniqueId;
        this.instrument = instrument;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Price))
            return false;
        Price other = (Price) o;

        return this.uniqueId == other.uniqueId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uniqueId);
    }

    @Override
    public String toString() {
        return "Price{" +
                "uniqueId=" + uniqueId +
                ", instrument=" + instrument +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timestamp=" + timestamp +
                '}';
    }
}
