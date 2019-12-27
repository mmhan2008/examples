package com.example.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author user01
 * @create 2019/12/26
 */
@Service
public class SysJobService {

    @Autowired
    private JdbcTemplate template;

    public Integer addJob(SysJob sysJob) throws Exception{
        String sql = "insert into my_job_details (job_group,job_name,job_class_path" +
                ",job_cron,job_data_map,job_status,job_describe) values (?,?,?,?,?,?,?)";
        Object[] args = {sysJob.getGroupName(),sysJob.getJobName(),sysJob.getJobClasspath(),sysJob.getCronExpression(),
                sysJob.getDataMap(),1,sysJob.getDescription()};
        return template.update(sql, args);
    }

    public Integer updateJob(SysJob sysJob) throws Exception{
        String sql = "update my_job_details set job_group=?,job_name=?,job_class_path=?" +
                ",job_cron=?,job_data_map=?,job_status=?,job_describe=? where id = ?";
        Object[] args = {sysJob.getGroupName(),sysJob.getJobName(),sysJob.getJobClasspath(),
                sysJob.getCronExpression(),sysJob.getDataMap(),sysJob.getStatus(),sysJob.getDescription(),sysJob.getId()};
        return template.update(sql, args);

    }

    public Integer delJob(Integer id) throws Exception{
        String sql = "delete from my_job_details where id = ?";
        Object[] args = {id};
        return template.update(sql, args);
    }

    public SysJob queryJob(Integer id) throws Exception{
        String sql = "select job_group,job_name,job_class_path,job_cron,job_data_map,job_status,job_describe from my_job_details where id = ?";
        return template.queryForObject(sql, new RowMapper<SysJob>() {
            @Override
            public SysJob mapRow(ResultSet rs, int rowNum) throws SQLException {
                SysJob sysJob = new SysJob();
                sysJob.setId(id);
                sysJob.setJobName(rs.getString("job_name"));
                sysJob.setGroupName(rs.getString("job_group"));
                sysJob.setJobClasspath(rs.getString("job_class_path"));
                sysJob.setCronExpression(rs.getString("job_cron"));
                sysJob.setDataMap(rs.getString("job_data_map"));
                sysJob.setJobName(rs.getString("job_name"));
                sysJob.setStatus(rs.getInt("job_status"));
                sysJob.setDescription(rs.getString("job_describe"));
                return sysJob;
            }
        },id);
    }

    public List<SysJob> queryAllJob() throws Exception{
        List<SysJob> list = new ArrayList<>();
        String sql = "select * from my_job_details";
        template.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                SysJob sysJob = new SysJob();
                sysJob.setId(rs.getInt("id"));
                sysJob.setJobName(rs.getString("job_name"));
                sysJob.setGroupName(rs.getString("job_group"));
                sysJob.setJobClasspath(rs.getString("job_class_path"));
                sysJob.setCronExpression(rs.getString("job_cron"));
                sysJob.setDataMap(rs.getString("job_data_map"));
                sysJob.setJobName(rs.getString("job_name"));
                sysJob.setStatus(rs.getInt("job_status"));
                sysJob.setDescription(rs.getString("job_describe"));
                list.add(sysJob);
            }
        });
        return list;
    }
}

