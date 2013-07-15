/**
 * Copyright (C) 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.palaso.languageforge.client.lex.jsonrpc;

import com.google.gwt.http.client.RequestBuilder.Method;

/**
 * Action definition for command pattern.
 * 
 * @author <a href="mailto:mail@raphaelbauer.com">rEyez</<a>
 * 
 * @param <R>
 *            The result. <A> The object to send while requesting => can be ""
 */
public interface Action<R> {

	
	/**
	 * Hey... to what endpoint do I correspond...?
	 * 
	 * something like "photoservice"
	 * 
	 * @return
	 */
	public String getService();

	/**
	 * Hey... to what endpoint do I correspond...?
	 * 
	 * something like "http://192.168.1.1/api/v1/photoservice"
	 * 
	 * @return
	 */
	public String getFullServiceUrl();

	/**
	 * The name of the method within the realm of the service. Useful for RPC.
	 * 
	 * @return
	 */
	public String getServiceMethod();

	/**
	 * 
	 * @return usually "GET", "DELETE"...
	 */
	public Method getHTTPMethod();

	/**
	 * If an action gets a response => It should be able to map it to the
	 * correct Result class...
	 * 
	 * @param response
	 * @return
	 */
	public JsonRpcResponse<R> decodeResponse(String json);

	/**
	 * this will return a requestmodel which related to current action
	 * @return
	 */
	public RequestModel getRequestModel(int requestId);
	
	/**
	 * this will return a requestmodel in json string format which related to current action
	 * @return
	 */
	public String getRequestModelAsJsonString(int requestId);
	
	/**
	 * If you want to call a service you must supply a method that can translate
	 * your object into a string sent over the wire.
	 * 
	 * @param response
	 * @return
	 */
	public String encodeRequest();
    
    /**
     * If you want to call a service you must supply a method that can translate
     * your object into a string sent over the wire. Typical RPC implementations
     * support multiple parameters so we reflect that here also.
     *
     * @param response
     * @return
     */
    public int getParamCount();
    public String encodeParam(int i);
    
    
    public boolean isBackgroundRequest();

}
