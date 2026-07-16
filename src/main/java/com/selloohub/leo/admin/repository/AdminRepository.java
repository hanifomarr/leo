package com.selloohub.leo.admin.repository;

import com.selloohub.leo.admin.model.Admin;
import com.selloohub.leo.common.repository.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends SoftDeleteRepository<Admin, String> {

    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}
