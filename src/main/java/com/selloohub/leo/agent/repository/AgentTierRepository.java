package com.selloohub.leo.agent.repository;

import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.model.TierStatus;
import com.selloohub.leo.common.repository.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentTierRepository extends SoftDeleteRepository<AgentTier, String, TierStatus> {

    boolean existsByName(String name);
}
