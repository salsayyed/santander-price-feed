package org.santander.fx;

import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

public interface PriceCache {

    void addPrice(Price price);

    Price getLatest(Instrument instrument);
}
