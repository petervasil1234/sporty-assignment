package com.sporty.assignment.matching;

import com.sporty.assignment.api.http.ApiBet;
import com.sporty.assignment.api.http.ApiPaths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;

    @GetMapping(ApiPaths.BETS)
    public List<ApiBet> getBets(@RequestParam(required = false) String eventId) {
        if (eventId != null) {
            return betService.findByEventId(eventId).stream()
                    .map(BetMapper::toApi)
                    .toList();
        }
        return betService.findAll().stream().map(BetMapper::toApi).toList();
    }
}
