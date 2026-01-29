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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import java.io.IOException;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPathsStrict;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class)
@SlingServletPathsStrict(paths = "/bin/streamed-upload", extensions = "txt", methods = "POST")
public class StreamedUploadServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final String REQUEST_PARTS_ITERATOR_ATTR = "request-parts-iterator";

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Get the request-parts-iterator attribute set by the Sling Engine
        Object iteratorAttr = request.getAttribute(REQUEST_PARTS_ITERATOR_ATTR);

        if (iteratorAttr == null) {
            response.getWriter()
                    .write("ERROR: " + REQUEST_PARTS_ITERATOR_ATTR + " attribute not found. "
                            + "Make sure to use 'Sling-uploadmode: stream' header or 'uploadmode=stream' parameter.");
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Iterator<?> partsIterator = (Iterator<?>) iteratorAttr;

        int partCount = 0;
        StringBuilder sb = new StringBuilder();

        while (partsIterator.hasNext()) {
            // will fail if the part does not implement the javax variant of the interface
            Part part = (Part) partsIterator.next();

            partCount++;
            sb.append("Part ").append(partCount).append(": name=").append(part.getName());
            String filename = part.getSubmittedFileName();
            if (filename != null) {
                sb.append(", filename=").append(filename);
            }
            sb.append("\n");
        }

        response.getWriter().write("OK: Processed " + partCount + " part(s) as javax.servlet.http.Part\n");
        response.getWriter().write(sb.toString());
    }
}
