package com.lasertagnation.yachtmodule.repository;

import com.lasertagnation.yachtmodule.entity.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YachtRepository extends JpaRepository<Yacht, Long> {
}
