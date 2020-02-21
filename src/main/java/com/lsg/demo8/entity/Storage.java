package com.lsg.demo8.entity;

import javax.persistence.*;

@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "stk_id")
    private Long stkId;
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "stk_prod_id")
    private Product product;
    @Column(name="stk_warehouse")
    private String stkWarehouse;
    @Column(name="stk_ware")
    private String stkWare;
    @Column(name="stk_count")
    private Integer stkCount;

    public Long getStkId() {
        return stkId;
    }

    public void setStkId(Long stkId) {
        this.stkId = stkId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStkWarehouse() {
        return stkWarehouse;
    }

    public void setStkWarehouse(String stkWarehouse) {
        this.stkWarehouse = stkWarehouse;
    }

    public String getStkWare() {
        return stkWare;
    }

    public void setStkWare(String stkWare) {
        this.stkWare = stkWare;
    }

    public Integer getStkCount() {
        return stkCount;
    }

    public void setStkCount(Integer stkCount) {
        this.stkCount = stkCount;
    }

    public String getStkMemo() {
        return stkMemo;
    }

    public void setStkMemo(String stkMemo) {
        this.stkMemo = stkMemo;
    }

    @Column(name="stk_memo")
    private String stkMemo;
}
