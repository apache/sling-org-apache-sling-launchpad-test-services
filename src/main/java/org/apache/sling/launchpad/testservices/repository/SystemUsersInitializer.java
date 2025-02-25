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
package org.apache.sling.launchpad.testservices.repository;

import javax.jcr.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.api.SlingRepositoryInitializer;
import org.apache.sling.jcr.repoinit.JcrRepoInitOpsProcessor;
import org.apache.sling.repoinit.parser.RepoInitParser;
import org.apache.sling.repoinit.parser.operations.Operation;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SlingRepositoryInitializer that creates system users and sets their ACLs.
 * Meant to be used for our integration tests until we can create those from
 * the provisioning model.
 */
@Component(service = SlingRepositoryInitializer.class)
public class SystemUsersInitializer implements SlingRepositoryInitializer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REPOINIT_FILE = "/repoinit.txt";

    @Reference
    private RepoInitParser parser;

    @Reference
    private JcrRepoInitOpsProcessor processor;

    @Override
    public void processRepository(SlingRepository repo) throws Exception {
        final Session s = repo.loginAdministrative(null);
        final InputStream is = getClass().getResourceAsStream(REPOINIT_FILE);
        try {
            if (is == null) {
                throw new IOException("Class Resource not found:" + REPOINIT_FILE);
            }
            final List<Operation> ops;
            try (final Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                ops = parser.parse(r);
            }
            log.info("Executing {} repoinit Operations", ops.size());
            processor.apply(s, ops);
            s.save();
        } finally {
            s.logout();
            if (is != null) {
                is.close();
            }
        }
    }
}
