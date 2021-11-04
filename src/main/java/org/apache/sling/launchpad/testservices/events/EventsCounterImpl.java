/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sling.launchpad.testservices.events;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;

import org.apache.felix.utils.json.JSONWriter;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Count the number of OSGi events that we receive on specific topics, and
 *  report them to clients.
 */
@SuppressWarnings("serial")
@Component(
        immediate=true,
        service = {
                javax.servlet.Servlet.class,
                org.osgi.service.event.EventHandler.class,
                org.apache.sling.launchpad.testservices.events.EventsCounter.class
        },
        property = {
            "service.description:String=Paths Test Servlet",
            "service.vendor:String=The Apache Software Foundation",
            "sling.servlet.paths:String=/testing/EventsCounter", 
            "sling.servlet.extensions:String=json", 
            org.osgi.service.event.EventConstants.EVENT_TOPIC + ":String=" + SlingConstants.TOPIC_RESOURCE_ADDED,
            org.osgi.service.event.EventConstants.EVENT_TOPIC + ":String=" + SlingConstants.TOPIC_RESOURCE_RESOLVER_MAPPING_CHANGED
        }
)
public class EventsCounterImpl extends SlingSafeMethodsServlet implements EventHandler,EventsCounter {

    private final Map<String, AtomicInteger> counters = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public synchronized void handleEvent(Event event) {
        final String topic = event.getTopic();
        AtomicInteger counter = counters.computeIfAbsent(topic, k -> new AtomicInteger());
        counter.incrementAndGet();
        log.debug("{} counter is now {}", topic, counter.get());
    }
    
    public synchronized int getEventsCount(String topic) {
        final AtomicInteger counter = counters.get(topic);
        if(counter == null) {
            log.debug("getEventsCount({}) returns 0, counter not found", topic);
            return 0;
        }
        return counter.get();
    }

    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) 
    throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            final JSONWriter w = new JSONWriter(response.getWriter());
            w.object();
            for(Map.Entry<String, AtomicInteger> entry : counters.entrySet()) {
                w.key(entry.getKey()).value(entry.getValue());
            }
            w.endObject();
            w.flush();
        } catch(IOException je) {
            throw (IOException)new IOException("JSONException in doGet").initCause(je);
        }
    }
}