
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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Dispatcher on the client. Can be replaced by an arbitrary implementation.
 * eg.:
 * <ul>
 * <li>DefaultDispatcher</li>
 * <li>CachingDispatcher</li>
 * <li>BatchingDispatcher</li>
 * </ul>
 * 
 * @author <a href="mailto:mail@raphaelbauer.com">rEyez</<a>
 * 
 */
public interface DispatchAsync {

	<A extends Action<R>, R> void execute(A action, AsyncCallback<R> callback);

}
