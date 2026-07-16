package com.selloohub.leo.agent.model;

import com.selloohub.leo.common.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "agent_tier")
public class AgentTier extends Auditable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private TierStatus status;

    public AgentTier() {
    }

    public AgentTier(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TierStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TierStatus getStatus() {
        return status;
    }

    public void setStatus(TierStatus status) {
        this.status = status;
    }
}
