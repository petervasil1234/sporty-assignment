package com.sporty.assignment.settlement;

import com.sporty.assignment.api.http.ApiBetSettlement;
import com.sporty.assignment.api.http.ApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Settlements", description = "View bet settlement results")
public class BetSettlementController {

    private final BetSettlementService settlementService;

    @GetMapping(ApiPaths.SETTLEMENTS)
    @Operation(summary = "List settlements", description = "Returns all settled bets with their results (WON/LOST)")
    public List<ApiBetSettlement> getSettlements() {
        return settlementService.findAll().stream()
                .map(BetSettlementMapper::toApi)
                .toList();
    }
}
