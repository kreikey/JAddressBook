package rkreikebaumaddressbook;

public class Node<T extends Comparable <? super T>> implements Comparable<Node<T>> {
	public T content;
	public Node<T> next;
	public Node<T> prev;

	public Node() {
		super();
	}

	public int compareTo(Node<T> otherNode) {
		return content.compareTo(otherNode.content);
	}
}