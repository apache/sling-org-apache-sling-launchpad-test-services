/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.launchpad.testservices.servlets.requestdispatcher;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.builder.Builders;
import org.apache.sling.api.request.builder.SlingHttpServletResponseResult;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

/**
 * Servlet using RequestDispatcher.include to include output of another servlet in it's response.
 * This time the inclusion is not done directly on the response, but the include is
 * done into a "synthetic response" created via Sling Builders and the output than written to the real response.
 */
@Component(
        immediate=true,
        service = javax.servlet.Servlet.class,
        property = {
                "service.description:String=Paths Test Servlet",
                "service.vendor:String=The Apache Software Foundation",
                "sling.servlet.paths:String=" + RequestDispatcherIncludeBufferedServlet.PATH
        })
@SuppressWarnings("serial")
public class RequestDispatcherIncludeBufferedServlet extends SlingSafeMethodsServlet {

    static final String PATH = "/testing/requestDispatcher/includeBuffered";

    @Override
    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");

        response.getWriter().write("includeBuffered(");

        // build "synthetic response"
        SlingHttpServletResponseResult syntheticResponse = Builders.newResponseBuilder().build();

        // write output of other servlet to this response to allow to post-process or validate it
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(OriginalResponseServlet.PATH);
        requestDispatcher.include(request, syntheticResponse);

        // write generated output to response
        response.getWriter().write(syntheticResponse.getOutputAsString());

        response.getWriter().write(")");
    }

}
