package org.santander.fx;

import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PriceCacheImpl implements PriceCache {

    private final Map<Instrument, Price> instrumentLatestPrices = new HashMap<>();
    private final Map<Instrument, LocalDateTime> lastUpdated = new HashMap<>();

    @Override
    public void addPrice(Price price) {
        Instrument instrument = price.getInstrument();
        LocalDateTime lastUpdatedTime = lastUpdated.get(instrument);
        if (lastUpdatedTime == null || price.getTimestamp().isAfter(lastUpdatedTime)) {
            // update
            instrumentLatestPrices.put(instrument, price);
            lastUpdated.put(instrument, price.getTimestamp());
        }
        // ignore if not most recent based on timestamp
    }

    @Override
    public Price getLatest(Instrument instrument) {
        return instrumentLatestPrices.get(instrument);
    }
}
