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
package org.apache.sling.launchpad.testservices.adapter;

import org.apache.sling.api.adapter.AdapterFactory;
import org.osgi.service.component.annotations.Component;

/**
 * Service which actually is an adapter factory. See SLING-2522.
 */
@Component(
        service = AdapterFactory.class,
        property = {
            AdapterFactory.ADAPTABLE_CLASSES + ":String=org.apache.sling.api.resource.Resource",
            AdapterFactory.ADAPTER_CLASSES + ":String=something_which_should_appear"
        })
public class ReallyAnAdapterFactory implements AdapterFactory {

    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        // TODO Auto-generated method stub
        return null;
    }
}
