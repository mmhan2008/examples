package com.example.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author user01
 * @create 2019/12/24
 */
@RestController
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SysJobService sysJobService;

    @PostMapping("addJob")
    public boolean addControl(@RequestBody SysJob sysJob) {
        Integer integer = 0;
        boolean b = false;
        try {
            integer = sysJobService.addJob(sysJob);
            if (integer > 0) {
                b = quartzService.addJob(sysJob.getJobClasspath(), sysJob.getJobName(), sysJob.getGroupName(),
                        sysJob.getCronExpression(), sysJob.getDataMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }

    @PostMapping("update")
    public boolean updateControl(@RequestBody SysJob sysJob){
        Integer integer = 0;
        boolean b = false;
        try {
            integer = sysJobService.updateJob(sysJob);
            if (integer > 0) {
                b = quartzService.updateJob(sysJob);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }

    @PostMapping("del")
    public boolean delControl(Integer id){
        boolean b = false;
        try {
            SysJob sysJob = sysJobService.queryJob(id);
            if (sysJob != null && sysJob.getJobName()!= null && sysJob.getGroupName() != null) {
                if (sysJobService.delJob(id) > 0) {
                    b = quartzService.delJob(sysJob.getJobName(), sysJob.getGroupName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }

    @RequestMapping("getAllJobs")
    public List<SysJob> getJobsControl () {
        try {
           return sysJobService.queryAllJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("pause")
    public boolean pauseControl(Integer id){
        boolean b = false;
        try {
            SysJob sysJob = sysJobService.queryJob(id);
            if (sysJob != null && sysJob.getJobName()!= null && sysJob.getGroupName() != null) {
                sysJob.setStatus(State.PAUSE.ordinal());
                Integer integer = sysJobService.updateJob(sysJob);
                if (integer > 0) {
                  b = quartzService.pauseJob(sysJob.getJobName(),sysJob.getGroupName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @PostMapping("resume")
    public boolean resumeControl(Integer id){
        boolean b = false;
        try {
            SysJob sysJob = sysJobService.queryJob(id);
            if (sysJob != null && sysJob.getJobName()!= null && sysJob.getGroupName() != null) {
                sysJob.setStatus(State.RUN.ordinal());
                Integer integer = sysJobService.updateJob(sysJob);
                if (integer > 0) {
                    b = quartzService.resumeJob(sysJob.getJobName(), sysJob.getGroupName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }

    @PostMapping("runNow")
    public boolean runNowControl(Integer id){
        boolean b = false;
        try {
            SysJob sysJob = sysJobService.queryJob(id);
            if (sysJob != null && sysJob.getJobName()!= null && sysJob.getGroupName() != null) {
                sysJob.setStatus(State.RUN.ordinal());
                Integer integer = sysJobService.updateJob(sysJob);
                if (integer > 0) {
                    b = quartzService.runJobNow(sysJob.getJobName(), sysJob.getGroupName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }
}

