//Simran Gidwani
//CS 1501 Spring 2018

import java.util.*;
import java.lang.*;

public class PHPArray<V> implements Iterable<V>
{;
	PHPArray.Node<V>[] phpArray;
	PHPArray.Node<V>[] tempArray;
	Node<V> start;
	Node<V> end;
	int M; 
	int N; 
	private final int size = 256;
	ArrayList<String> keyList;
	ArrayList<V> valueList;	
	Node<V> iterator;
	
	public PHPArray(int size)
	{
            phpArray = (Node<V>[]) new Node<?>[size];
            tempArray = null;
            M = size;
            N = 0;
            start = null;
            keyList = new ArrayList<String>();
            valueList = new ArrayList<V>();
            end = start;
            iterator = start;
	}

	public static class Pair<V>
	{
            String key;
            V value;

            public Pair(String k, V val)
            {
                key = k;
                value = val;
            }		
	}
        
	private static class Node<V>
	{
            Node<V> next;
            V value;
            String key;
            Node<V> prev;

            public Node(String k, V val)
            {
                value = val;
                next = null;
                key = k;
                prev = null;
            }
	}
	public void put(String k, V val)
	{
            if((double)N/(double)M >= (1.0/2.0))
            {      
                System.out.println(" \t\tSize: " + N + " --resizing array from " + M + " to " + (2*M));
                resize(2*M);                       
            }
            int hashNum = hash(k);
	
            if(phpArray[hashNum] == null)
            {
                phpArray[hashNum] = new Node<V>(k, val);
                if(start == null)
                {
                    start = phpArray[hashNum];
                    end = start;
                    iterator = start;
                }

                else
                {
                    end.next = phpArray[hashNum];
                    phpArray[hashNum].prev = end;
                    end = phpArray[hashNum];
                }
                    keyList.add(k);
                    valueList.add(val);
                    N++;
		}
		else
		{
			
                    if(k.equals(phpArray[hashNum].key))
                    {

                        int index = valueList.indexOf(phpArray[hashNum].value);
                        valueList.remove(index);
                        valueList.add(index, val);
                        phpArray[hashNum].value = val;
                    }
                    else
                    {
                        int index = hashNum;
                        boolean found = false;
                        while(phpArray[index] != null && !found)
                        {
                            index = (index + 1) % M;
                            //Make sure that key isn't already there after probing for it
                            if(phpArray[index] != null && k.equals(phpArray[index].key))
                            {
                                found = true;
                            }
                        }
                        if(found)
                        {
                            int valIndex = valueList.indexOf(phpArray[index].value);
                            valueList.remove(valIndex);
                            valueList.add(valIndex, val);
                            phpArray[index].value = val;
                        }
                        else
                        {
                            //Add the pair at index
                            phpArray[index] = new Node<V>(k, val);
                            end.next = phpArray[index];
                            phpArray[index].prev = end;
                            end = phpArray[index];
                            keyList.add(k);
                            valueList.add(val);
                            N++;
                        }
                    }
		}
	}
	public void put(int k, V val)
	{
            put(String.valueOf(k), val);
	}
	public V get(String k)
	{
	
            int hashNum = hash(k);

            if(phpArray[hashNum] == null)
            {
                    return null;
            }
            else
            {
                //returns value if equal
                if(phpArray[hashNum].key.equals(k))
                {
                        return phpArray[hashNum].value;
                }
                else
                {
                    int index = hashNum;
                    while(phpArray[index] != null)
                    {
                        //Check keys
                        if(phpArray[index].key.equals(k))
                        {
                            return phpArray[index].value;
                        }
                        index = (index + 1) % M;
                    }
                    return null;
                }
            }
	}
	
	public V get(int kee)
	{
            return get(Integer.toString(kee));
	}
	
	public void unset(String k)
	{
            int hashNum = hash(k);

            if(phpArray[hashNum] == null)
            {
                throw new RuntimeException("Not found in array");
            }
            else
            {

                if(k.equals(phpArray[hashNum].key))
                {
                    delete(phpArray[hashNum], hashNum);
                }

                else
                {
                    int index = hashNum;
                    boolean found = false;
                    while(phpArray[index] != null && !found)
                    {
                        System.out.println("\t\tKey " + phpArray[index+2].key + " rehashed..." + "\n");
                        index = (index + 1) % M;
                        if(k.equals(phpArray[index].key))
                                found = true;
                    }
                    if(found)
                        delete(phpArray[index], index);

                    else
                        throw new RuntimeException("Not found in array");
                }
            }
	}
	
	public void unset(int k)
	{
            unset(Integer.toString(k));
	}
	
