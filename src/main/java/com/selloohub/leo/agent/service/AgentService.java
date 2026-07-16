package com.selloohub.leo.agent.service;

import com.selloohub.leo.agent.dto.*;
import com.selloohub.leo.agent.model.Agent;
import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.model.TierStatus;
import com.selloohub.leo.agent.repository.AgentRepository;
import com.selloohub.leo.agent.repository.AgentTierRepository;
import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.common.response.PageResponse;
import com.selloohub.leo.common.util.PasswordGenerator;
import com.selloohub.leo.common.util.ReferralCodeGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final AgentTierRepository agentTierRepository;
    private final PasswordEncoder passwordEncoder;

    public AgentService(AgentRepository agentRepository, AgentTierRepository agentTierRepository, PasswordEncoder passwordEncoder) {
        this.agentRepository = agentRepository;
        this.agentTierRepository = agentTierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateAgentResponse createAgent(CreateAgentRequest request) {
        AgentTier tier = agentTierRepository.findById(request.tierId())
                .filter(t -> t.getStatus() == TierStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found or inactive"));

        if (agentRepository.existsByPhone(request.phone()))
            throw new ConflictException("AGENT_PHONE_EXISTS", "Phone already exists");
        if (agentRepository.existsByUsername(request.username())) {
            throw new ConflictException("AGENT_USERNAME_EXISTS", "Username already exists");
        }

        String initialPassword = PasswordGenerator.generate(12);
        String referralCode = ReferralCodeGenerator.generate(6);
        String passwordHash = passwordEncoder.encode(initialPassword);

        Agent agent = new Agent(request.tierId(), request.name(), request.phone(), request.email(), request.username(), passwordHash, referralCode);
        agentRepository.save(agent);

        return CreateAgentResponse.from(agent, initialPassword);
    }

    public PageResponse<AgentResponse> listAgents(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AgentResponse> agentPage = agentRepository.findAllActive(pageable)
                .map(AgentResponse::from);

        return PageResponse.from(agentPage);
    }

    public AgentResponse getAgentById(String id) {
        Agent agent = agentRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found or inactive"));
        return AgentResponse.from(agent);
    }

    public AgentResponse updateAgent(String id, UpdateAgentRequest request) {

        Agent agent = agentRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found or inactive"));

        agentTierRepository.findById(request.tierId())
                .filter(t -> t.getStatus() == TierStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found or inactive"));

        agent.setTierId(request.tierId());
        agent.setName(request.name());
        agent.setEmail(request.email());

        agentRepository.save(agent);
        return AgentResponse.from(agent);
    }

    public AgentResponse updateAgentStatus(String id, UpdateAgentStatusRequest request) {

        Agent agent = agentRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found or inactive"));

        agent.setStatus(request.status());
        agentRepository.save(agent);
        return AgentResponse.from(agent);

    }

    public ResetPasswordResponse resetPassword(String id) {

        Agent agent = agentRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found or inactive"));

        String newPassword = PasswordGenerator.generate(12);
        agent.setPasswordHash(passwordEncoder.encode(newPassword));
        agentRepository.save(agent);

        return new ResetPasswordResponse(agent.getId(), agent.getUsername(), newPassword);
    }

    public RegenerateReferralCodeResponse regenerateReferralCode(String id) {

        Agent agent = agentRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found or inactive"));

        String newReferralCode = ReferralCodeGenerator.generate(6);
        agent.setReferralCode(newReferralCode);
        agentRepository.save(agent);

        return new RegenerateReferralCodeResponse(agent.getId(), agent.getUsername(), newReferralCode);
    }
}
