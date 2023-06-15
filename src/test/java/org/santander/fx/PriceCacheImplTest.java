package org.santander.fx;

import org.junit.Before;
import org.junit.Test;
import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class PriceCacheImplTest {

    private PriceCache priceCache;

    @Before
    public void beforeEach() {
        priceCache = new PriceCacheImpl();
    }

    @Test
    public void shouldNotFailWhenNoDataPopulated() {
        assertThatCode(() -> priceCache.getLatest(Instrument.EURUSD)).doesNotThrowAnyException();
    }

    @Test
    public void shouldGetLatestPrice() {
        Price eurUsd = TestUtil.createPrice(Instrument.EURUSD, 1.1011, 1.1019);
        priceCache.addPrice(eurUsd);
        Price latest = priceCache.getLatest(Instrument.EURUSD);
        assertThat(latest.getBid()).isEqualTo(1.1011);
        assertThat(latest.getAsk()).isEqualTo(1.1019);
    }

    @Test
    public void shouldOverridePriceWhenANewerOneIsAdded() {
        Price eurUsd = TestUtil.createPrice(Instrument.EURUSD, 1.1011, 1.1019);
        Price eurUsdLatest = TestUtil.createPrice(Instrument.EURUSD, 1.1012, 1.1020);
        priceCache.addPrice(eurUsd);
        priceCache.addPrice(eurUsdLatest);

        Price latest = priceCache.getLatest(Instrument.EURUSD);
        assertThat(latest.getBid()).isEqualTo(1.1012);
        assertThat(latest.getAsk()).isEqualTo(1.1020);
    }

    @Test
    public void shouldNotOverrideRecentPriceWithAnOlderOne() {
        Price gbpUsd = TestUtil.createPrice(Instrument.GBPUSD, 1.2011, 1.2019);
        Price gbpUsdOld = TestUtil.createPrice(Instrument.GBPUSD, 1.4012, 1.4020, LocalDateTime.now().minusDays(3));

        priceCache.addPrice(gbpUsd);
        priceCache.addPrice(gbpUsdOld);

        Price latest = priceCache.getLatest(Instrument.GBPUSD);
        assertThat(latest.getBid()).isEqualTo(1.2011);
        assertThat(latest.getAsk()).isEqualTo(1.2019);
    }

}