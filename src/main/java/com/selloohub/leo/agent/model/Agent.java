package com.selloohub.leo.agent.model;

import com.selloohub.leo.common.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "agent")
public class Agent extends Auditable {

    @Id
    private String id;

    private String tierId;

    private String name;

    @Indexed(unique = true)
    private String phone;

    private String email;

    @Indexed(unique = true)
    private String username;

    private String passwordHash;

    @Indexed(unique = true)
    private String referralCode;

    private AgentStatus status;

    public Agent() {
    }

    public Agent(String tierId, String name, String phone, String email, String username, String passwordHash, String referralCode) {
        this.tierId = tierId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.referralCode = referralCode;
        this.status = AgentStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getTierId() {
        return tierId;
    }

    public void setTierId(String tierId) {
        this.tierId = tierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
    }


}
