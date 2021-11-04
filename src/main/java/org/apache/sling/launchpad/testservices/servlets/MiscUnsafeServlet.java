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

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPathsStrict;
import org.osgi.service.component.annotations.Component;

/**
 * Validates that the <tt>sun.misc.Unsafe</tt> class can be accessed
 *
 */
@Component(service=Servlet.class)
@SlingServletPathsStrict(
        paths = "/bin/miscUnsafe",
        extensions = "txt")
public class MiscUnsafeServlet extends SlingAllMethodsServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        // use the classloader instead of the class literal to prevent generating an Import-Package clause
        // for the sun.misc package
        response.setContentType("text/plain");
        try {
            response.getWriter().write(getClass().getClassLoader().loadClass("sun.misc.Unsafe").getCanonicalName());
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed loading Unsafe class", e);
        }
    }
}
