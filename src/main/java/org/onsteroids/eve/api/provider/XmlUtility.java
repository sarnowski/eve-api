/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author Tobias Sarnowski
 */
public final class XmlUtility {
	private static final Logger LOG = LoggerFactory.getLogger(XmlUtility.class);

	private Node node;

	public XmlUtility(Node node) {
		this.node = Preconditions.checkNotNull(node, "Node");
	}


	public Node getNodeByName(String name) {
		return getNodeByName(name, node);
	}

	public static Node getNodeByName(String name, Node parent) {
		Preconditions.checkNotNull(name, "Name");
		Preconditions.checkNotNull(parent, "Parent");
		NodeList list = parent.getChildNodes();
		for (int n = 0; n < list.getLength(); n++) {
			Node node = list.item(n);
			if (name.equals(node.getNodeName())) {
				return node;
			}
		}
		throw new IllegalArgumentException("Node " + name + " not found");
	}


	public boolean hasNodeWithName(String name) {
		return hasNodeWithName(name, node);
	}

	public static boolean hasNodeWithName(String name, Node parent) {
		Preconditions.checkNotNull(name, "Name");
		Preconditions.checkNotNull(parent, "Parent");
		NodeList list = parent.getChildNodes();
		for (int n = 0; n < list.getLength(); n++) {
			Node node = list.item(n);
			if (name.equals(node.getNodeName())) {
				return true;
			}
		}
		return false;
	}


	public List<Node> getNodesByName(String name) {
		return getNodesByName(name, node);
	}

	public static List<Node> getNodesByName(String name, Node parent) {
		Preconditions.checkNotNull(name, "Name");
		Preconditions.checkNotNull(parent, "Parent");
		List<Node> nodes = Lists.newArrayList();
		NodeList list = parent.getChildNodes();
		for (int n = 0; n < list.getLength(); n++) {
			Node node = list.item(n);
			if (name.equals(node.getNodeName())) {
				nodes.add(node);
			}
		}
		return nodes;
	}


	public String getContentOf(String nodeName) {
		return getContentOf(nodeName, node);
	}

	public static String getContentOf(String nodeName, Node node) {
		Preconditions.checkNotNull(nodeName, "nodeName");
		Preconditions.checkNotNull(node, "node");
		return getNodeByName(nodeName, node).getTextContent();
	}


	public String getAttribute(String name) {
		return getAttribute(name, node);
	}

	public static String getAttribute(String name, Node node) {
		Preconditions.checkNotNull(name, "Name");
		Preconditions.checkNotNull(node, "Node");
		Preconditions.checkNotNull(node.getAttributes().getNamedItem(name), "Attribute not found: " + name);
		return node.getAttributes().getNamedItem(name).getTextContent();
	}
}
