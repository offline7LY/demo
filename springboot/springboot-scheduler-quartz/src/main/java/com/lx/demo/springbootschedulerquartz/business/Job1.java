package com.lx.demo.springbootschedulerquartz.business;

import lombok.extern.slf4j.Slf4j;

/**
 * jobToBeExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job1#execute is going to start...
 * jobToBeExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job2#execute is going to start...
 * 2019-08-11 19:28:40.010  INFO 14275 --- [ryBean_Worker-1] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
 * 2019-08-11 19:28:40.010  INFO 14275 --- [ryBean_Worker-2] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
 * 2019-08-11 19:28:40.012  INFO 14275 --- [ryBean_Worker-1] c.l.d.s.business.Job1                    : execute 执行了...
 * 2019-08-11 19:28:40.012  INFO 14275 --- [ryBean_Worker-2] c.l.d.s.business.Job2                    : execute 执行了...
 * jobWasExecuted
 * jobWasExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job1#execute is finished...
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job2#execute is finished...
 * jobToBeExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job1#execute is going to start...
 * 2019-08-11 19:28:45.001  INFO 14275 --- [ryBean_Worker-3] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
 * 2019-08-11 19:28:45.001  INFO 14275 --- [ryBean_Worker-3] c.l.d.s.business.Job1                    : execute 执行了...
 * jobWasExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job1#execute is finished...
 * jobToBeExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job2#execute is going to start...
 * 2019-08-11 19:28:45.002  INFO 14275 --- [ryBean_Worker-1] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
 * 2019-08-11 19:28:45.002  INFO 14275 --- [ryBean_Worker-1] c.l.d.s.business.Job2                    : execute 执行了...
 * jobWasExecuted
 * Job : group1.com.lx.demo.springbootschedulerquartz.business.Job2#execute is finished...
 */
@Slf4j
public class Job1 {

    public void execute(){
        log.info("{} 执行了...", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
