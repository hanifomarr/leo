package com.selloohub.leo.common.ping;

import com.selloohub.leo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecuritySmokeTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpointIsPubliclyAccessible() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void anonymousRejectedFromAdminZone() throws Exception {
        mockMvc.perform(get("/api/v1/admin/ping"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("AUTH_FAILED"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminRoleAllowedIntoAdminZone() throws Exception {
        mockMvc.perform(get("/api/v1/admin/ping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Pong"));
    }

    @Test
    @WithMockUser(roles = "AGENT")
    void agentRoleForbiddenFromAdminZone() throws Exception {
        mockMvc.perform(get("/api/v1/admin/ping"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("FORBIDDEN"));
    }

    @Test
    void anonymousRejectedFromAgentZone() throws Exception {
        mockMvc.perform(get("/api/v1/agent/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("AUTH_FAILED"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminRoleForbiddenFromAgentZone() throws Exception {
        mockMvc.perform(get("/api/v1/agent/me"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("FORBIDDEN"));
    }
}