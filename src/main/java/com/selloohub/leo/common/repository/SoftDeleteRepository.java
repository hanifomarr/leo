package com.selloohub.leo.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends MongoRepository<T, ID> {

    @Query("{'deleted': false}")
    Page<T> findAllActive(Pageable pageable);

    @Query("{'_id': ?0, 'deleted': false}")
    Optional<T> findActiveById(ID id);
}
