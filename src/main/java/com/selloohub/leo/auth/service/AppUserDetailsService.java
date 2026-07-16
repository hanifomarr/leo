package com.selloohub.leo.auth.service;

import com.selloohub.leo.admin.model.AdminStatus;
import com.selloohub.leo.admin.repository.AdminRepository;
import com.selloohub.leo.agent.model.AgentStatus;
import com.selloohub.leo.agent.repository.AgentRepository;
import com.selloohub.leo.auth.security.AppUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final AgentRepository agentRepository;

    public AppUserDetailsService(AdminRepository adminRepository, AgentRepository agentRepository) {
        this.adminRepository = adminRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username)
                .map(admin -> new AppUserDetails(
                        admin.getId(), admin.getUsername(), admin.getDisplayName(), admin.getPasswordHash(), "ADMIN", null, admin.getStatus() == AdminStatus.ACTIVE))
                .or(() -> agentRepository.findByUsername(username)
                        .map(agent -> new AppUserDetails(agent.getId(), agent.getUsername(), agent.getName(), agent.getPasswordHash(),
                                "AGENT", agent.getTierId(), agent.getStatus() == AgentStatus.ACTIVE)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
