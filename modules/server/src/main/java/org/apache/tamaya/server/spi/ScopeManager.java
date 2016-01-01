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
package org.apache.tamaya.server.spi;

import org.apache.tamaya.ConfigException;
import org.apache.tamaya.ConfigOperator;
import org.apache.tamaya.spi.ServiceContextManager;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton manager for scopes, used by the server component to filtering returned config.
 */
public final class ScopeManager {
    /** The logger used. */
    private static final Logger LOG = Logger.getLogger(ScopeManager.class.getName());

    private static Map<String, ScopeProvider> scopeProviders = initProviders();

    /**
     * Singleton constructor.
     */
    private static Map<String, ScopeProvider> initProviders(){
        Map<String, ScopeProvider> result = new ConcurrentHashMap<>();
        for(ScopeProvider prov: ServiceContextManager.getServiceContext().getServices(ScopeProvider.class)){
            try{
                result.put(prov.getScopeType(), prov);
            } catch(Exception e){
                LOG.log(Level.WARNING, "Error loading scopes from " + prov, e);
            }
        }
        return result;
    }

    /**
     * Singleton constructor.
     */
    private ScopeManager(){}

    /**
     * Get the scope given its name.
     *
     * @throws ConfigException if no such scope is defined
     * @param scopeId the scope name
     * @return the scope matching
     */
    public static ConfigOperator getScope(String scopeId, String target)
         throws ConfigException {
        ScopeProvider  prov = scopeProviders.get(scopeId);
        if(prov==null){
            throw new ConfigException("No such scope: " + scopeId);
        }
        return prov.getScope(target);
    }

    /**
     * Get the defined scope names.
     * @return the defined scope names, never null.
     */
    public static Set<String> getScopes(){
        return scopeProviders.keySet();
    }
}
