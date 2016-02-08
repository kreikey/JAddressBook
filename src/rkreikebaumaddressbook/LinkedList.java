package rkreikebaumaddressbook;

import java.util.*;

// We should refactor this linked list to be 0-indexed to be more conventional and more clear.
// Could we make it work as a 1-indexed list? Probably. But I don't think it's worth it.
// The only disadvantage to making it 0-indexed is that we would have to modify our ContactManager, 
// but this is easy. That's the whole reason why we have a linked list as a separate layer of abstraction.
// A more correct design, of course, would be to have a generic linked list that can operate on a generic node
// that implements the comparable interface. This generic node would, in turn, contain an object that implements the comparable interface.

public class LinkedList <T extends Comparable<? super T>> {
	private int length;
	private int index;
	private Node<T> cur;
	private Node<T> first;

	public LinkedList() {
		super();
		this.length = 0;
		this.index = -1;
	}
	
	public LinkedList(Node<T> first, int length) {
		super();
		if (first == null)
		{
			this.length = 0;
			this.index = -1;
		}
		else
		{
			this.length = 1;
			this.index = 0;
		}
		if (length > 1)
			this.length = length;
		this.first = first;
		this.cur = first;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getIndex() {
		return index;
	}

	public void insert(Node<T> n) {
		if (first == null)
		{
			first = n;
			first.next = first;
			first.prev = first;
			cur = first;
		}
		else
		{
			n.next = cur.next;
			n.prev = cur;
			n.next.prev = n;
			cur.next = n;
			cur = cur.next;
		}
		index++;
		length++;
	}

	public Node<T> deleteCur() {
		Node<T> t = cur;
		
		if (cur != null)
		{
			cur.next.prev = cur.prev;
			cur.prev.next = cur.next;
			length--;
			if (cur.next == cur)
			{
				cur = null;
				first = null;
				index--;
			} else {
				if (cur.next == first)
				{
					cur = cur.prev;
					index--;
				} else {
					if (cur == first)
					{
						cur = cur.next;
						first = cur;
					} else
						cur = cur.next;
				}
			}

			t.next = null;
			t.prev = null;
			return t;
		} else 
			return null;
	}

	public Node<T> getCur() {
		return cur;
	}

	public Node<T> getPrev() {
		if (cur != null)
		{
			cur = cur.prev;
			index--;
			if (index < 0)
				index += length;
			return cur;
		}
		else 
			return null;
	}

	public Node<T> getNext() {
		if (cur != null)
		{
			cur = cur.next;
			index++;
			if (index == length)
				index = 0;
			return cur;
		}
		else 
			return null;
	}

	public Node<T> jumpAhead() {
		for (int x = 0; x < 10; x++)
			this.getNext();
		return cur;
	}

	public Node<T> jumpBack() {
		for (int x = 0; x < 10; x++)
			this.getPrev();
		return cur;
	}
	
	public Node<T> getFirst() {
		cur = first;
		if (cur == null)
			index = -1;
		else
			index = 0;
		return cur;
	}

	public boolean atFirst() {
		return (cur == first);
	}

	public boolean isEmpty() {
		if (length == 0)
			return true;
		else
			return false;
	}

	public void deleteAll() {
		while (length != 0)
			this.deleteCur();
	}
	
	public void sort() {
		this.rMergeSort();
//		this.quickSort();
//		this.qSort();		
//		this.insertionSort();
	}
	
	private void extract(Node<T> n) {
		n.prev.next = n.next;
		n.next.prev = n.prev;
	}
	
	private void insertAfter(Node<T> two, Node<T> one) {
		two.prev = one;
		two.next = one.next;
		one.next = two;
		two.next.prev = two;
	}

	private void insertBefore(Node<T> two, Node<T> one) {
		two.next = one;
		two.prev = one.prev;
		one.prev = two;
		two.prev.next = two;
	}

	private void swap(Node<T> a, Node<T> b) {
		Node<T> t;
		
		if (b.next == a && a.next == b) {
			return;
		}
		
		if (a.prev == b) {
			t = a;
			a = b;
			b= t;
		}
		
		t = a.prev;
		extract(a);
		insertAfter(a, b);
		extract(b);
		insertAfter(b, t);
	}
		
	private LinkedList<T> getLeftHalf() {
		return new LinkedList<T>(this.first, (this.length / 2));
	}
	
	private LinkedList<T> getRightHalf() {
		this.getFirst();
		int count = 0;
		int mid = this.length / 2;
		while (count < mid) {
			this.getNext();
			count++;
		}
		return new LinkedList<T>(this.cur, (this.length - mid));
	}
	
	private void merge(LinkedList<T> rightList) {
		Node<T> rightTemp;
		
		while (this.index < this.length) {
			while ((rightList.cur.compareTo(this.cur) < 0) && (rightList.index < rightList.length)) {
				rightTemp = rightList.cur;
				rightList.cur = rightList.cur.next;
				rightList.index++;
				extract(rightTemp);
				insertBefore(rightTemp, this.cur);
				if (this.cur == this.first)
					this.first = rightTemp;
			}
			this.cur = this.cur.next;
			this.index++;
		}
		this.length += rightList.length;		// for semantic correctness, though it doesn't really matter
	}
	
	private void rMergeSort() {
//		if (this.length < 2)
//			return;
		if (this.length < 17) {
			this.insertionSort();
			return;
		}
		
		LinkedList<T> leftList = this.getLeftHalf();
		LinkedList<T> rightList = this.getRightHalf();
		leftList.rMergeSort();
		rightList.rMergeSort();
		leftList.merge(rightList);
		this.first = leftList.first;
		this.cur = this.first;	
		this.index = 0;
	}
	
	private void iMergeSort() {
		ArrayList<Node<T>> frontNodes = new ArrayList<Node<T>>(this.length);
		ArrayList<Node<T>> frontNodesMerged;
		LinkedList<T> leftList, rightList;
		int sliceSize = 1;
		int lastSize = sliceSize;
		boolean wasOdd = false;
		
		this.getFirst();
		for (int x = 0; x < this.length; x++) {					// do the initial indexing
			frontNodes.add(this.cur);
			this.getNext();
		}
		
		for (sliceSize = 1; sliceSize < this.length; sliceSize *= 2) {		// change the slice size each time we do a set of merges			
			frontNodesMerged = new ArrayList<Node<T>>((frontNodes.size() / 2) + 1);
			// Iterate through frontNodes and merge the sublists by twos
			for (int i = 0; i < (frontNodes.size() - 1); i += 2) {
				leftList = new LinkedList<T>(frontNodes.get(i), sliceSize);
				if ((i + 1) == (frontNodes.size() - 1))
					rightList = new LinkedList<T>(frontNodes.get(i + 1), lastSize);		
				else
					rightList = new LinkedList<T>(frontNodes.get(i + 1), sliceSize);		
				leftList.merge(rightList);
				frontNodesMerged.add(leftList.first);
			}
			if ((frontNodes.size() % 2) != 0) {
				frontNodesMerged.add(frontNodes.get(frontNodes.size() - 1));
				wasOdd = true;
			} else {
				if (wasOdd == true)
					lastSize += sliceSize;
			}
			if (wasOdd == false) {
				lastSize *= 2;
			}
			frontNodes = frontNodesMerged;
		}
		this.first = frontNodes.get(0);
		this.cur = this.first;
		this.index = 0;
	}
	
	private void quickSort() {
		quickSortInternal(first.prev);
	}
	
	private void quickSortInternal(Node<T> end) {
		Node<T> pivot, curLeft, curRight, tooBig, tooSmall, temp;
		LinkedList<T> left;
		LinkedList<T> right;
		int leftLen = 1;			// make sure we do lenghts correctly
		int rightLen = 0;			// 0 for left and 1 for right is almost certainly the right thing for the general case.
		int leftNdx = 1;
		int rightNdx = this.length - 1;
		int pivNdx = this.length / 2;
		
//		if (this.length < 2) {
//			return;
//		}

		if (this.length < 17) {
			this.insertionSort();
			return;
		}
		
		pivot = this.first;	
		for (int i = 0; i < pivNdx; i++) {
			pivot = pivot.next;
		}
		if (end == pivot)
			end = this.first;
		swap(pivot, this.first);
		this.first = pivot;
		
		curRight = end;
		curLeft = this.first.next;
				
		while (leftNdx < rightNdx) {
			while ((curLeft.compareTo(pivot) <= 0) && (leftNdx < rightNdx)) {
				curLeft = curLeft.next;	
				leftLen++;
				leftNdx++;
			}
			while (curRight.compareTo(pivot) > 0 && (rightNdx > 0)) {
				curRight = curRight.prev;
				rightLen++;
				rightNdx--;
			}
			if (leftNdx < rightNdx) {
				tooBig = curLeft;
				tooSmall = curRight;
				// don't they pass each other?
				swap(tooBig, tooSmall);
				curLeft = tooSmall;
				curRight = tooBig;
				if (end == tooSmall)
					end = tooBig;
			}
		}
		if (curRight.compareTo(pivot) < 0) {
			swap(pivot, curRight);
			temp = curRight;
			if (curRight == curLeft)
				curLeft = pivot;
			if (curRight == end)
				end = pivot;
			curRight = pivot;
			pivot = temp;
		}
		// keep track of end
		// how do we properly split into left and right when our list is of size 2?
		
		if (rightNdx == leftNdx) {
//			System.out.println("curRight not less than curLeft.");			
			rightNdx--;
			curRight = curRight.prev;
		}
		
		this.first = pivot;
		
		left = new LinkedList<T>(this.first, leftLen);
		right = new LinkedList<T>(curLeft, rightLen);

		// Sort the sublists
		left.quickSortInternal(curRight);	// this choice is not exact. It seems we include the pivot.
		right.quickSortInternal(end);			// But it works, and I think it's the most correct way to get it to work.

		this.first = left.first;
		this.cur = this.first;
		this.index = 0;
	}
	
	private void qSort() {
		// write a better quickSort-like function that's optimized for linked lists.
		Node<T> pivot, temp;
		LinkedList<T> left;
		LinkedList<T> right;
		int pivNdx = this.length / 2, leftLen = 0, rightLen = 0;
		
//		if (this.length < 2) 
//			return;

		if (this.length < 17) {
			this.insertionSort();
			return;
		}
		
		pivot = this.first;
		for (int i = 0; i < pivNdx; i++)
			pivot = pivot.next;
		swap(pivot, this.first);
		this.first = pivot;
		this.getFirst();
		
		this.index++;
		this.cur = this.cur.next;
		
		while (this.index < this.length) {
			if (this.cur.compareTo(pivot) < 0) {
				leftLen++;
				temp = this.cur;
				this.index++;
				this.cur = this.cur.next;
				extract(temp);
				insertBefore(temp, pivot);
				if (pivot == this.first) {
					this.first = temp;
				}
			} else {
				rightLen++;
				this.index++;
				this.cur = this.cur.next;
			}
		}
		
		left = new LinkedList<T>(this.first, leftLen);
		
		if (rightLen == 0)
			right = new LinkedList<T>(pivot, rightLen);
		else
			right = new LinkedList<T>(pivot.next, rightLen);
		
		left.qSort();
		right.qSort();
		
		this.first = left.first;
		this.cur = this.first;		
		this.index = 0;
	}
	
	private void insertionSort() {
		Node<T> insPoint, temp;
		
		this.getFirst();
		this.getNext();
		// We could possibly optimize this by eliminating function calls in the loop.
		// To do so, we would replace getNext with it's main components, and change our while loop.
		while (this.index < this.length) {
			insPoint = this.cur.prev;
			
			while (insPoint != this.first.prev && this.cur.compareTo(insPoint) < 0) {
				insPoint = insPoint.prev;
			}
			
			if (this.cur == insPoint) {
				this.first = cur;
				this.index = 0;
				break;
			}
			
			if (this.cur.prev != insPoint) {
				temp = this.cur;
				this.index++;
				this.cur = this.cur.next;
				extract(temp);
				insertAfter(temp, insPoint);

				if (temp.next == this.first) {
					this.first = temp;
				}
			} else {
				this.index++;
				this.cur = this.cur.next;
			}
		}
		
		this.cur = this.first;
		this.index = 0;
	}	
	
	public boolean confirmSort() {
		this.getFirst();
		Node<T> temp = cur;
		while (getNext() != temp) {
			if (this.cur.compareTo(this.cur.prev) <= 0) {
				return false;
			}
		}
		this.getFirst();
		return true;
	}
}