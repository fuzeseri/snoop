package com.glueball.snoop.client;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
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

        long start = System.currentTimeMillis();

        final Random rand = new Random(1000);

        final List<Thread> ths = new ArrayList<Thread>();
        for (int ind = 0; ind < 100; ind++) {

            final Thread th = new Thread(new Runnable() {

                @Override
                public void run() {

                    for (int i = 0; i < 100; i++) {

                        try {
                            Thread.sleep(500 + rand.nextInt(500));
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }

                        final WebClient client = WebClient.create(
                                "http://localhost:8080/", providers);

                        final SearchResults results = client
                                .path("search/java").query("page=1")
                                .accept(MediaType.APPLICATION_JSON)
                                .get(SearchResults.class);

                        System.out.println(i);
                        client.close();
                    }
                }
            });
            th.setName("CleintThread-" + ind);
            th.start();
            ths.add(th);
        }

        for (final Thread th : ths) {
            th.join();
        }

        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
    }
}
