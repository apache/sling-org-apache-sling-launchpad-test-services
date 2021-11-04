/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.sling.launchpad.testservices.servlets;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPathsStrict;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The <tt>DomServlet</tt> evaluates a simple XML document using DOM APIs.
 * 
 */
@Component(service=Servlet.class)
@SlingServletPathsStrict(
        paths = "/bin/dom",
        extensions = "xml")
public class DomServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final String XML_INPUT = "<content><name>DOM</name></content>";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // completely disable external entities declarations:
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new InputSource(new StringReader(XML_INPUT)));

            NodeList contentNodeList = document.getElementsByTagName("content");
            Node contentNode = contentNodeList.item(0);
            Node nameNode = contentNode.getFirstChild();
            String result = nameNode.getTextContent();

            response.setContentType("text/plain");
            response.getWriter().write(result);
        } catch (SAXException | ParserConfigurationException e) {
            throw new ServletException(e);
        }
    }

}
