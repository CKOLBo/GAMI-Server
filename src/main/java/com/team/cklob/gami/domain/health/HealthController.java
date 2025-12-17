package com.team.cklob.gami.domain.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Health", description = "서버 상태 확인 API")
public class HealthController {

    @Operation(
            summary = "헬스 체크",
            description = "서버가 정상 동작 중인지 확인한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서버 정상")
    })
    @GetMapping("/api/health")
    public Map<String, String> healthCheck() {
        return Map.of("status", "UP");
    }
}
