//Simran Gidwani
import java.util.*;
import java.lang.*;

public class DLB implements DictInterface
{
    private Node dictionary;
    
    public DLB()
    {
        dictionary = new Node('/');   
    }
    
    private class Node
    {
        private Node child;
        private Node sibling;
        private char value;
        
        public Node(char val)
        {
            value = val;
            child = null;
            sibling= null;
        }       
    }
    
    public boolean add(String str)
    {
        Node currNode = dictionary;
        boolean letAdd  = false;
        for (int i = 0; i < str.length(); i++)
        {
            char letter = str.charAt(i);
             
            if (!letAdd)
            {
                while (currNode != null)
                {
                    //while the currentnode isnt null add the letter to the node
                    //and make currentnode the child
                    if (currNode.value == letter)
                    {
                        if (currNode.child != null)
                        {
                            currNode = currNode.child;
                            break;
                        }
                        else
                            break;
                    }
                    
                    //if the sibling isnt null make that the current node
                    if (currNode.sibling != null)
                    {
                        currNode = currNode.sibling;
                    }
                    
                    else
                    {
                        letAdd = true;
                        break;
                    }
                    
                }
                
                
                if (letAdd)
                {
                    currNode.sibling = new Node(letter);
                    currNode = currNode.sibling;
                }
            }
            
            else
            {
                currNode.child = new Node(letter);
                currNode = currNode.child;
            }
        }
        
        boolean stringEnd = false;
        if (currNode.child == null)
        {
            currNode.child = new Node('$');
            return true;          
        }
        else
        {
            currNode = currNode.child;
            while (currNode != null)
            {
                if (currNode.value == '$')
                {
                    return false;
                }
                if (currNode.sibling != null)
                {
                    currNode = currNode.sibling;
                }
                else
                    break;       
            }
            
            if (!stringEnd)
            {
                currNode.sibling = new Node('$');
                return true;
            }
                    
        } 
        return false;
    }
    
    public int searchPrefix(StringBuilder s)
    {
        boolean word = false;
        boolean prefix = false;
        Node currNode = dictionary;
        for (int i=0; i < s.length(); i++)
        {
            boolean found = false;
            while (currNode != null)
            {
                //if the letter is found go down to search pointer level
                //for next letter
                if (currNode.value == s.charAt(i))
                {
                    found = true;
                    //letter doesnt have a pointer level, prefix is not found
                    if (currNode.child == null)
                    {
                        return 0;
                    }
                    else
                    {
                        currNode = currNode.child;
                        break;
                    }
                }
                //not that letter, go through the siblings if exist and look
                //for letter
                else
                {
                    if (currNode.sibling != null)
                    {
                        currNode = currNode.sibling;
                    }
                    //if no sibling, prefix not found
                    else
                    {
                        return 0;
                    }
                }
            }
            
            //reach the end of string search for terminator
            if (found && (s.length()-1)==i)
            {
                while (currNode != null)
                {
                    
                    //its a prefix
                    if (currNode.value != '$')
                    {
                        prefix = true;
                    }
                    //its a word
                    else
                        word = true;
                    currNode = currNode.sibling;
                }
            }
            
        }
        
        if (prefix && word)
        {
            return 3;
        }
        else if (word)
        {
            return 2;
        }
        return 1;     
    }
    
    public int searchPrefix(StringBuilder s, int start, int end)
    {
        boolean word = false;
        boolean prefix = false;
        Node currNode = dictionary;
        
        for (int i = start; i <= end; i++)
        {
            boolean found = false;
            
            while (currNode != null)
            {
                if (currNode.value == s.charAt(i))
                {
                    found = true;
                    if (currNode.child == null)
                    {
                        return 0;
                    }
                    else
                    {
                        currNode = currNode.child;
                        break;
                    }                 
                }
                
                else
                {
                    if (currNode.sibling != null)
                    {
                        currNode = currNode.sibling;
                        
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
            
            if (found && end == i)
            {
                while (currNode !=null)
                {
                    if (currNode.value != '$')
                    {
                        prefix = true;
                    }
                    else 
                        word = true;
                    currNode = currNode.sibling;
                }
                
            }
        }
        
        if (prefix && word)
        {
            return 3;
        }
        else if (word)
        {
            return 2;
        }
        return 1;
    }
}

    

