package org.santander.fx;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.santander.fx.model.Adjustment;
import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PriceProcessorImplTest {

    private PriceProcessor priceProcessor;
    @Mock
    private PriceCache priceCache;
    @Captor
    private ArgumentCaptor<Price> priceCaptor;

    @Before
    public void beforeEach() {
        Adjustment adjustment = new Adjustment(-0.1,0.1);
        priceProcessor = new PriceProcessorImpl(adjustment, priceCache);
    }

    @Test
    public void shouldAdjustPriceBasedOnConfiguredAdjustments() {
        Price eurUsd = TestUtil.createPrice(Instrument.EURUSD, 1.1011, 1.1019);
        priceProcessor.process(eurUsd);

        verify(priceCache).addPrice(priceCaptor.capture());
        Price adjustedPrice = priceCaptor.getValue();

        assertThat(adjustedPrice.getAsk()).isEqualTo(1.1030);
        assertThat(adjustedPrice.getBid()).isEqualTo(1.0999);
    }

    @Test
    public void shouldAdjustPriceAndSetCorrectPrecision() {
        Price eurJpy = TestUtil.createPrice(Instrument.EURJPY, 1.10, 1.12);
        priceProcessor.process(eurJpy);

        verify(priceCache).addPrice(priceCaptor.capture());
        Price adjustedPrice = priceCaptor.getValue();

        assertThat(adjustedPrice.getAsk()).isEqualTo(1.12);
        assertThat(adjustedPrice.getBid()).isEqualTo(1.09);
    }

}