	private void delete(Node<V> node, int hashNum)
	{
            if(node == start)
            {
                if(node.next == null)
                {
                    start = null;
                    end = null;
                }
                else
                {
                    node.next.prev = null;
                    start = node.next;
                    iterator = start;
                }
            }	
            else if(node == end)
            {
                end = node.prev;
                node.prev.next = null;
            }
            else
            {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            //Remove the key, value and node
            keyList.remove(node.key);
            valueList.remove(node.value);
            node = null;	
            phpArray[hashNum] = null;
            N--;
            //Rehash
            int index = (hashNum + 1) % M;
            while(phpArray[index] != null)
            {
                int newHash = hash(phpArray[index].key);
                //If null, put it there
                if(phpArray[newHash] == null)
                {
                    phpArray[newHash] = phpArray[index];
                    phpArray[index] = null;
                }
                //Else, increase the index til you find an empty spot
                else
                {
                    while(phpArray[newHash] != null)
                    {
                        newHash = (newHash + 1) % M;
                    }
                    phpArray[newHash] = phpArray[index];
                    phpArray[index] = null;
                }
                index = (index + 1) % M;
            }             
	}

	public ArrayList<String> keys()
	{
            return keyList;
	}
	
	public ArrayList<V> values()
	{
            return valueList;
	}

	public void sort()
	{
                //as long as size is greater than one, can call this method
            if(N > 1)
            {
                Node<V>[] sortArray = (Node<V>[]) new Node<?>[N];
                Node<V> sNode = start;
                //Copy to temp array
                for(int b = 0; b < N; b++)
                {
                    sortArray[b] = sNode;
                    sNode = sNode.next;
                }
                quickSort(sortArray, 0, sortArray.length-1);
                Node<V>[] temp = (Node<V>[]) new Node<?>[M];
                Node<V> tempStart;
                Node<V> tempEnd;
                ArrayList<String> tempKeys = new ArrayList<String>();
                ArrayList<V> tempVals = new ArrayList<V>();

                for(int i = 0; i < N; i++)
                {
                    sortArray[i] = new Node<V>(Integer.toString(i), sortArray[i].value);
                    tempKeys.add(sortArray[i].key);
                    tempVals.add(sortArray[i].value);
                }			
                tempStart = sortArray[0];
                tempEnd = sortArray[N - 1];
                for(int j = 0; j < N; j++)
                {
                    if(sortArray[j] == tempStart)
                    {
                        sortArray[j].next = sortArray[j+1];
                    }

                    else if(sortArray[j] == tempEnd)
                    {
                        sortArray[j].prev = sortArray[j-1];
                    }
                    else
                    {
                        sortArray[j].next = sortArray[j+1];
                        sortArray[j].prev = sortArray[j-1];
                    }
                }

                for(int k = 0; k < sortArray.length; k++)
                {
                    int hashNum = hash(sortArray[k].key);
                    if(temp[hashNum] == null)
                    {
                        temp[hashNum] = sortArray[k];
                    }
                    else
                    {
                        int index = hashNum;
                        while(temp[index] != null)
                        {
                            index = (index + 1) % M;
                        }
                        temp[index] = sortArray[k];
                    }
                }
                //Reset
                phpArray = temp;
                start = tempStart;
                end = tempEnd;
                keyList = tempKeys;
                valueList = tempVals;
                iterator = start;
            }
		//If there is only 1 node, just hash that value to a temporary array and
		//make that array the new one for the object
            else if(N == 1)
            {
                Node<V> tempNode = new Node<V>(Integer.toString(0), start.value);
                int hashNum = hash(tempNode.key);
                Node<V>[] temp = (Node<V>[]) new Node<?>[N];
                temp[hashNum] = tempNode;
                phpArray = temp;
                start = tempNode;
                end = tempNode;
                iterator = start;
            }
		
	}
	public void asort()
	{
            if(N > 1)
            {
                Node<V>[] sorter = (Node<V>[]) new Node<?>[N];
                Node<V> sorterNode = start;
                //Copy everything to a tempArray
                for(int b = 0; b < N; b++)
                {
                    sorter[b] = sorterNode;
                    sorterNode = sorterNode.next;
                }
                quickSort(sorter, 0, sorter.length-1);
                ArrayList<String> tempKeys = new ArrayList<String>();
                ArrayList<V> tempVals = new ArrayList<V>();
                Node<V>[] temp = (Node<V>[]) new Node<?>[M];
                Node<V> tempRoot;
                Node<V> tempTail;

                for(int i = 0; i < N; i++)
                {
                    sorter[i] = new Node<V>(sorter[i].key, sorter[i].value);
                    tempKeys.add(sorter[i].key);
                    tempVals.add(sorter[i].value);
                }
                tempRoot = sorter[0];
                tempTail = sorter[N - 1];
                for(int j = 0; j < N; j++)
                {
                    if(sorter[j] == tempRoot)
                    {
                        sorter[j].next = sorter[j+1];
                    }
                    else if(sorter[j] == tempTail)
                    {
                        sorter[j].prev = sorter[j-1];
                    }
                    else
                    {
                        sorter[j].next = sorter[j+1];
                        sorter[j].prev = sorter[j-1];
                    }
                }

                for(int k = 0; k < sorter.length; k++)
                {
                    int hashNum = hash(sorter[k].key);
                    if(temp[hashNum] == null)
                    {
                        temp[hashNum] = sorter[k];
                    }
                    else
                    {
                        int index = hashNum;
                        while(temp[index] != null)
                        {
                            index = (index + 1) % M;
                        }
                        temp[index] = sorter[k];
                    }
                }
                //Reset
                phpArray = temp;
                start = tempRoot;
                end = tempTail;
                keyList = tempKeys;
                valueList = tempVals;
                iterator = start;
		}

		else if(N == 1)
		{
                    Node<V> tempNode = new Node<V>(Integer.toString(0), start.value);
                    //Hash the value into a new array
                    int hashNum = hash(tempNode.key);
                    Node<V>[] temp = (Node<V>[]) new Node<?>[N];
                    temp[hashNum] = tempNode;
                    phpArray = temp;
                    start = tempNode;
                    end = tempNode;
                    iterator = start;
		}
	}
	//Iterator
	public Iterator<V> iterator()
	{
            return new NodeIterator();
	}
	private class NodeIterator implements Iterator<V>
	{
            private Node<V> nextNode;
            private String currKey;
            private NodeIterator()
            {
                nextNode = start;
                if(start != null)
                    currKey = start.key;
            }
            public boolean hasNext()
            {
                return nextNode!= null;
            }
            public V next()
            {
                if(hasNext())
                {
                    Node<V> returnNode = nextNode;
                    currKey = returnNode.key;
                    nextNode = nextNode.next;
                    return returnNode.value;
                }
                else
                    return null;
            }
            public void remove()
            {
                throw new UnsupportedOperationException("remove() is not supported");
            }
	}
	//Return another Pair from the linked list
        public Pair<V> each()
        {
            if(iterator == null)
            {
                return null;
            }
            else
            {   
                Node<V> retNode = iterator;
                iterator = iterator.next;
                return new Pair<V>(retNode.key, retNode.value);
            }
        }

	public void reset()
	{
            iterator = start;
	}

	private int hash(String key)
	{
            int hashNum = 0;
            for(int i = 0; i < key.length(); i++)
            {
                hashNum = (key.charAt(i) + hashNum * size) % M;
            }
            hashNum = Math.abs(hashNum);
            return hashNum;
	}

	private void resize(int newSize)
	{
            int firstM = M;
            M = newSize;
            tempArray = (Node<V>[]) new Node<?>[newSize];

            for(int i = 0; i < firstM; i++)
            {
                if(phpArray[i]!= null)
                {			
                    int hashNum = hash(phpArray[i].key);				
                    if(tempArray[hashNum] == null)
                    {
                        tempArray[hashNum] = phpArray[i];
                    }

                    else
                    {
                        int index = hashNum;
                        while(tempArray[index] != null)
                        {
                            index = (index + 1) % newSize;
                        }

                        tempArray[index] = phpArray[i];	
                    }
                }
            }
            phpArray= tempArray;
            tempArray = null;               
	}
	//Show table contents of the array
	public void showTable()
	{
            String k;
            V value;
            System.out.println("\t Raw Hash Table Contents: ");
            for(int i = 0; i < M; i++)
            {
                if(phpArray[i] == null)
                {
                    System.out.println(i + ": null");
                }
                else
                {
                    k = phpArray[i].key;
                    value = (V)phpArray[i].value;
                    System.out.println(i + ": Key: " + k + " Value: " + value);
                }
            }
	}
	//Return the length
	public int length()
	{
            return N;
	}
	
	public PHPArray<String> array_flip()
	{
            PHPArray<String> newArray = new PHPArray<String>(M);
            Node<V> node = start;
            while(node != null)
            {
                newArray.put((String)node.value, node.key);
                node = node.next;
            }
            return newArray;
	}

	private void quickSort(Node<V>[] array, int min, int max)
	{
            if(min >= max)
                return;

            int l = min;
            int h = max;
            int piv = (max);
            while(l <= h)
            {
                while(((Comparable)array[h].value).compareTo((Comparable)array[piv].value) > 0)
                {
                    h--;
                }
                while(((Comparable)array[l].value).compareTo((Comparable)array[piv].value) < 0)
                {
                    l++;
                }
                if(l <= h)
                {
                    Node<V> temp = array[l];
                    array[l] = array[h];
                    array[h] = temp;
                    l++;
                    h--;
                }
            }

            if(min < h)
                    quickSort(array, min, h);
            if(l < max)
                    quickSort(array, l, max);
	}
}