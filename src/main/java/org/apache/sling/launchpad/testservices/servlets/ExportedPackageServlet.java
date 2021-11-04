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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

/** Test servlet for SLING-2808 */
@Component(
        immediate=true, 
        service = javax.servlet.Servlet.class,
        property = {
                "service.description:String=Exported packages Test Servlet",
                "service.vendor:String=The Apache Software Foundation",
                "sling.servlet.resourceTypes:String=sling/servlet/default",
                "sling.servlet.selectors:String=EXPORTED_PACKAGES",
                "sling.servlet.extensions:String=txt"
        })
@SuppressWarnings("serial")
public class ExportedPackageServlet extends SlingSafeMethodsServlet {
    @Reference
    private PackageAdmin packageAdmin;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
            throws ServletException, IOException {
        final String packName = request.getParameter("package");
        final ExportedPackage p = packageAdmin.getExportedPackage(packName);
        if(p == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Package not found: " + packName);
        } else {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("PACKAGE FOUND: ");
            response.getWriter().write(p.toString());
            response.getWriter().flush();
        }
    }
}