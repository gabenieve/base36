package org.base36.gen;

import java.util.ArrayList;

import org.base36.gen.test.mainTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base36 {

	private String base36;
	public final int MAX_digits = 5;
	public final int base = 36;
	public final int MAX_INT = (int) Math.pow((double)base, (double)MAX_digits-1)*35-1;
	
	final Logger logger = LoggerFactory.getLogger(mainTest.class);
	
	public Base36(){
		base36 = "";
		for(int i = 0; i < MAX_digits; i++){
			base36 += "0";
		}
	}
	public String getBase36(){
		return this.base36;
	}
	public void setBase36(long n){
		//This will convert an long value to a base36
	}
	
	public int base36ToInt(String str){//passed test for base36!
		
		logDebug("Start \'base36ToInt(String str)\'");
		//make sure that each case is upper 
		str = str.toUpperCase();
		int size = str.length();
		
		if(str.startsWith("E")){//for now the string must not start with E, does not matter if size is 1.
			throw new IllegalArgumentException("Base 36 can't start with \'E\'!");
		}
		//This function will convert a base36 string to it's int equivalent 
		int result = 0;
		double frontVal =0;
		double lastVal = 0;
		
		boolean started = false;
		
		
		frontVal = char36ToInt(str.charAt(0));
		
		for(int i = 0; i < size; i++){
			 
			logDebug("["+i+"] frontVal = " + frontVal +"\n");
			
			if(!Character.isLetterOrDigit(str.charAt(i))){
				throw new IllegalArgumentException("Base 36 has invalid character (\'"+str.charAt(i)+"\').");
			}
			if(frontVal > 13 && i == 0 && i+1 < size){
				frontVal = char35ToInt(str.charAt(i));
				lastVal = char36ToInt(str.charAt(i+1));
				
				frontVal = (frontVal + (lastVal/((double)base)))*((double)base);
				logDebug("["+i+"] (frontVal + (lastVal/base))*(base) = " + frontVal);
				started =true;
			}else if(i+1 < size && (started || frontVal >0.0) ){
				lastVal = char36ToInt(str.charAt(i+1));
				logDebug("["+i+"] lastVal = " + lastVal);
				
				//should contain the temp result:
				frontVal = (frontVal + (lastVal/((double)base)))*((double)base);
				 
				logDebug("["+i+"] (frontVal + (lastVal/base))*(base) = " + frontVal);
				
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
		logDebug("result = " + result);
		logDebug("End of \'base36ToInt(String str)\'");
		logInfo("Converted base36(" +str+") to base10(" + result+")");
		return result;
	}

	public String intToBase36(int n){//passed test for base36!
		//Converts an int value to a base36 string
		String result = "";
		ArrayList<String> list = new ArrayList<String>();
		int frontVal =0;
		int lastVal =0;
		int val = n;//preserve input
		frontVal = val;
		boolean got1st = false;
		
		while(val >= base){
			
			//val is greater than the base; so we need to continue loop until it is not
			
			frontVal = val/base;//store the front part
			
			lastVal = val%base;//store the last part
			//TODO log this
			logDebug("val = " + val + "\tfrontVal = " +frontVal + "\tlastVal = " + lastVal);
			//if(!got1st && lastVal > 13 ){
			//	list.add(Character.toString(intToChar35(lastVal)));
			//	got1st = true;
			//}else
				list.add(Character.toString(intToChar36(lastVal)));
			val = frontVal;
			
		}
		
		//add last base36 digit
		if(frontVal > 0)
			list.add(Character.toString(intToChar35(frontVal)));
		
		//fill the rest of the string with 0s
		for(int i = list.size(); i < MAX_digits; i++)
			list.add("0");
		
		//convert list to string, in order.
		for(int i =list.size()-1; i >= 0 ; i--)
			result += list.get(i);
		logInfo("Converted base10(" +n+") to base36(" + result + ")");
		return result;
	}
	//Functions to simplify conversions: 
	public char intToChar36(int n){
		//Converts the integer value to the equivalent base 36 character
		if(n >= base || n < 0){
			throw new IllegalArgumentException("Must Enter a positive value that is less than "+base);
		}
		
		char result = (char)0;
		
		if(n >=0 && n <= 9)
			result = (char)(48+n);
		else if(n > 9 && n < base){
			result = (char)(65-10+n);
		}
		
		return result;
	}
	public char intToChar35(int n){
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
	
	public int char36ToInt(char c){
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
	public int char35ToInt(char c){
		int result = char36ToInt(c); 
		if(result > 13)
			result--;
		return result;
			
	}
	 //used to make things easy
    private void logInfo(Object obj){
    	logger.info(obj.toString());
    }
    private void logError(Object obj){
    	logger.error(obj.toString());
    }
    private void logWarrning(Object obj){
    	logger.warn(obj.toString());
    }
    private void logDebug(Object obj){
    	logger.debug(obj.toString());
    }
    private void println(Object obj){
    	System.out.println(obj.toString() + "\n");
    }
}
