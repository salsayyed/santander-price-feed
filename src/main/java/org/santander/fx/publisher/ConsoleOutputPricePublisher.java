package org.santander.fx.publisher;

import org.santander.fx.model.Price;

public class ConsoleOutputPricePublisher implements PricePublisher {
    @Override
    public void publish(Price price) {
        if (price == null) {
            System.out.println("No price!");
            return;
        }
        System.out.println(price);
    }
}
