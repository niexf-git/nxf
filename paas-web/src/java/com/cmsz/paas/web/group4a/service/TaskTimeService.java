package com.cmsz.paas.web.group4a.service;

import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.exception.ApplicationException;
import com.cmsz.paas.web.group4a.entity.TaskTimeEntity;


/**
 * @author jiayz
 * @date 2019年11月6日
 */
public interface TaskTimeService {
    void insert(TaskTimeEntity entity)throws ApplicationException;
    void update(TaskTimeEntity entity)throws ApplicationException;
    void delete(TaskTimeEntity entity)throws ApplicationException;
    void deleteTimeDiff(String time)throws ApplicationException;
    List<TaskTimeEntity> findByMap(Map<String,Object> map)throws ApplicationException;
    
}
