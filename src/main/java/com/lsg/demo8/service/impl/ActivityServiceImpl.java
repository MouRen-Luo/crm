package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Activity;
import com.lsg.demo8.repository.ActivityRepository;
import com.lsg.demo8.service.ActivityService;
import org.hibernate.id.enhanced.AccessCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityRepository activityRepository;
    @Override
    public List<Activity> getActivityList(String custNo) {
        return activityRepository.findAllByAtvCustNo(custNo);
    }

    @Override
    public Activity getActivityById(Long atvId) {
        return activityRepository.getOne(atvId);
    }

    @Override
    public Activity add(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void del(Long atvId) {
        activityRepository.deleteById(atvId);
    }

    @Override
    public void delActivity(String no) {
        activityRepository.deleteAllByAtvCustNo(no);
    }
}
