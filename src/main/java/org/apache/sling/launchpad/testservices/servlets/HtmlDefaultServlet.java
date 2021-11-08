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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * Default servlet for the html extension, see SLING-1069.
 * This servlet collides with the Default GET Servlet generating proper HTML not
 * expected by HtmlDefaultServletTest. For this reason this component is
 * disabled by default and must be enabled (by creating a config) for testing 
 * in the HtmlDefaultServletTest class.
 */
@Component(
        immediate=true, 
        service = javax.servlet.Servlet.class, 
        configurationPolicy = ConfigurationPolicy.REQUIRE,
        property = {
                "service.description:String=HTML Default Test Servlet",
                "service.vendor:String=The Apache Software Foundation",
                "sling.servlet.resourceTypes:String=sling/servlet/default",
                "sling.servlet.extensions:String=html",
                "sling.servlet.methods:String=GET"
        })
@SuppressWarnings("serial")
public class HtmlDefaultServlet extends TestServlet {
}