package com.selloohub.leo.agent.controller;

import com.selloohub.leo.agent.dto.*;
import com.selloohub.leo.agent.service.AgentService;
import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.common.response.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateAgentResponse>> createAgent(
            @Valid @RequestBody CreateAgentRequest request) {

        CreateAgentResponse response = agentService.createAgent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AgentResponse>>> listAgents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<AgentResponse> response = agentService.listAgents(page, size);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgentResponse>> getAgentById(
            @PathVariable("id") String id
    ) {
        AgentResponse response = agentService.getAgentById(id);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<AgentResponse>> updateAgent(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateAgentRequest request
    ) {
        AgentResponse response = agentService.updateAgent(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AgentResponse>> updateAgentStatus(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateAgentStatusRequest request
    ) {
        AgentResponse response = agentService.updateAgentStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<ApiResponse<ResetPasswordResponse>> resetPassword(
            @PathVariable("id") String id
    ) {
        ResetPasswordResponse response = agentService.resetPassword(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/referral-code")
    public ResponseEntity<ApiResponse<RegenerateReferralCodeResponse>> regenerateReferralCode(
            @PathVariable("id") String id
    ) {
        RegenerateReferralCodeResponse response = agentService.regenerateReferralCode(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
