package com.selloohub.leo.agent.repository;

import com.selloohub.leo.agent.model.Agent;
import com.selloohub.leo.common.repository.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends SoftDeleteRepository<Agent, String> {

    Optional<Agent> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    boolean existsByReferralCode(String referralCode);
}
