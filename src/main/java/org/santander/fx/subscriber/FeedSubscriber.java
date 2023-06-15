package org.santander.fx.subscriber;

public interface FeedSubscriber<I> {

    void onMessage(I message);
}
