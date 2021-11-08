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
package org.apache.sling.launchpad.testservices.servlets;

import java.io.IOException;

import javax.jcr.Repository;
import javax.servlet.ServletException;

import org.apache.felix.utils.json.JSONWriter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/** Test servlet that dumps our repository descriptors */ 
@SuppressWarnings("serial")
@Component(
        immediate = true,
        service = javax.servlet.Servlet.class,
        property = {
                "service.description:String=Repository Descriptors Servlet",
                "service.vendor:String=The Apache Software Foundation",
                "sling.servlet.paths:String=/testing/RepositoryDescriptors",
                "sling.servlet.extensions:String=json"
        })
public class RepositoryDescriptorsServlet extends SlingSafeMethodsServlet {

    @Reference
    private Repository repository;
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
    throws ServletException,IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            final JSONWriter w = new JSONWriter(response.getWriter());
            w.object();
            w.key("descriptors");
            w.object();
            for(String key : repository.getDescriptorKeys()) {
                w.key(key).value(repository.getDescriptor(key));
            }
            w.endObject();
            w.endObject();
            w.flush();
        } catch(IOException je) {
            throw (IOException)new IOException("JSONException in doGet").initCause(je);
        }
    }
}