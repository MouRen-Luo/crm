package com.lsg.demo8.service;

import com.lsg.demo8.entity.Dict;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DictService {
    public List<Dict> getDictByType(String Type);

    public Page<Dict> list(String dictType,Pageable pageable);

    public XSSFWorkbook show();

    public XSSFWorkbook show2();

    public Page<Dict> getDictList(Long dictId,String dictItem,String dictType,Pageable pageable);

    public void del(Long dictId);

    public int count(String type);

    public Dict add(Dict dict);

    public Dict getById(Long dictId);
}
