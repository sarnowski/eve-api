/**
 * Copyright 2010 Tobias Sarnowski
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

/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiListResult;
import com.eveonline.api.exceptions.ApiException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.onsteroids.eve.api.InternalApiException;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Tobias Sarnowski
 */
public abstract class SerializableApiListResult<S extends SerializableApiResult> extends SerializableApiResult implements ApiListResult<S> {
	// because java doesn't support multi inheritance we have to choose between SerializableApiResult and ForwardingList
	// from google collections. We want to be immutable and using all features of ApiResult which can change quite often
	// so we implement the forwarding, immutable list ourself.

	private static final Logger LOG = LoggerFactory.getLogger(SerializableApiListResult.class);

	private List<S> results;


	public SerializableApiListResult() {
	}

	public SerializableApiListResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
		processCoreResult(xmlApiResult, xmlResult);
	}

	@Override
	public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
		List<S> results = Lists.newArrayList();

		Class<? extends S> definition = getRowDefinition();

		Node rowset = XmlUtility.getNodeByName("rowset", xmlResult);
		List<Node> rows = XmlUtility.getNodesByName("row", rowset);
		for (Node row: rows) {

			S result;
			try {
				result = definition.newInstance();
			} catch (InstantiationException e) {
				throw new InternalApiException(e);
			} catch (IllegalAccessException e) {
				throw new InternalApiException(e);
			}

			result.processCoreResult(xmlApiResult, row);

			results.add(result);
		}

		// we cannot change the servers values, so we are immutable!
		this.results = ImmutableList.copyOf(results);
	}

	/**
	 * @return the class which defines the implementation
	 */
	public abstract Class<? extends S> getRowDefinition();


	@Override
	public int size() {
		return results.size();
	}

	@Override
	public boolean isEmpty() {
		return results.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return results.contains(o);
	}

	@Override
	public Iterator<S> iterator() {
		return results.iterator();
	}

	@Override
	public Object[] toArray() {
		return results.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return results.toArray(a);
	}

	@Override
	public boolean add(S t) {
		return results.add(t);
	}

	@Override
	public boolean remove(Object o) {
		return results.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return results.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends S> c) {
		return results.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends S> c) {
		return results.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return results.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return results.retainAll(c);
	}

	@Override
	public void clear() {
		results.clear();
	}

	@Override
	public S get(int index) {
		return results.get(index);
	}

	@Override
	public S set(int index, S element) {
		return results.set(index, element);
	}

	@Override
	public void add(int index, S element) {
		results.add(index, element);
	}

	@Override
	public S remove(int index) {
		return results.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return results.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return results.lastIndexOf(o);
	}

	@Override
	public ListIterator<S> listIterator() {
		return results.listIterator();
	}

	@Override
	public ListIterator<S> listIterator(int index) {
		return results.listIterator(index);
	}

	@Override
	public List<S> subList(int fromIndex, int toIndex) {
		return results.subList(fromIndex, toIndex);
	}
}
