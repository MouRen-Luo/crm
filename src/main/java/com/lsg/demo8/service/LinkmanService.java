package com.lsg.demo8.service;

import com.lsg.demo8.entity.Linkman;

import java.util.List;

public interface LinkmanService {
    public List<Linkman> getLinkmanList(String custNo);

    public Linkman getLinkman(Long lkmId);

    public Linkman add(Linkman linkman);

    public void del(Long lkmId);

    public void delLinkman(String no);
}
