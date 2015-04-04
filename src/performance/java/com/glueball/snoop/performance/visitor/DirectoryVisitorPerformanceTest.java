/**
 * 
 */
package com.glueball.snoop.performance.visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.glueball.snoop.visitor.DirectoryVisitor;

/**
 * @author karesz
 */
public class DirectoryVisitorPerformanceTest {

    @Test
    public final void test() throws IOException {

        final DirectoryVisitor visitor = new DirectoryVisitor();

        Files.walkFileTree(Paths.get("/home/karesz"), visitor);
    }

}
