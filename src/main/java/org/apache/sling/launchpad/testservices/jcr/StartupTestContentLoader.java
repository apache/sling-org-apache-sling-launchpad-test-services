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
package org.apache.sling.launchpad.testservices.jcr;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.sling.jcr.jackrabbit.server.TestContentLoader;
import org.osgi.service.component.ComponentContext;

/**
 * Component which loads the JCR test content on startup.
 *
 * @scr.component enabled="false" metatype="no"
 *
 * @scr.property name="service.description" value="Test Content Loader"
 * @scr.property name="service.vendor" value="The Apache Software Foundation"
 *
 */
public class StartupTestContentLoader {

    /** @scr.reference */
    private TestContentLoader loader;

    // ---------- SCR integration

    protected void activate(ComponentContext context) throws RepositoryException, IOException {
        loader.loadTestContent();
    }

    protected void deactivate(ComponentContext context) {
        ;
    }

}
