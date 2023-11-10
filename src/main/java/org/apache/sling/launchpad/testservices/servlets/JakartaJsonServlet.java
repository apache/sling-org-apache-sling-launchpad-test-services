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
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPathsStrict;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonWriter;

/**
 * Serlializes a basic class using the Jakarta JSON API 
 * 
 */
@Component(service=Servlet.class)
@SlingServletPathsStrict(
        paths = "/bin/jakarta",
        extensions = "json")
public class JakartaJsonServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response)
			throws ServletException, IOException {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add("greeting", "hello, world");
		JsonObject object = objectBuilder.build();
		
		response.setContentType("application/json");
		try ( JsonWriter writer = Json.createWriter(response.getOutputStream()) ) {
			writer.writeObject(object);
		}
	}
}
