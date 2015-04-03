/**
 * 
 */
package com.glueball.snoop.test.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.glueball.snoop.entity.Meta;

/**
 * @author karesz
 */
public class MetaTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.entity.Meta#Meta(java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public final void testMeta() {

        final String author = "Author";
        final String title = "Title";
        final String desc = "Description";

        final Meta meta = new Meta(author, title, desc);

        assertEquals(author, meta.getAuthor());
        assertEquals(title, meta.getTitle());
        assertEquals(desc, meta.getDescription());
    }

    /**
     * Test method for {@link com.glueball.snoop.entity.Meta#hasContent()}.
     */
    @Test
    public final void testHasContent() {

        final String author = "Author";
        final String title = "Title";
        final String desc = "Description";

        // Non empty fields
        final Meta meta = new Meta(author, title, desc);

        assertTrue(meta.hasContent());

        // Emty author
        final Meta metaEmptyAuthor = new Meta("", title, desc);

        assertTrue(metaEmptyAuthor.hasContent());

        // Empty title
        final Meta metaEmptyTitle = new Meta(author, "", desc);

        assertTrue(metaEmptyTitle.hasContent());

        // Empty description
        final Meta metaEmptyDesc = new Meta(author, title, "");

        assertTrue(metaEmptyDesc.hasContent());

        // Empty fields
        final Meta emptyMeta = new Meta("", "", "");

        assertTrue(!emptyMeta.hasContent());

        // Null author
        final Meta metaNullAuthor = new Meta(null, title, desc);

        assertTrue(metaNullAuthor.hasContent());

        // Null title
        final Meta metaNullTitle = new Meta(author, null, desc);

        assertTrue(metaNullTitle.hasContent());

        // Null description
        final Meta metaNullDesc = new Meta(author, title, null);

        assertTrue(metaNullDesc.hasContent());

        // Null fields
        final Meta nullMeta = new Meta(null, null, null);

        assertTrue(!nullMeta.hasContent());

    }

    /**
     * Test method for {@link com.glueball.snoop.entity.Meta#hasTitle()}.
     */
    @Test
    public final void testHasTitle() {

        final String author = "Author";
        final String title = "Title";
        final String desc = "Description";

        // Non empty fields
        final Meta meta = new Meta(author, title, desc);

        assertTrue(meta.hasTitle());

        // Empty title
        final Meta metaEmptyTitle = new Meta(author, "", desc);

        assertTrue(!metaEmptyTitle.hasTitle());

        // Null title
        final Meta metaNullTitle = new Meta(author, null, desc);

        assertTrue(!metaNullTitle.hasTitle());
    }

    /**
     * Test method for {@link com.glueball.snoop.entity.Meta#hasAuthor()}.
     */
    @Test
    public final void testHasAuthor() {

        final String author = "Author";
        final String title = "Title";
        final String desc = "Description";

        // Non empty fields
        final Meta meta = new Meta(author, title, desc);

        assertTrue(meta.hasAuthor());

        // Empty author
        final Meta metaEmptyAuthor = new Meta("", title, desc);

        assertTrue(!metaEmptyAuthor.hasAuthor());

        // Null author
        final Meta metaNullAuthor = new Meta(null, title, desc);

        assertTrue(!metaNullAuthor.hasAuthor());
    }

    /**
     * Test method for {@link com.glueball.snoop.entity.Meta#hasDescription()}.
     */
    @Test
    public final void testHasDescription() {

        final String author = "Author";
        final String title = "Title";
        final String desc = "Description";

        // Non empty fields
        final Meta meta = new Meta(author, title, desc);

        assertTrue(meta.hasDescription());

        // Empty description
        final Meta metaEmptyDescription = new Meta(author, title, "");

        assertTrue(!metaEmptyDescription.hasDescription());

        // Null descriptiom
        final Meta metaNullDescription = new Meta(author, title, null);

        assertTrue(!metaNullDescription.hasDescription());
    }
}
