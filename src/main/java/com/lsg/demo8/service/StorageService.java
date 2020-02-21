package com.lsg.demo8.service;

import com.lsg.demo8.entity.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageService {
    public Page<Storage> getStorageList(String prodName, String sktWarehouse, Pageable pageable);
}
