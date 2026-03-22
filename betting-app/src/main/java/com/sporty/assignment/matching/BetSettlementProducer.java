package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.BetSettlementMessage;

public interface BetSettlementProducer {

    void send(BetSettlementMessage message);
}
