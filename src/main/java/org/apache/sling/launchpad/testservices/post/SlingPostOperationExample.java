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
package org.apache.sling.launchpad.testservices.post;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.servlets.post.PostOperation;
import org.apache.sling.servlets.post.PostResponse;
import org.apache.sling.servlets.post.SlingPostProcessor;

/** Example PostOperation used in integration tests */
@Component(immediate=true)
@Service
@Property(name=PostOperation.PROP_OPERATION_NAME, value="test:example")
public class SlingPostOperationExample implements PostOperation {
    public void run(SlingHttpServletRequest request, PostResponse response, SlingPostProcessor[] processors) {
        final Resource r = request.getResource();
        final Node n = r.adaptTo(Node.class);
        try {
            response.setPath(r.getPath());
            response.setTitle("Content modified by " + getClass().getSimpleName());
            n.setProperty(getClass().getName(), "Operation was applied to " + n.getPath());
            n.getSession().save();
        } catch(RepositoryException re) {
            throw new SlingException(getClass().getSimpleName() + " failed", re);
        }
    }
}