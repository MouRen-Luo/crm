package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Dict;
import com.lsg.demo8.repository.DictRespository;
import com.lsg.demo8.service.DictService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.print.Doc;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class DictServiceImpl implements DictService {
    @Resource
    private DictRespository dictRespository;

    @Override
    public List<Dict> getDictByType(String Type) {
        return dictRespository.findAllByDictType(Type);
    }

    @Override
    public Page<Dict> list(String dictType,Pageable pageable) {
        Specification<Dict> specification = new Specification<Dict>() {
            @Override
            public Predicate toPredicate(Root<Dict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (dictType!=null && !dictType.equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("dictType"),dictType));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return dictRespository.findAll(specification,pageable);
    }

    @Override
    public XSSFWorkbook show() {
        List<Dict> di = dictRespository.kl();
        XSSFWorkbook xw = new XSSFWorkbook();
        Sheet se = xw.createSheet("Goods");
        Row titleRow = se.createRow(0);
        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("客户等级");
        titleRow.createCell(2).setCellValue("数量");
        int cell = 1;
        for (Dict li:
             di) {
            Row rw = se.createRow(cell);
            rw.createCell(0).setCellValue(li.getDictId());
            rw.createCell(1).setCellValue(li.getDictItem());
            if (li.getDictIsEditable() ==null){
                rw.createCell(2).setCellValue(0);
            }else {
                rw.createCell(2).setCellValue(li.getDictIsEditable());
            }
            cell++;
        }
        return xw;
    }

    @Override
    public XSSFWorkbook show2() {
        List<Dict> di = dictRespository.ol();
        XSSFWorkbook xw = new XSSFWorkbook();
        Sheet se = xw.createSheet("Goods");
        Row titleRow = se.createRow(0);
        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("服务类型");
        titleRow.createCell(2).setCellValue("数量");
        int cell = 1;
        for (Dict li:
                di) {
            Row rw = se.createRow(cell);
            rw.createCell(0).setCellValue(li.getDictId());
            rw.createCell(1).setCellValue(li.getDictItem());
            if (li.getDictIsEditable() ==null){
                rw.createCell(2).setCellValue(0);
            }else {
                rw.createCell(2).setCellValue(li.getDictIsEditable());
            }
            cell++;
        }
        return xw;
    }

    @Override
    public Page<Dict> getDictList(Long dictId, String dictItem, String dictType, Pageable pageable) {
        Specification<Dict> specification = new Specification<Dict>() {
            @Override
            public Predicate toPredicate(Root<Dict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(dictId!=null){
                    predicates.add(criteriaBuilder.equal(root.get("dictId"),dictId));
                }
                if (dictItem!=null && !dictItem.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("dictItem"),dictItem));
                }

                if (dictType!=null && !dictType.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("dictType"),dictType));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return dictRespository.findAll(specification,pageable);
    }

    @Override
    public void del(Long dictId) {
        dictRespository.deleteById(dictId);
    }

    @Override
    public int count(String type) {
        return dictRespository.countAllByDictType(type);
    }

    @Override
    public Dict add(Dict dict) {
        return dictRespository.save(dict);
    }

    @Override
    public Dict getById(Long dictId) {
        return dictRespository.getOne(dictId);
    }


}
