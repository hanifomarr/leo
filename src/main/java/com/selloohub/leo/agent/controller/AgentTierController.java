package com.selloohub.leo.agent.controller;

import com.selloohub.leo.agent.dto.AgentTierResponse;
import com.selloohub.leo.agent.dto.CreateAgentTierRequest;
import com.selloohub.leo.agent.dto.CreateAgentTierResponse;
import com.selloohub.leo.agent.service.AgentTierService;
import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.common.response.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/agent-tier")
public class AgentTierController {

    private final AgentTierService agentTierService;

    public AgentTierController(AgentTierService agentTierService) {
        this.agentTierService = agentTierService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateAgentTierResponse>> createAgentTier(
            @Valid @RequestBody CreateAgentTierRequest request) {

        CreateAgentTierResponse response = agentTierService.createAgentTier(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgentTierResponse>> getAgentTierById(
            @PathVariable("id") String id
    ) {
        AgentTierResponse response = agentTierService.getAgentTierById(id);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AgentTierResponse>>> getAgentTiers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<AgentTierResponse> response = agentTierService.getAllAgentTiers(page, size);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }
}

