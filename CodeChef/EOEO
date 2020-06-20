/* package codechef;

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
	public static void main (String[] args) throws java.lang.Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		long value,ans;
		while(T-->0){
		    ans = 0;
		    value = Long.parseLong(br.readLine());  // get Current TS Limit
		    if(value%2==0){         // TS is even
		        while(value>0){
		            if((value & 1) == 1)
		                break;
		            else      
		                value>>=1;
		        }
		        ans = value>>1;
		    }else{                  // TS is odd
		        ans = value/2;      
		    }
		    System.out.println(ans);
		}
	}
}
