package com.selloohub.leo.agent;

import com.selloohub.leo.AbstractIntegrationTest;
import com.selloohub.leo.agent.model.Agent;
import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.repository.AgentRepository;
import com.selloohub.leo.agent.repository.AgentTierRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgentCreationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentTierRepository agentTierRepository;

    private String createdTierId;
    private final List<String> createdAgentIds = new ArrayList<>();

    @AfterEach
    void cleanup() {
        createdAgentIds.forEach(agentRepository::deleteById);
        if (createdTierId != null) {
            agentTierRepository.deleteById(createdTierId);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void duplicateUsernameRejectedWithConflict() throws Exception {
        AgentTier tier = agentTierRepository.save(new AgentTier("Test Tier A", "for tests"));
        createdTierId = tier.getId();

        Agent existing = agentRepository.save(new Agent(
                tier.getId(), "Existing Agent", "+60144444444",
                "existing@example.com", "dup-user", "unused", "DUPCODE1"));
        createdAgentIds.add(existing.getId());

        mockMvc.perform(post("/api/v1/admin/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tierId": "%s",
                                  "name": "New Agent",
                                  "phone": "+60155555555",
                                  "email": "new@example.com",
                                  "username": "dup-user"
                                }
                                """.formatted(tier.getId())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.code").value("AGENT_USERNAME_EXISTS"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createdAgentResponseNeverExposesPasswordHash() throws Exception {
        AgentTier tier = agentTierRepository.save(new AgentTier("Test Tier B", "for tests"));
        createdTierId = tier.getId();

        mockMvc.perform(post("/api/v1/admin/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tierId": "%s",
                                  "name": "Password Test Agent",
                                  "phone": "+60166666666",
                                  "email": "passwordtest@example.com",
                                  "username": "password-test-agent"
                                }
                                """.formatted(tier.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.passwordHash").doesNotExist())
                .andExpect(jsonPath("$.data.initialPassword").exists());

        createdAgentIds.add(agentRepository.findByUsername("password-test-agent").orElseThrow().getId());
    }
}