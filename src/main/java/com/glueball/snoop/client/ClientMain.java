package com.glueball.snoop.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.glueball.snoop.module.main.model.SearchResults;

public class ClientMain {

    public static void main(final String[] args) throws InterruptedException {

        final List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJsonProvider());

        // final WebClient client = WebClient.create( "http://localhost:8080/",
        // providers);

        long start = System.currentTimeMillis();

        final Random rand = new Random(1000);

        final List<Thread> ths = new ArrayList<Thread>();
        for (int ind = 0; ind < 300; ind++) {

            final Thread th = new Thread(new Runnable() {

                @Override
                public void run() {

                    for (int i = 0; i < 50; i++) {

                        try {
                            Thread.sleep(500 + rand.nextInt(500));
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        final WebClient client = WebClient.create("http://localhost:8080/", providers);
                        final SearchResults results = client.path("search/java").query("page=1").accept(MediaType.APPLICATION_JSON).get(SearchResults.class);
                        System.out.println(i);
                        client.close();
                    }
                }
            });
            th.setName("CleintThread-" + ind);
            th.start();
            ths.add(th);
        }

        for (final Thread th : ths)
            th.join();

        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        /*
         * for (final SearchResult result : results) {
         * 
         * System.out.println(result.toString()); }
         */
    }
}
