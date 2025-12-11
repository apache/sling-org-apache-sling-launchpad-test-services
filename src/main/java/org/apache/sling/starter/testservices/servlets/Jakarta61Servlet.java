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
package org.apache.sling.starter.testservices.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.Servlet;
import org.apache.sling.api.SlingJakartaHttpServletRequest;
import org.apache.sling.api.SlingJakartaHttpServletResponse;
import org.apache.sling.api.servlets.SlingJakartaSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPathsStrict;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

/**
 * Jakarta Servlet using new setCharacterEncoding overload added in Servlet API 6.1.
 */
@Component(service = Servlet.class)
@SlingServletPathsStrict(paths = "/bin/jakarta-servlet61", extensions = "txt")
public class Jakarta61Servlet extends SlingJakartaSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            @NotNull SlingJakartaHttpServletRequest request, @NotNull SlingJakartaHttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain");
        // setCharacterEncoding overload with Charset added in Servlet API 6.1
        response.setCharacterEncoding(StandardCharsets.UTF_8);
        response.getWriter().write("Hello Servlet API 6.1");
    }
}
