package org.base36.gen.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.*;
import org.apache.log4j.PropertyConfigurator.*;
import org.apache.log4j.config.*;
//PropertyConfigurator.configure("log4j.properties");
import org.base36.gen.main;

public class mainTest {
//last tested int 11144144
	
	
	int max = (int) Math.pow((double)main.base, (double)main.MAX_digits-1)*35-1;
	Logger logger = LoggerFactory.getLogger(mainTest.class);
	//configure("log4j.properties");
	
	//NOTE: 23514624 in base36 = E0000; however since 'E' is reserved in the 1st char, 
	// we need to use base 35, which is F0000
	@Test
	public void test() {
		//Start logging to MainTest.log
		logToMainTest();
		Date startTime = new Date();//System.currentTimeMillis()/1000.0;
		
		logInfo("\tTest Started - " + startTime);
		
		/*int input = 23514623;
		String str = test_intToBase36(input);
		int val = test_base36ToInt(str);
		str = test_intToBase36(input+1);
		val = test_base36ToInt(str);*/
		//assertEquals("revert base 36 to int: ", input, val);
		//test_ConvertandRevert_base35();
		//logDebug("max = "+max);
		//test_base36ToInt("D0000");
		//base36ToInt_throw_IllegalArgumentException("E0000");
		
		//test_base36ToInt("F0000");
		//test_base36ToInt("G0000");
		
		testAllfromMinToMax(58786558, max+3);
		
		
		Date endTime = new Date();//System.currentTimeMillis()/1000.0;
		logInfo("Total Run time = "+ (endTime.getTime()/1000.0-startTime.getTime()/1000.0 +" sec"));
		logInfo("\tTest Ended - " + endTime+"\n");
		
	}
	public void RunAllTests(){
		base36ToInt_RunAllCharsForThrows();
	}
	public void logToMainTest(){
		try{
			Properties props = new Properties();
			props.load(new FileInputStream("src\\org\\base36\\gen\\test\\log4j.properties"));
			PropertyConfigurator.configure(props);
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
			//do nothing....
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
    public boolean base36ToInt_throw_IllegalArgumentException(String str){
    	boolean passed = true;
    	String message = "";
    	
    	//Make sure to throw exception if base36 contains an 'E'
    	try{main.base36ToInt(str);}catch(IllegalArgumentException e){passed = false;message = e.toString();}
    	
    	if(!message.isEmpty())
    		logError(message);
    	
    	return passed;
    }
    public void base36ToInt_RunAllCharsForThrows(){
    	//runs a base36ToInt with ASCII chars from 0 to 127.
    	//this should only throw exceptions for the invalid characters
    	
    	logDebug("Starting \'base36ToInt_RunAllCharsForThrows()\' Test...");
    	for(int i = 0; i< 128; i++){
    		base36ToInt_throw_IllegalArgumentException(Character.toString((char)i));
    	}
    	logDebug("End of \'base36ToInt_RunAllCharsForThrows()\' Test");
    }
	public void test_ConvertandRevert_base35(){
		char c = '!';
		int n = 0;
		
		for(int i = 0; i <38; i++){
			c = main.intToChar35(i);
			n = main.char35ToInt(c);
			logInfo("int input = "+i+"\tbase36 char = " + c +"\t intVal = " + n);
			if(i < 35)
				assertEquals("Convert \'"+i+"\' to base 35 and back to int ", i, n );
			else
				assertEquals("Convert \'"+i+"\' to base 35 and back to int ", 0, n );
		}
	}
	//TEST int to char base 36:
	public char test_intToChar35(int n){
		//same as int to char base 36; however E must be removed
		logDebug("Starting \'test_intToChar35(int n)\'");
		char c = main.intToChar35(n);
		logDebug(n + "\tto charBase35 = \t" + c);
		logDebug("End of \'test_intToChar35(int n)\'");
		return c;
	}
	public int  test_char35ToInt(char c){
		logDebug("Starting \'test_char35ToInt(char c)\'");
		
		int result = main.char35ToInt(c);
		logDebug(c + "\tcharBase35 to int = \t" + result);
		logDebug("End of \'test_intToChar35(int n)\'");
		
		return result;
	}
	//TEST int to char base 36:
	public void test_intToChar36(){
    	logDebug("Starting \'test_intToChar36()\'");
		for(int i =0; i<=39; i++){
			if(i >= 0 && i < 10)
				assertEquals("Result",(char)(48+i), main.intToChar36(i));
			else if(i >= 10 && i <36)
				assertEquals("Result",(char)(65-10+i), main.intToChar36(i));
			char result = main.intToChar36(i);
			if(result != (char)0)
				logDebug(i +" = "+result);
			else logDebug("null");
		}
		logDebug("End of \'test_intToChar36()\'");
    }
	
	//TEST: char base36 to Int
    public void test_char36ToInt(){
    	logDebug("Starting \'test_char36ToInt()\'");
    	int result = 0;
		for(int i =0; i<=39; i++){
			if(i >= 0 && i < 10){
				assertEquals("Result",i, main.char36ToInt((char)(48+i)));
				result= main.char36ToInt((char)(48+i));
			}else if(i >= 10 && i < 36){
				assertEquals("Result",i,  main.char36ToInt((char)(65-10+i)));
				result= main.char36ToInt((char)(65-10+i));
			}else result = -1;
			logDebug(main.intToChar36(i) +" = "+result);
			
		}
		logDebug("End of \'test_char36ToInt()\'");
    }
    //TEST: int to base36
    public String test_intToBase36(int val){
    	//NEW
    	logDebug("Starting \'test_intToBase36(int val)\'");
    	String str = main.intToBase36(val);
    	logDebug("base36: "+str);
    	logDebug("End of \'test_intToBase36(int val)\'");
    	return str;
    }
    //TEST: base36 To Int
    public int test_base36ToInt(String str){
    	//NEW
    	logDebug("Starting \'test_base36ToInt(String str)\'");
    	int val = main.base36ToInt(str);
    	logDebug("int: "+val);
    	logDebug("End of \'test_base36ToInt(String str)\'");
    	return val;
    }
    
    public void testAllfromMinToMax(int min, int max){
    	//this test will make sure that we can convert a long int value to a base 36; and vice versa
    	logDebug("Starting \'testAllfromMinToMax(int max)\'");
    	String str = "";
    	int val = -1;
    	
    	for(int i = min; i < max; i++){
    		str = test_intToBase36(i);
    		try{
    			val = test_base36ToInt(str);
    			if(i <= this.max)
    				assertEquals("("+i+")Check if input is the same as output \nfor int->base64 to base36->int: ", i, val);
    		}catch(IllegalArgumentException e){
    			logError(e.toString());	
    		}
    		//TODO remove this comment println("");
    	}
    	logDebug("End of \'testAllfromMinToMax(int max)\'");
    }
    
    //used to make things easy
    public void logInfo(Object obj){
    	logger.info(obj.toString());
    }
    public void logError(Object obj){
    	logger.error(obj.toString());
    }
    public void logWarrning(Object obj){
    	logger.warn(obj.toString());
    }
    public void logDebug(Object obj){
    	logger.debug(obj.toString());
    }
    public void println(Object obj){
    	System.out.println(obj.toString() + "\n");
    }
    
}
