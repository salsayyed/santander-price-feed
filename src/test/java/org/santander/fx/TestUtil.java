package org.santander.fx;

import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

import java.time.LocalDateTime;

public class TestUtil {

    public static Price createPrice(Instrument instrument, double bid, double ask) {
        return createPrice(instrument, bid, ask, LocalDateTime.now());
    }

    public static Price createPrice(Instrument instrument, double bid, double ask, LocalDateTime timestamp) {
        return new Price(123,instrument,bid,ask,timestamp);
    }
}
