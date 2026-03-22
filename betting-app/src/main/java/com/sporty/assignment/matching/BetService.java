package com.sporty.assignment.matching;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;

    public List<Bet> findAll() {
        return (List<Bet>) betRepository.findAll();
    }

    public List<Bet> findByEventId(String eventId) {
        return betRepository.findByEventId(eventId);
    }
}
