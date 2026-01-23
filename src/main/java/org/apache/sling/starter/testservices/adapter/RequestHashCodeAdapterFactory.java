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
package org.apache.sling.starter.testservices.adapter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.starter.testservices.exported.RequestHashCodePojo;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

/**
 * AdapterFactory that adapts SlingHttpServletRequest to RequestHashCodePojo.
 * The POJO will contain the hash code of the request object as a string.
 */
@Component(
        service = AdapterFactory.class,
        property = {
            AdapterFactory.ADAPTABLE_CLASSES + ":String=org.apache.sling.api.SlingHttpServletRequest",
            AdapterFactory.ADAPTER_CLASSES
                    + ":String=org.apache.sling.starter.testservices.exported.RequestHashCodePojo"
        })
public class RequestHashCodeAdapterFactory implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(@NotNull Object adaptable, @NotNull Class<AdapterType> type) {
        if (adaptable instanceof SlingHttpServletRequest && type == RequestHashCodePojo.class) {
            SlingHttpServletRequest request = (SlingHttpServletRequest) adaptable;
            String hashCodeString = String.valueOf(request.hashCode());
            RequestHashCodePojo pojo = new RequestHashCodePojo(hashCodeString);
            return type.cast(pojo);
        }
        return null;
    }
}
