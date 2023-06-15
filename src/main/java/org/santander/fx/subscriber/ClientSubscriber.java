package org.santander.fx.subscriber;

import org.santander.fx.model.Instrument;
import org.santander.fx.model.Price;

public interface ClientSubscriber {
    void requestLatest(Instrument instrument);
}
