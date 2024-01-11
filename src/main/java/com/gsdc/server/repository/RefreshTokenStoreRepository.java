package com.gsdc.server.repository;

import com.gsdc.server.entity.RefreshTokenStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenStoreRepository extends CrudRepository<RefreshTokenStore, Integer> {

    Optional<RefreshTokenStore> findFirstByLoginOrderByIdDesc(String login);

    void deleteAllByLogin(String login);
}
