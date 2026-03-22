package com.sporty.assignment.matching;

import com.sporty.assignment.api.http.ApiBet;
import com.sporty.assignment.api.http.ApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Bets", description = "View pre-populated bets")
public class BetController {

    private final BetService betService;

    @GetMapping(ApiPaths.BETS)
    @Operation(summary = "List bets", description = "Returns all bets, optionally filtered by event ID")
    public List<ApiBet> getBets(
            @Parameter(description = "Filter by event ID") @RequestParam(required = false) String eventId) {
        if (eventId != null) {
            return betService.findByEventId(eventId).stream()
                    .map(BetMapper::toApi)
                    .toList();
        }
        return betService.findAll().stream().map(BetMapper::toApi).toList();
    }
}
