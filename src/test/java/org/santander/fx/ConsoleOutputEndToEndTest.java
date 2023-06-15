package org.santander.fx;

import org.junit.Before;
import org.junit.Test;
import org.santander.fx.model.Adjustment;
import org.santander.fx.model.Instrument;
import org.santander.fx.publisher.ConsoleOutputPricePublisher;
import org.santander.fx.publisher.PricePublisher;
import org.santander.fx.subscriber.CSVFeedSubscriber;
import org.santander.fx.subscriber.ClientSubscriber;
import org.santander.fx.subscriber.ClientSubscriberImpl;
import org.santander.fx.subscriber.FeedSubscriber;

public class ConsoleOutputEndToEndTest {


    private PriceCache priceCache;
    private PriceProcessor priceProcessor;
    private FeedSubscriber<String> csvFeedSubscriber;

    private PricePublisher pricePublisher;
    private ClientSubscriber clientSubscriber;


    @Before
    public void setUp() {
        Adjustment adjustment = new Adjustment(-0.1,0.1);
        priceCache = new PriceCacheImpl();
        priceProcessor = new PriceProcessorImpl(adjustment, priceCache);
        pricePublisher = new ConsoleOutputPricePublisher();
        clientSubscriber = new ClientSubscriberImpl(priceCache, pricePublisher);

        csvFeedSubscriber = new CSVFeedSubscriber(priceProcessor);
    }

    @Test
    public void shouldPrintToConsole() {
        String feed = "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                "107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002\n" +
                "108,EUR/USD,1.1000,1.2000,01-06-2020 12:01:03:001\n" +
                "109,EUR/JPY,122.70,123.90,01-06-2020 12:00:02:002";

        csvFeedSubscriber.onMessage(feed);
        // should print price with uniqueId 108
        clientSubscriber.requestLatest(Instrument.EURUSD);
        // should print price with unique id 107 as its the latest by timestamp for EURJPY
        clientSubscriber.requestLatest(Instrument.EURJPY);
        //should print No price!
        clientSubscriber.requestLatest(Instrument.GBPUSD);
    }
}
