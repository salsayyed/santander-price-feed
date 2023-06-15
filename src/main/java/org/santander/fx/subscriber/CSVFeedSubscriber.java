package org.santander.fx.subscriber;

import org.santander.fx.PriceProcessor;
import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CSVFeedSubscriber implements FeedSubscriber<String> {

    private final Map<String, Instrument> instrumentBySlashedName = new HashMap<>();
    private final DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
    private final PriceProcessor priceProcessor;

    public CSVFeedSubscriber(PriceProcessor priceProcessor) {
        this.priceProcessor = priceProcessor;
        for (Instrument instrument : Instrument.values()) {
            instrumentBySlashedName.put(instrument.getSlashedName(),instrument);
        }
    }
    @Override
    public void onMessage(String message) {
        String[] lines = message.split("\n");
        for (String line : lines) {
            Price price = parseLine(line);
            priceProcessor.process(price);
        }
    }

    private Price parseLine(String line) {
        String[] lineComponents = line.split(",");
        return new Price(Long.parseLong(lineComponents[0]),
                instrumentBySlashedName.get(lineComponents[1]),
                Double.parseDouble(lineComponents[2]),
                Double.parseDouble(lineComponents[3]),
                LocalDateTime.parse(lineComponents[4], timeStampFormatter));
    }
}
