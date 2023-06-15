package org.santander.fx.subscriber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.santander.fx.PriceProcessor;
import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CSVFeedSubscriberTest {

    @Mock
    private PriceProcessor priceProcessor;
    @Captor
    private ArgumentCaptor<Price> priceCaptor;

    private final DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");

    @Test
    public void shouldParseCsvMessageCorrectly() {
        FeedSubscriber<String> csvFeedSubscriber = new CSVFeedSubscriber(priceProcessor);

        String csv = "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                "107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002";

        csvFeedSubscriber.onMessage(csv);
        verify(priceProcessor, times(2)).process(priceCaptor.capture());

        List<Price> pricesProcessed = priceCaptor.getAllValues();
        Price expected_1 = new Price(106L, Instrument.EURUSD, 1.1000, 1.2000,
                LocalDateTime.parse("01-06-2020 12:01:01:001", timeStampFormatter));
        Price expected_2 = new Price(107L, Instrument.EURJPY, 119.60, 119.90,
                LocalDateTime.parse("01-06-2020 12:01:02:002", timeStampFormatter));

        Price actual_1 = pricesProcessed.get(0);
        Price actual_2 = pricesProcessed.get(1);

        assertAllFieldsAreEqual(expected_1, actual_1);
        assertAllFieldsAreEqual(expected_2, actual_2);
    }

    private void assertAllFieldsAreEqual(Price expected, Price actual) {
        assertThat(expected.getUniqueId()).isEqualTo(actual.getUniqueId());
        assertThat(expected.getBid()).isEqualTo(actual.getBid());
        assertThat(expected.getAsk()).isEqualTo(actual.getAsk());
        assertThat(expected.getTimestamp()).isEqualTo(actual.getTimestamp());
    }
}