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
package org.apache.sling.launchpad.testservices.exported;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

public class FakeSlingHttpServletRequest implements HttpServletRequest {

    private final String pathInfo;

    private final String method;

    private final String scheme;

    private final String host;

    private final int port;

    private final Map<String, Object> attrs = new HashMap<>();

    private String contextPath;

    public FakeSlingHttpServletRequest(String pathInfo) {
        this(pathInfo, null);
    }

    public FakeSlingHttpServletRequest(String pathInfo, String httpMethod) {
        this(null, null, -1, pathInfo, httpMethod);
    }

    public FakeSlingHttpServletRequest(String scheme, String host, int port,
            String pathInfo) {
        this(scheme, host, port, pathInfo, null);
    }

    public FakeSlingHttpServletRequest(String scheme, String host, int port,
            String pathInfo, String httpMethod) {
        this.scheme = (scheme == null) ? "http" : scheme;
        this.host = (host == null) ? "localhost" : host;
        this.port = port;
        this.pathInfo = pathInfo;
        this.method = httpMethod;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public Object getAttribute(String name) {
        return attrs.get(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.emptyEnumeration();
    }

    public String getCharacterEncoding() {
        return null;
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public String getLocalName() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration<Locale> getLocales() {
        return null;
    }

    public String getParameter(String name) {
        return null;
    }

    public Map<String, String[]> getParameterMap() {
        return Collections.emptyMap();
    }

    public Enumeration<String> getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String name) {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public BufferedReader getReader() {
        return null;
    }

    public String getRealPath(String path) {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public String getScheme() {
        return scheme;
    }

    public String getServerName() {
        return host;
    }

    public int getServerPort() {
        return port;
    }

    public boolean isSecure() {
        return false;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void removeAttribute(String name) {
    }

    public void setAttribute(String name, Object o) {
        attrs.put(name, o);
    }

    public void setCharacterEncoding(String env) {
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String name) {
        return 0;
    }

    public String getHeader(String name) {
        return null;
    }

    public Enumeration<String> getHeaderNames() {
        return null;
    }

    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    public int getIntHeader(String name) {
        return 0;
    }

    public String getMethod() {
        return method;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getQueryString() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getServletPath() {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public HttpSession getSession(boolean create) {
        return null;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public long getContentLengthLong() {
        return -1;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {
        //no-op
    }

    @Override
    public void logout() throws ServletException {
        //no-op
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return Collections.emptyList();
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

}
