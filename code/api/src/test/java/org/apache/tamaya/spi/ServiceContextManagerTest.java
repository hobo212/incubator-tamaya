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
package org.apache.tamaya.spi;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Additional tests for {@link ServiceContextManager}, created by atsticks on 20.08.16.
 */
public class ServiceContextManagerTest {

    @Test
    public void setGetServiceContext() throws Exception {
        ServiceContext prev = ServiceContextManager.getServiceContext();
        try {
            MyServiceContext mine = new MyServiceContext();
            ServiceContextManager.set(mine);
            assertTrue(ServiceContextManager.getServiceContext() == mine);
            ServiceContextManager.set(mine);
            assertTrue(ServiceContextManager.getServiceContext() == mine);
        }finally{
            ServiceContextManager.set(prev);
            assertTrue(ServiceContextManager.getServiceContext() == prev);
        }

    }

    private static final class MyServiceContext implements ServiceContext{

        @Override
        public int ordinal() {
            return 0;
        }

        @Override
        public <T> T getService(Class<T> serviceType) {
            return null;
        }

        @Override
        public <T> List<T> getServices(Class<T> serviceType) {
            return Collections.emptyList();
        }
    }

}