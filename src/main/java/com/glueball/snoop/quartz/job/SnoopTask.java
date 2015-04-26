package com.glueball.snoop.quartz.job;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
/**
 * Interface to define scheduled task.
 *
 * @author karesz
 */
public interface SnoopTask {

    /**
     * Method to call by the scheduler.
     */
    void run();

    /**
     * @return the name of the task.
     */
    String getName();

    /**
     * Set the name of the task.
     *
     * @param name
     *            the name to set.
     */
    void setName(String name);

    /**
     * The sleep interval between runs in seconds.
     *
     * @return the interval in seconds.
     */
    int getIntervalSeconds();
}
