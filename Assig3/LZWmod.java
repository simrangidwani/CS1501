//Simran Gidwani
//CS 1501 Summer 2018
public class LZWmod {
    private static int W = 9;         // codeword width         
    private static final int R = 256;        // number of input chars
    private static int L = (int) Math.pow(2, W);       // number of codewords = 2^W
    private static int L2 = (int) Math.pow(2, 16);
    public static boolean reset = false;

    public static void compress() {    
        StringBuilder pattern; //sbInput
        StringBuilder input = new StringBuilder();   //t
        TSTmod<Integer> st = new TSTmod<Integer>();
        for (int i = 0; i < R; i++)
            st.put(new StringBuilder().append((char) i), i);
        int code = R+1;  // R is codeword for EOF
        
        pattern = charList();
        
        while (pattern != null) {
            StringBuilder s = st.longestPrefixOf(pattern);  // Find max prefix match s.
            
            while (s.length() == pattern.length())
            {
                input = charList();
                pattern.append(input);
                s = st.longestPrefixOf(pattern);
            }
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            if (input != null && code < L)
            {
                st.put(pattern, code++);
                if (code == L && W < 16)
                {
                    W++;
                    L = (int)Math.pow(2, W);                 
                }
                
                //else if you reach max codewords and max length, reset dictionary
                else if(W == 16 && code == L && reset)
                {
                    System.out.println("Resetting");
                    resetDict();
                }
            }
            pattern = input;
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
        String[] st = new String[L2];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i+1 < L2) st[i++] = val + s.charAt(0);
            if (i+1 == L && W < 16)
            {
                W++;
                L = (int)Math.pow(2, W);
            }
           
            if (i == L2)
            {
                //if i reaches max num of codewords and user
                //specifies to reset
                if (reset)
                {
                    resetDict();
                }
                else
                {
                    BinaryStdOut.write(val);
                    codeword = BinaryStdIn.readInt(W);
                    while (codeword != R)
                    {
                        BinaryStdOut.write(st[codeword]);
                        codeword = BinaryStdIn.readInt(W);
                    }
                    break;
                }
                
            }
            val = s;
        }
        BinaryStdOut.close();
    }


    private static StringBuilder charList()
    {
        char input;
        StringBuilder list = new StringBuilder();
        try{
        input = BinaryStdIn.readChar();
        list.append((char) input);
        } catch (Exception e){
            list = null;
        }
        return list;
    }
    
    private static void resetDict()
    {
        System.out.println("resetting dictionary");
        
    }

    public static void main(String[] args) {
        System.out.println("XXXXX");
        if (args[0].equals("-"))
        { 
            if (args[1].equals("r"))
            {
                reset = true;                
            }
            else if (args[1].equals("n"))
            {
                reset = false;               
            }
            compress();
            
        }
        else if (args[0].equals("+"))
        {
            expand();
        }
        else
            throw new RuntimeException("Illegal command line argument");
                    
        }
        
        
        
//        if      (args[0].equals("-")) compress();
//        else if (args[0].equals("+")) expand();
//        else throw new RuntimeException("Illegal command line argument");
//        
//        if (args[1].equals("r"))
//        {
//            reset = true;           
//            
//        }
//        else if (args[1].equals("n"))
//            reset = false;
        
    }

