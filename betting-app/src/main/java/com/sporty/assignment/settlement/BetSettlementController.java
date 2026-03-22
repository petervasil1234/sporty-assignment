package com.sporty.assignment.settlement;

import com.sporty.assignment.api.http.ApiBetSettlement;
import com.sporty.assignment.api.http.ApiPaths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BetSettlementController {

    private final BetSettlementService settlementService;

    @GetMapping(ApiPaths.SETTLEMENTS)
    public List<ApiBetSettlement> getSettlements() {
        return settlementService.findAll().stream()
                .map(BetSettlementMapper::toApi)
                .toList();
    }
}
