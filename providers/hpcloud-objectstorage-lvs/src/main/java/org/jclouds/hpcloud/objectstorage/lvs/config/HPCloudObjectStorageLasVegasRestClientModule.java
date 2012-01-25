/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.hpcloud.objectstorage.lvs.config;

import static org.jclouds.hpcloud.objectstorage.lvs.reference.HPCloudObjectStorageLasVegasConstants.PROPERTY_CDN_ENDPOINT;

import java.net.URI;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.hpcloud.objectstorage.lvs.CDNManagement;
import org.jclouds.hpcloud.objectstorage.lvs.HPCloudObjectStorageLasVegasAsyncClient;
import org.jclouds.hpcloud.objectstorage.lvs.HPCloudObjectStorageLasVegasClient;
import org.jclouds.http.RequiresHttp;
import org.jclouds.openstack.OpenStackAuthAsyncClient.AuthenticationResponse;
import org.jclouds.openstack.reference.AuthHeaders;
import org.jclouds.openstack.swift.CommonSwiftAsyncClient;
import org.jclouds.openstack.swift.CommonSwiftClient;
import org.jclouds.openstack.swift.config.BaseSwiftRestClientModule;
import org.jclouds.rest.ConfiguresRestClient;

import com.google.inject.Provides;

/**
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
@RequiresHttp
public class HPCloudObjectStorageLasVegasRestClientModule extends
         BaseSwiftRestClientModule<HPCloudObjectStorageLasVegasClient, HPCloudObjectStorageLasVegasAsyncClient> {

   public HPCloudObjectStorageLasVegasRestClientModule() {
      super(HPCloudObjectStorageLasVegasClient.class, HPCloudObjectStorageLasVegasAsyncClient.class);
   }

   @Provides
   @Singleton
   CommonSwiftClient provideCommonSwiftClient(HPCloudObjectStorageLasVegasClient in) {
      return in;
   }

   @Provides
   @Singleton
   CommonSwiftAsyncClient provideCommonSwiftClient(HPCloudObjectStorageLasVegasAsyncClient in) {
      return in;
   }

   @Provides
   @Singleton
   @CDNManagement
   protected URI provideCDNUrl(AuthenticationResponse response, @Named(PROPERTY_CDN_ENDPOINT) String cdnEndpoint) {
	 
	  if (response.getServices().get(AuthHeaders.CDN_MANAGEMENT_URL) == null) {
	     return URI.create(cdnEndpoint + response.getServices().get(AuthHeaders.STORAGE_URL).getPath());
	  }
	  // Placeholder for when the Object Storage service returns the CDN Management URL in the headers 
      return response.getServices().get(AuthHeaders.CDN_MANAGEMENT_URL);
   }
}
