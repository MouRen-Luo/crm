package com.lsg.demo8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bas_dict")
public class Dict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id")
    private Long dictId;
    @Column(name = "dict_type")
    private String dictType;
    @Column(name = "dict_item")
    private String dictItem;
    @Column(name = "dict_value")
    private String dictValue;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictItem() {
        return dictItem;
    }

    public void setDictItem(String dictItem) {
        this.dictItem = dictItem;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public Integer getDictIsEditable() {
        return dictIsEditable;
    }

    public void setDictIsEditable(Integer dictIsEditable) {
        this.dictIsEditable = dictIsEditable;
    }

    @Column(name = "dict_is_editable")
    private Integer dictIsEditable;
}
