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
package org.apache.sling.launchpad.testservices.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HtmlResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

/** Servlet used to test HtmlResponse escaping */
@Component(
        immediate = true,
        service = javax.servlet.Servlet.class,
        property = {
            "service.description:String=Paths Test Servlet",
            "service.vendor:String=The Apache Software Foundation",
            "sling.servlet.paths:String=/testing/HtmlResponseServlet"
        })
@SuppressWarnings("serial")
public class HtmlResponseServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        final HtmlResponse hr = new HtmlResponse();
        // Specific status to help recognize this servlet in tests
        final int status = HttpServletResponse.SC_GATEWAY_TIMEOUT;
        hr.setStatus(status, getClass().getName() + ": GET always fails with status " + status);
        hr.setLocation("Location: some <script>");
        hr.setTitle(getClass().getName() + ": fake response to test <escaping>");
        hr.send(response, true);
    }
}
