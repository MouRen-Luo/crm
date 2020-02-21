package com.lsg.demo8.service;

import com.lsg.demo8.entity.Activity;

import java.util.List;

public interface ActivityService {
    public List<Activity> getActivityList(String custNo);

    public Activity getActivityById(Long atvId);

    public Activity add(Activity activity);

    public void del(Long atvId);

    public void delActivity(String no);
}
