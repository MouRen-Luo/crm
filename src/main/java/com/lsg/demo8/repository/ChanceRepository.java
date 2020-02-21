package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Chance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChanceRepository extends JpaRepository<Chance, Long>, JpaSpecificationExecutor<Chance> {
}
