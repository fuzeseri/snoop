/**
 * 
 */
package com.glueball.snoop.quartz.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.entity.NetworkShares;
import com.glueball.snoop.quartz.job.SnoopTask;
import com.glueball.snoop.task.document.ScanTask;
import com.glueball.snoop.util.ScheduleHelper;

/**
 * @author karesz
 */
public final class ScanTaskFactory implements TaskFactory {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(ScanTaskFactory.class);

    /**
     * Name of the group of task created by this factory.
     */
    private static final String TASK_GROUP_NAME = "scanTasks";

    /**
     * Full path of the shares.xml file.
     */
    private String sharesXml;

    /**
     * @param sharesXml
     *            the sharesXml to set
     */
    @Required
    public void setSharesXml(final String sharesXml) {

        this.sharesXml = sharesXml;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.task.TaskFactory#createTasks()
     */
    @Override
    public List<SnoopTask> createTasks() {

        final List<NetworkShare> shares = getShares();
        final List<SnoopTask> tasks = new ArrayList<SnoopTask>(shares.size());
        for (final NetworkShare share : shares) {

            final ScanTask task = new ScanTask(
                    share,
                    ScheduleHelper.getIntervalInSeconds(share.getScanDelay()),
                    share.getName());

            tasks.add(task);
        }
        return tasks;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.task.TaskFactory#getName()
     */
    @Override
    public String getName() {

        return TASK_GROUP_NAME;
    }

    /**
     * Unmarshall the network share instances from shares.xml file.
     *
     * @return list of NetworkShare instances.
     */
    private List<NetworkShare> getShares() {

        final List<NetworkShare> shares = new ArrayList<NetworkShare>();
        try {

            final JAXBContext jaxbContext = JAXBContext
                    .newInstance(NetworkShares.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext
                    .createUnmarshaller();
            final NetworkShares netShares = (NetworkShares) jaxbUnmarshaller
                    .unmarshal(new File(sharesXml));
            shares.addAll(netShares.getShares());
        } catch (JAXBException e) {

            LOG.error("IO ERROR when unmarshalling shares.xml");
            LOG.debug(e.getMessage());
        }

        return shares;
    }
}
