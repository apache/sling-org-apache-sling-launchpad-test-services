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
package org.apache.sling.launchpad.testservices.handlers;

import org.apache.jackrabbit.server.io.DeleteHandler;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

@Component(
        service = DeleteHandler.class,
        property = {
                Constants.SERVICE_RANKING + ":Integer=2"
        }
        )
public class TestDeleteHandler2 extends AbstractDeleteHandler {

    public TestDeleteHandler2() {
        this.HANDLER_NAME = "test-delete-handler-2";
        this.HANDLER_BKP = "backed-up-by-" + HANDLER_NAME;
    }

}
