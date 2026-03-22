package com.sporty.assignment.matching;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BetRepository extends CrudRepository<Bet, Long> {

    List<Bet> findByEventId(String eventId);
}
