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
package org.apache.sling.launchpad.testservices.scripting;

import javax.script.Bindings;

import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.osgi.service.component.annotations.Component;

/** Example/test BindingsValuesProvider targeting all scripts */
@Component(
        immediate = true,
        property = {
            "service.description:String=StringBindingsValuesProvider",
            "service.vendor:String=The Apache Software Foundation"
        })
public class StringBindingsValuesProvider implements BindingsValuesProvider {

    public void addBindings(Bindings bindings) {
        bindings.put("helloWorld", "Hello World!");
    }
}
