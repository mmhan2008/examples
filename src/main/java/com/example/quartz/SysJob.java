package com.example.quartz;

import lombok.Data;

/**
 * @author user01
 * @create 2019/12/24
 */
@Data
public class SysJob {
    private Integer id;
    private String jobClasspath;
    private String jobName;
    private String groupName;
    private String cronExpression;
    private String dataMap;
    private Integer status;     //0.NONE, 1.NORMAL, 2.PAUSED, 3.COMPLETE, 4.ERROR, 5.BLOCKED
    private String description;
}

