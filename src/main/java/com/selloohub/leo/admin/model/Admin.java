package com.selloohub.leo.admin.model;

import com.selloohub.leo.common.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")
public class Admin extends Auditable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String passwordHash;

    private String displayName;

    private AdminRole role;

    private AdminStatus status;

    public Admin() {
    }

    public Admin(String username, String passwordHash, String displayName, AdminRole role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.role = role;
        this.status = AdminStatus.ACTIVE;
    }

    public String getId() {
        return id;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public AdminStatus getStatus() {
        return status;
    }

    public void setAdminStatus(AdminStatus status) {
        this.status = status;
    }
}
