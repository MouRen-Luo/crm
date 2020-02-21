package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.Linkman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LinkmanRepository extends JpaRepository<Linkman, Long>, JpaSpecificationExecutor<Linkman> {

    public List<Linkman> findAllByLkmCustNo(String custNo);

    public void deleteAllByLkmCustNo(String no);
}
