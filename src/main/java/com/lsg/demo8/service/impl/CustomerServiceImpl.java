package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Customer;
import com.lsg.demo8.repository.CustomerRepository;
import com.lsg.demo8.repository.LostRepository;
import com.lsg.demo8.repository.ServiceRepository;
import com.lsg.demo8.service.CustomerService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private LostRepository lostRepository;
    @Resource
    private ServiceRepository serviceRepository;
    @Override
    public Page<Customer> getUserList(String custName, String custNo, String custManagerName, String dictItem, String dictItem2, Pageable pageable) {
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (custName!=null && !custName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custName"),"%"+custName+"%"));
                }
                if (custNo!=null && !custNo.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custNo"),"%"+custNo+"%"));
                }
                if (custManagerName!=null && !custManagerName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custManagerName"),"%"+custManagerName+"%"));
                }
                if (dictItem!=null && !dictItem.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custLevelLabel"),"%"+dictItem+"%"));
                }
                if (dictItem2!=null && !dictItem2.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custRegion"),"%"+dictItem2+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return customerRepository.findAll(specification,pageable);
    }

    @Override
    public Customer getone(Long custId) {
        return customerRepository.getOne(custId);
    }

    @Override
    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getByno(String no) {
        return customerRepository.findAllByCustNo(no);
    }

    @Override
    public void del(String no) {
        customerRepository.deleteAllByCustNo(no);
    }

    @Override
    public void delLost(String no) {
        lostRepository.deleteAllByLstCustNo(no);
    }

    @Override
    public void delService(String no) {
            serviceRepository.deleteAllBySvrCustNo(no);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     *Excel连接数据库
     * @return
     */
    @Override
    public XSSFWorkbook show() {
        //查询数据的方法调用   重点
        List<Customer> list = customerRepository.findAll();//查出数据库数据
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Goods");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("客户编号");//第一列
        titleRow.createCell(1).setCellValue("客户名称"); //title标题
        titleRow.createCell(2).setCellValue("客户等级");
        titleRow.createCell(3).setCellValue("客户地址");
        int cell = 1;
        for (Customer cst : list) {
            //注意控制行
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(cst.getCustNo());
            row.createCell(1).setCellValue(cst.getCustName());//将数据库的数据遍历出来
            row.createCell(2).setCellValue(cst.getCustLevelLabel());
            row.createCell(3).setCellValue(cst.getCustAddr());
            cell++;
        }
        return wb;
    }
}
