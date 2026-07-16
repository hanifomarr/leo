package com.selloohub.leo.agent.controller;

import com.selloohub.leo.agent.dto.AgentResponse;
import com.selloohub.leo.agent.service.AgentService;
import com.selloohub.leo.auth.security.AppUserDetails;
import com.selloohub.leo.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentSelfController {

    private final AgentService agentService;

    public AgentSelfController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AgentResponse>> getAgent(
            @AuthenticationPrincipal AppUserDetails principal
    ) {

        AgentResponse response = agentService.getAgentById(principal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
