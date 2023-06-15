package org.santander.fx.subscriber;

import org.santander.fx.PriceCache;
import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;
import org.santander.fx.publisher.PricePublisher;

public class ClientSubscriberImpl implements ClientSubscriber {

    private final PriceCache priceCache;
    private final PricePublisher pricePublisher;

    public ClientSubscriberImpl(PriceCache priceCache, PricePublisher pricePublisher) {
        this.priceCache = priceCache;
        this.pricePublisher = pricePublisher;
    }

    @Override
    public void requestLatest(Instrument instrument) {
        pricePublisher.publish(priceCache.getLatest(instrument));
    }
}
