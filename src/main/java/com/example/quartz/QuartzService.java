package com.example.quartz;

import com.alibaba.fastjson.JSON;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author user01
 * @create 2019/12/24
 */
@Service
public class QuartzService {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void initializeScheduler(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a task
     * @param jobClassPath
     * @param jobName
     * @param groupName
     * @param cronExpression
     * @param jsonMap
     * @throws Exception
     */
    public boolean addJob(String jobClassPath, String jobName, String groupName, String cronExpression, String jsonMap) {
        try {
            if (!ifExist(jobName, groupName)) {

                JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassPath).getClass()).
                    withIdentity(jobName,groupName).build();
                if (!StringUtils.isEmpty(jsonMap)) {
                    Map dataMap = (Map) JSON.parse(jsonMap);
                    jobDetail.getJobDataMap().putAll(dataMap);
                }
                //构建表达式
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName,groupName)
                        .withSchedule(scheduleBuilder).startNow().build();
                scheduler.scheduleJob(jobDetail,trigger);
                System.out.println(">>>>>>>>> addJob success, jobGroup: " + groupName + " jobName: " + jobName);
                return true;
            }
            System.out.println(">>>>>>>>> addJob fail, job already exist, jobGroup: " + groupName + " jobName: " + jobName);
        } catch (Exception e) {
            System.out.println("job creation failed ,An error occurred");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Modify the expression of a task
     * @param sysJob
     * @return boolean
     */
    public boolean updateJob(SysJob sysJob){
        try {
            if (ifExist(sysJob.getJobName(), sysJob.getGroupName())) {
                TriggerKey triggerKey = TriggerKey.triggerKey(sysJob.getJobName(), sysJob.getGroupName());
                // Map map = (Map) JSON.parse(sysJob.getDataMap());
                // JobDataMap jobDataMap = new JobDataMap();
                // jobDataMap.putAll(map);
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                            .withSchedule(CronScheduleBuilder.cronSchedule(sysJob.getCronExpression()))
                            // .usingJobData(jobDataMap)
                            .build();
                scheduler.rescheduleJob(triggerKey, trigger);
                if (sysJob.getStatus() == 0) {
                    scheduler.pauseJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getGroupName()));
                }
                System.out.println(">>>>>>>>> update success, jobGroup: " + sysJob.getGroupName() + " jobName: " + sysJob.getJobName());
                return true;
            }
            System.out.println(">>>>>>>>> update failed, job does not exist ,jobGroup: " + sysJob.getGroupName() + " jobName: " + sysJob.getJobName());
        } catch (Exception e) {
            System.out.println("job update failed ,An error occurred");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Delete a task
     * @param jobName
     * @param groupName
     */
    public boolean delJob(String jobName,String groupName){
        try {
            if (ifExist(jobName, groupName)) {
                scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, groupName));
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, groupName));
                scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
                System.out.println(">>>>>>>>> delete success, jobGroup: " + groupName + " jobName: " + jobName);
                return true;
            }
            System.out.println(">>>>>>>>> delete failed, job does not exist ,jobGroup: " + groupName + " jobName: " + jobName);
        } catch (SchedulerException e) {
            System.out.println("job delete failed ,An error occurred");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Check if the task exists
     * @param jobName
     * @param groupName
     * @return
     */
    public boolean ifExist(String jobName,String groupName) {
        boolean exists = false;
        try {
            exists = scheduler.checkExists(TriggerKey.triggerKey(jobName, groupName));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return exists;
        }
    }

    /**
     *  Pause a task
     * @param jobName
     * @param groupName
     */
    public boolean pauseJob(String jobName,String groupName){
        try {
            if (ifExist(jobName, groupName)) {
                scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
                System.out.println(">>>>>>>>> pause success ,jobGroup: " + groupName + " jobName: " + jobName);
                return true;
            }
            System.out.println(">>>>>>>>> pause failed, job does not exist ,jobGroup: " + groupName + " jobName: " + jobName);
        } catch (SchedulerException e) {
            System.out.println("job pause failed ,An error occurred");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Resume a task
     * @param jobName
     * @param groupName
     */
    public boolean resumeJob(String jobName,String groupName){
        try {
            if (ifExist(jobName, groupName)) {
                scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
                System.out.println(">>>>>>>>> resume success, jobGroup: " + groupName + " jobName: " + jobName);
                return true;
            }
            System.out.println(">>>>>>>>> resume failed, job does not exist ,jobGroup: " + groupName + " jobName: " + jobName);
        } catch (SchedulerException e) {
            System.out.println("job resume failed ,An error occurred");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * run a task immediately
     * @param jobName
     * @param groupName
     */
    public boolean runJobNow(String jobName,String groupName){
        try {
            if (ifExist(jobName, groupName)) {
                scheduler.triggerJob(JobKey.jobKey(jobName, groupName));
                System.out.println(">>>>>>>>> run success, jobGroup: " + groupName + " jobName: " + jobName);
                return true;
            }
            System.out.println(">>>>>>>>> run failed, job does not exist ,jobGroup: " + groupName + " jobName: " + jobName);

        } catch (SchedulerException e) {
            System.out.println("job run failed ,An error occurred");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Get all tasks
     * @return
     */
    public List<SysJob> getAllJob(){
        List<SysJob> list = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    SysJob sysJob = new SysJob();
                    sysJob.setJobName(jobKey.getName());
                    sysJob.setGroupName(jobKey.getGroup());
                    sysJob.setDescription(trigger.getDescription());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        sysJob.setCronExpression(cronTrigger.getCronExpression());
                    }
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    sysJob.setStatus(triggerState.ordinal());
                    JobDataMap dataMap = trigger.getJobDataMap();
                    sysJob.setDataMap(JSON.toJSONString(dataMap));
                    list.add(sysJob);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void pauseAll(){

    }

    public Job getClass(String classname) throws Exception {
        Class<?> clazz = Class.forName(classname);
        return (Job) clazz.newInstance();
    }
}

