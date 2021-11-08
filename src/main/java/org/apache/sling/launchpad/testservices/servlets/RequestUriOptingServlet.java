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

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.servlets.OptingServlet;
import org.osgi.service.component.annotations.Component;

/** OptingServlet that uses the RequestURI to opt in */
@Component(
        immediate=true, 
        service = javax.servlet.Servlet.class,
        property = {
                "service.description:String=Request URI Opting Test Servlet",
                "service.vendor:String=The Apache Software Foundation",
                "sling.servlet.resourceTypes:String=sling/servlet/default",
                "sling.servlet.resourceTypes:String=sling/nonexisting",
                "sling.servlet.methods:String=POST",
                "sling.servlet.methods:String=GET",
        })
@SuppressWarnings("serial")
public class RequestUriOptingServlet extends TestServlet implements OptingServlet {

    public boolean accepts(SlingHttpServletRequest request) {
        // Use a fake selector to opt in. Not really useful, this
    	// is just a test of the opting mechanism.
        return request.getRequestURI().contains(".RequestUriOptingServlet.html");
    }
}
