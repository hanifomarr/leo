package com.selloohub.leo.agent.service;

import com.selloohub.leo.agent.dto.AgentTierResponse;
import com.selloohub.leo.agent.dto.CreateAgentTierRequest;
import com.selloohub.leo.agent.dto.CreateAgentTierResponse;
import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.repository.AgentTierRepository;
import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.common.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AgentTierService {

    private final AgentTierRepository agentTierRepository;

    public AgentTierService(AgentTierRepository agentTierRepository) {
        this.agentTierRepository = agentTierRepository;
    }

    public CreateAgentTierResponse createAgentTier(CreateAgentTierRequest request) {

        if (agentTierRepository.existsByName(request.name()))
            throw new ConflictException("AGENT_TIER_NAME_EXISTS", "Agent Tier name already exists");

        AgentTier agentTier = new AgentTier(request.name(), request.description());
        agentTierRepository.save(agentTier);
        return CreateAgentTierResponse.from(agentTier);
    }

    public AgentTierResponse getAgentTierById(String id) {
        AgentTier agentTier = agentTierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent Tier not found with id: " + id));

        return AgentTierResponse.from(agentTier);
    }

    public PageResponse<AgentTierResponse> getAllAgentTiers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AgentTierResponse> agentTierPage = agentTierRepository.findAllActive(pageable)
                .map(AgentTierResponse::from);

        return PageResponse.from(agentTierPage);
    }

}
