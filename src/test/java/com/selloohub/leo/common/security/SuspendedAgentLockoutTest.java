package com.selloohub.leo.common.security;

import com.selloohub.leo.AbstractIntegrationTest;
import com.selloohub.leo.agent.model.Agent;
import com.selloohub.leo.agent.model.AgentStatus;
import com.selloohub.leo.agent.repository.AgentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SuspendedAgentLockoutTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private JwtService jwtService;

    private String createdAgentId;

    @AfterEach
    void cleanup() {
        if (createdAgentId != null) {
            agentRepository.deleteById(createdAgentId);
        }
    }

    @Test
    void activeAgentTokenGrantsAccessToAgentZone() throws Exception {
        Agent agent = agentRepository.save(new Agent(
                null, "Test Agent", "+60111111111", "test@example.com",
                "lockout-test-active", "unused", "LOCKOUT1"));
        createdAgentId = agent.getId();

        String token = jwtService.generateToken(agent.getUsername(), "AGENT");

        mockMvc.perform(get("/api/v1/agent/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(agent.getId()));
    }

    @Test
    void suspendedAgentTokenRejectedOnNextRequest() throws Exception {
        Agent agent = agentRepository.save(new Agent(
                null, "Test Agent", "+60122222222", "test2@example.com",
                "lockout-test-suspended", "unused", "LOCKOUT2"));
        createdAgentId = agent.getId();

        String token = jwtService.generateToken(agent.getUsername(), "AGENT");

        agent.setStatus(AgentStatus.SUSPENDED);
        agentRepository.save(agent);

        mockMvc.perform(get("/api/v1/agent/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("AGENT_SUSPENDED"));
    }
}