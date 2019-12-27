$(function() {
    getJobList();
    $('#new-job').click(function () {
        $('#add-confirm').modal({
            relatedElement: this,
            onConfirm: function(data) {
                var jobName = $('.job-name-a input').val()
                var groupName = $('.group-name-a input').val()
                var cronExpression = $('.cron-a input').val()
                var dataMap = $('.data-map-a input').val()
                var description = $('.description-a input').val()
                var jobClasspath = $('.class-path-a input').val()
                var job = {
                    'jobName':jobName,
                    'groupName':groupName,
                    'cronExpression':cronExpression,
                    'dataMap':dataMap,
                    'description':description,
                    'jobClasspath':jobClasspath
                }
                addJob(job);
            },onCancel: function() {
            }
        })
    });

    $('.result-list').delegate('.edit','click',function () {
        var id = $(this).parents('.gradeX').find('td').eq(0).text()
        var jobName = $(this).parents('.gradeX').find('td').eq(2).text()
        var groupName = $(this).parents('.gradeX').find('td').eq(1).text()
        var cronExpression = $(this).parents('.gradeX').find('td').eq(4).text()
        var dataMap = $(this).parents('.gradeX').find('td').eq(5).text()
        var description = $(this).parents('.gradeX').find('td').eq(7).text()
        var jobClasspath = $(this).parents('.gradeX').find('td').eq(3).text()
        var status = $(this).parents('.gradeX').find('td').eq(6).text()
        $('.job-id-e input').val(id)
        $('.job-name-e input').val(jobName)
        $('.group-name-e input').val(groupName)
        $('.cron-e input').val(cronExpression)
        $('.data-map-e input').val(dataMap)
        $('.description-e input').val(description)
        $('.class-path-e input').val(jobClasspath)
        $('.status-e input').val(status)
        $('#edit-confirm').modal({
            relatedElement: this,
            onConfirm: function(data) {
                var id = $('.job-id-e input').val()
                var jobName = $('.job-name-e input').val()
                var groupName = $('.group-name-e input').val()
                var cronExpression = $('.cron-e input').val()
                var dataMap = $('.data-map-e input').val()
                var description = $('.description-e input').val()
                var jobClasspath = $('.class-path-e input').val()
                var status = $('.status-e input').val() == '已暂停' ? 0:1;
                var job = {
                    'id':id,
                    'jobName':jobName,
                    'groupName':groupName,
                    'cronExpression':cronExpression,
                    'dataMap':dataMap,
                    'description':description,
                    'jobClasspath':jobClasspath,
                    'status':status
                }
                updateJob(job);
            },onCancel: function() {
            }
        })
    })
})

function getJobList() {
    $.ajax({
        url:"getAllJobs",
        type:"get",
        async:false,
        success:function (data) {
            console.log(data)
            $(".result-list").html("");
            $.each(data,function (i,o) {
                var status = o.status == 1?'正在运行':'已暂停';
                $(".result-list").append("<tr class='gradeX'>" +
                    "<td>" + o.id + "</td>" +
                    "<td>" + o.groupName + "</td>" +
                    "<td>" + o.jobName + "</td>" +
                    "<td>" + o.jobClasspath + "</td>" +
                    "<td>" + o.cronExpression + "</td>" +
                    "<td>" + o.dataMap + "</td>" +
                    "<td>" + status + "</td>" +
                    "<td>" + o.description + "</td>" +
                    "<td><div class='tpl-table-black-operation'>" +
                    "<a href='javascript:;' class='edit'><i class='am-icon-pencil'></i> 编辑</a>&nbsp;" +
                    "<a href='javascript:;' onclick='pause(\""+o.id+"\")'><i class='am-icon-pause'></i> 暂停</a>&nbsp;" +
                    "<a href='javascript:;' onclick='resume(\""+o.id+"\")'><i class='am-icon-play'></i> 启动</a>&nbsp;" +
                    "<a href='javascript:;' onclick='del(\""+o.id+"\")'><i class='am-icon-trash'></i> 删除</a>" +
                    "</div></td></tr>");
            })
        }
    })
}
function addJob(job) {
    var sysJob = job;
    $.ajax({
        url:"addJob",
        type:"post",
        data:JSON.stringify(sysJob),
        contentType: "application/json",
        async:false,
        success :function (data) {
            console.log(data)
            getJobList();
        }
    })
}

function updateJob(job) {
    var sysJob = job;
    $.ajax({
        url:"update",
        type:"post",
        data:JSON.stringify(sysJob),
        contentType: "application/json",
        async:false,
        success :function (data) {
            console.log(data)
            alert(data == false ? '任务更新失败':'已更新');
            getJobList();
        }
    })
}

function pause(id) {
    $.ajax({
        url:"pause",
        type:"post",
        data:{"id": id},
        async:false,
        success :function (data) {
            console.log(data)
            alert(data == false ? '暂停失败':'任务已暂停');
            getJobList();
        }
    })
}
function resume(id) {
    $.ajax({
        url:"resume",
        type:"post",
        data:{"id": id},
        async:false,
        success :function (data) {
            console.log(data)
            alert(data == false ? '恢复失败':'任务已恢复运行');
            getJobList();
        }
    })
}

function del(id) {
    $.ajax({
        url:"del",
        type:"post",
        data:{"id": id},
        async:false,
        success :function (data) {
            console.log(data)
            alert(data == false ? '删除失败':'删除成功');
            getJobList();
        }
    })
}
