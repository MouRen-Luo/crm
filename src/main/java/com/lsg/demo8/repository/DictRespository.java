package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Customer;
import com.lsg.demo8.entity.Dict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.beans.Transient;
import java.util.List;

public interface DictRespository extends JpaRepository<Dict,Long>, JpaSpecificationExecutor<Dict> {
    public List<Dict> findAllByDictType(String type);

    @Transient
    @Query(value = "select bs.dict_item,bs.dict_id,bs.dict_type,bs.dict_value,(select count(*) from cst_customer as cu where cu.cust_level_label=bs.dict_item group by cust_level_label) as dict_is_editable from bas_dict as bs where dict_type='客户等级'  group by dict_item",nativeQuery = true)
    public List<Dict> kl();

    @Transient
    @Query(value = "select bs.dict_item,bs.dict_id,bs.dict_type,bs.dict_value,(select count(*) from cst_service as cu where cu.svr_type=bs.dict_item group by cu.svr_type) as dict_is_editable from bas_dict as bs where dict_type='服务类型'  group by dict_item",nativeQuery = true)
    public List<Dict> ol();

    public int countAllByDictType(String type);
}
