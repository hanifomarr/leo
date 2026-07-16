package com.selloohub.leo.agent;

import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.repository.AgentTierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.agent-tier.bootstrap.enabled", havingValue = "true")
public class AgentTierBootstrapRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AgentTierBootstrapRunner.class);
    private final AgentTierRepository agentTierRepository;
    private final String name;
    private final String description;

    public AgentTierBootstrapRunner(
            AgentTierRepository agentTierRepository,
            @Value("${app.agent-tier.bootstrap.name}") String name,
            @Value("${app.agent-tier.bootstrap.description}") String description) {
        this.agentTierRepository = agentTierRepository;
        this.name = name;
        this.description = description;
    }

    @Override
    public void run(String... args) throws Exception {

        if (agentTierRepository.existsByName(name)) {
            log.info("Agent tier with name {} already exists", name);
            return;
        }

        AgentTier agentTier = new AgentTier(name, description);
        agentTierRepository.save(agentTier);
        log.info("Agent tier with name {} created with id {}", agentTier.getName(), agentTier.getId());
    }
}
