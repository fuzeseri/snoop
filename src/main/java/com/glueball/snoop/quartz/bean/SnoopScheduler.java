package com.glueball.snoop.quartz.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import com.glueball.snoop.quartz.job.SnoopTask;
import com.glueball.snoop.quartz.task.TaskFactory;

/**
 * @author karesz
 */
public final class SnoopScheduler {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(SnoopScheduler.class);

    /**
     * Quartz scheduler to add jobs to it.
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * @param scheduler
     *            the scheduler to set
     */
    public final void setScheduler(final Scheduler scheduler) {

        this.scheduler = scheduler;
    }

    /**
     * List of task factories.
     */
    private List<TaskFactory> taskFactories;

    /**
     * @param taskFactories
     *            the taskFactories to set
     */
    @Required
    public final void setTaskFactories(final List<TaskFactory> taskFactories) {

        this.taskFactories = taskFactories;
    }

    public void init() {

        for (final TaskFactory factory : taskFactories) {

            for (final SnoopTask task : factory.createTasks()) {

                try {

                    final MethodInvokingJobDetailFactoryBean jobDetail =
                            new MethodInvokingJobDetailFactoryBean();
                    jobDetail.setTargetObject(task);
                    jobDetail.setTargetMethod("run");
                    jobDetail.setName(task.getName());
                    jobDetail.setConcurrent(false);
                    jobDetail.afterPropertiesSet();

                    final Trigger trigger = newTrigger()
                            // .forJob(job)
                            .withIdentity(task.getName(), factory.getName())
                            .startAt(new Date(new Date().getTime() + 10000))
                            .withSchedule(
                                    simpleSchedule()
                                            .withIntervalInSeconds(300)
                                            // task.getIntervalSeconds())
                                            .repeatForever())
                            .startNow()
                            .build();

                    scheduler.scheduleJob(jobDetail.getObject(), trigger);

                } catch (final SchedulerException e) {

                    LOG.info("ERROR scheduling task " + factory.getName()
                            + " - " + task.getName());
                    LOG.debug(e.getMessage(), e);
                } catch (final ClassNotFoundException e) {

                    LOG.info("ERROR scheduling task " + factory.getName()
                            + " - " + task.getName());
                    LOG.debug(e.getMessage(), e);
                } catch (final NoSuchMethodException e) {

                    LOG.info("ERROR scheduling task " + factory.getName()
                            + " - " + task.getName());
                    LOG.debug(e.getMessage(), e);
                }
            }
        }
    }
}
