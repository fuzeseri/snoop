package com.glueball.snoop.quartz.task;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import com.glueball.snoop.quartz.job.SnoopTask;

/**
 * @author karesz
 */
public interface TaskFactory {

    /**
     * @return
     */
    List<SnoopTask> createTasks();

    String getName();

}
