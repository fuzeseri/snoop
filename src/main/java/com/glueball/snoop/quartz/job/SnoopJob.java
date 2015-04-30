package com.glueball.snoop.quartz.job;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author karesz
 */
public class SnoopJob extends QuartzJobBean {

    /**
     * SnoopTask to exeute scheduled.
     */
    private SnoopTask task;

    /**
     * Construtor.
     *
     * @param pTask
     *            SnoopTask to exeute scheduled.
     */
    public SnoopJob(final SnoopTask pTask) {

        this.task = pTask;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
     * .quartz.JobExecutionContext)
     */
    @Override
    protected void executeInternal(final JobExecutionContext context)
            throws JobExecutionException {

        System.out.println("HELOOOOOOOOOOOOOOOOOO");
        task.run();
    }

    /**
     * @return the task
     */
    public SnoopTask getTask() {

        return task;
    }

    /**
     * @param task
     *            the task to set
     */
    public void setTask(final SnoopTask task) {

        this.task = task;
    }
}
