/**
 * 
 */
package com.glueball.snoop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "paths")
public class DocumentPaths {

    @XmlElement(name = "path")
    private List<DocumentPath> paths = new ArrayList<DocumentPath>();

    /**
     * @return the paths
     */
    public List<DocumentPath> getPaths() {

        return paths;
    }

    /**
     * @param paths
     *            the paths to set
     */
    public void setPaths(final List<DocumentPath> paths) {

        this.paths = paths;
    }
}
