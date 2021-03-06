package gen.base36.org;

/* Coded by: 
 * 	Gabriel E. Nieves
 * 
 * Date Created:
 * 	06/30/2014
 * 
 * Last Modified:
 * 	07/03/2014
 * 
 * Description:
 * 	This is an example to code to convert an integer to a base36 string value.
 * 	It should also convert a base36 string value back to the integer equivalent.
 * 	These functions/functionality should later be converted to a class, so that
 * 	it may be used in any other code. Also as a requirement the last digit of our
 * 	base 36 string should be base 35; this will eliminate the �E� character from 
 *  our conversion.
 * 
 * License:
 * 	...
 * 
 */

import java.util.ArrayList;

public class main {

	public final static int MAX_digits = 5;
	public final static int base = 36;
	public static void main(String[] args) {
		
		//println(intToChar36(5));
	}
	
	

	public static int base36ToInt(String str){//passed test for base36!
		
		
		//make sure that each case is upper 
		str = str.toUpperCase();
		//This function will convert a base36 string to it's int equivalent 
		int result = 0;
		double frontVal =0;
		double lastVal = 0;
		int size = str.length();
		boolean started = false;
		
		
		frontVal = char36ToInt(str.charAt(0));
		for(int i = 0; i < size; i++){
			//TODO: log this println("frontVal = " + frontVal);
			
			if(i+1 < size && (started || frontVal >0.0) ){
				lastVal = char36ToInt(str.charAt(i+1));
				//TODO: log this println("lastVal = " + lastVal);
				//should contain the temp result:
				frontVal = (frontVal + (lastVal/((double)base)))*((double)base);
				//TODO: log this println("new frontVal = " + frontVal);
			}else if(!started){
				if(frontVal == 0.0 && i+1 < size){
					lastVal = char36ToInt(str.charAt(i+1));
					if(lastVal > 0.0){
						frontVal = lastVal;
						started = true;
					}
						
				}
			}
			
		}
		result = (int)Math.round(frontVal);
		return result;
	}
	
	public static String intToBase36(int n){//passed test for base36!
		//Converts an int value to a base36 string
		String result = "";
		ArrayList<String> list = new ArrayList<String>();
		int frontVal =0;
		int lastVal =0;
		int val = n;//preserve input
		frontVal = val;
		
		while(val >= base){
			
			//val is greater than the base; so we need to continue loop until it is not
			
			frontVal = val/base;//store the front part
			lastVal = val%base;//store the last part
			//TODO log thisprintln("val = " + val + "\tfrontVal = " +frontVal + "\tlastVal = " + lastVal);
			list.add(Character.toString(intToChar36(lastVal)));
			val = frontVal;
			
		}
		
		//add last base36 digit
		if(frontVal > 0)
			list.add(Character.toString(intToChar36(frontVal)));
		
		//fill the rest of the string with 0s
		for(int i = list.size(); i < MAX_digits; i++)
			list.add("0");
		
		//convert list to string, in order.
		for(int i =list.size()-1; i >= 0 ; i--)
			result += list.get(i);
		
		return result;
	}
	
	public static char intToChar36(int n){//passed test!
		//Converts the integer value to the equivalent base 36 character
		char result = (char)0;
		
		if(n >=0 && n <= 9)
			result = (char)(48+n);
		else if(n > 9 && n < base){
			result = (char)(65-10+n);
		}
		return result;
	}
	public static char intToChar35(int n){//passed test!
		//Same as intToChar36; however it removes the 'E' char
		//Converts the integer value to the equivalent base 36 character
		char result = (char)0;
		
		if(n >=0 && n <= 9)
			result = (char)(48+n);
		else if(n > 9 && n < 14){
			result = (char)(65-10+n);
		}else if(n > 13 && n < 36)
			result = (char)(65-10+n+1);
		return result;
	}
	
	public static int char36ToInt(char c){//passed test!
		//Converts a base 36 character into its equivalent value in integer
		int result = 0;
		if(Character.isDigit(c))
			result = (int)c - 48;
		else if(Character.isAlphabetic(c)){
			//-10 because 0-9 are our 1st 10 in base36
			if((int)c <= 90){
				result = (int)c - (65-10);
				
			}
		}
		return result;
	}
	public static int char35ToInt(char c){//passed test!
		int result = char36ToInt(c); 
		if(result > 13)
			result--;
		return result;
			
	}
	public static void print(Object obj){
		System.out.print(obj);
	}
	public static void println(Object obj){
		System.out.println(obj);
	}
}
