package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Product;
import com.lsg.demo8.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StorageRepository extends JpaRepository<Storage, Long>, JpaSpecificationExecutor<Storage> {
}
