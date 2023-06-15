package org.santander.fx;

import org.santander.fx.model.Adjustment;
import org.santander.fx.model.Price;

public class PriceProcessorImpl implements PriceProcessor {

    // in a real system adjustment would be modifiable
    private final Adjustment adjustment;
    private final PriceCache priceCache;

    public PriceProcessorImpl(Adjustment adjustment, PriceCache priceCache) {
        this.adjustment = adjustment;
        this.priceCache = priceCache;
    }

    @Override
    public void process(Price price) {
        double adjustedBid = price.getBid() + (price.getBid() * (adjustment.getPercentBid() / 100));
        double adjustedAsk = price.getAsk() + (price.getAsk() * (adjustment.getPercentAsk() / 100));

        double precision = price.getInstrument().getPrecision();

        double newBid = ((long)(adjustedBid * precision)) / precision;
        double newAsk = ((long)(adjustedAsk * precision)) / precision;

        price.setBid(newBid);
        price.setAsk(newAsk);

        priceCache.addPrice(price);
    }

}
