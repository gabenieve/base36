package gen.base36.org.test;

import static org.junit.Assert.*;
import gen.base36.org.main;

import org.junit.Test;

import static org.junit.Assert.*;
public class mainTest {
//last tested int 11144144
	int max = (int) Math.pow((double)main.base, (double)main.MAX_digits)-1; 
	@Test
	public void test() {
		//int input = 61;
		//String str = test_intToBase36(input);
		//int val = test_base36ToInt(str);
		//assertEquals("revert base 36 to int: ", input, val);
		test_ConvertandRevert_base35();
		
		//testAllfromMinToMax(max);
	}
    
	public void test_ConvertandRevert_base35(){
		char c = '!';
		int n = 0;
		
		for(int i = 0; i <38; i++){
			c = main.intToChar35(i);
			n = main.char35ToInt(c);
			println("int input = "+i+"\tbase36 char = " + c +"\t intVal = " + n);
			if(i < 35)
				assertEquals("Convert \'"+i+"\' to base 35 and back to int ", i, n );
			else
				assertEquals("Convert \'"+i+"\' to base 35 and back to int ", 0, n );
		}
	}
	//TEST int to char base 36:
	public char test_intToChar35(int n){
		//same as int to char base 36; however E must be removed
		char c = main.intToChar35(n);
		println(n + "\tto charBase35 = \t" + c);
		//assertEquals("TEST: int ("+n+") to charBase35", n, "");
		return c;
	}
	public int  test_char35ToInt(char c){
		int result = main.char35ToInt(c);
		println(c + "\tcharBase35 to int = \t" + result);
		return result;
	}
	//TEST int to char base 36:
	public void test_intToChar36(){
    	//test intToChar36(n)
		for(int i =0; i<=39; i++){
			if(i >= 0 && i < 10)
				assertEquals("Result",(char)(48+i), main.intToChar36(i));
			else if(i >= 10 && i <36)
				assertEquals("Result",(char)(65-10+i), main.intToChar36(i));
			char result = main.intToChar36(i);
			if(result != (char)0)
				println(i +" = "+result);
			else println("null");
		}
    }
	
	//TEST: char base36 to Int
    public void test_char36ToInt(){

    	int result = 0;
		for(int i =0; i<=39; i++){
			if(i >= 0 && i < 10){
				assertEquals("Result",i, main.char36ToInt((char)(48+i)));
				result= main.char36ToInt((char)(48+i));
			}else if(i >= 10 && i < 36){
				assertEquals("Result",i,  main.char36ToInt((char)(65-10+i)));
				result= main.char36ToInt((char)(65-10+i));
			}else result = -1;
			println(main.intToChar36(i) +" = "+result);
			
		}
    }
    //TEST: int to base36
    public String test_intToBase36(int val){
    	//NEW
    	
    	String str = main.intToBase36(val);
    	println("base36: "+str);
    	return str;
    }
    //TEST: base36 To Int
    public int test_base36ToInt(String str){
    	//NEW
    	
    	int val = main.base36ToInt(str);
    	println("int: "+val);
    	return val;
    }
    
    public void testAllfromMinToMax(int max){
    	//this test will make sure that we can convert a long int value to a base 36; and vice versa
    	println("------------------------------------------");
    	for(int i = 0; i < max; i++){
    		String str = test_intToBase36(i);
    		int val = test_base36ToInt(str);
    		assertEquals("("+i+")Check if input is the same as output \nfor int->base64 to base36->int: ", i, val);
    		println("");
    	}
    }
    
    //used to make things easy
    public void print(Object obj){
    	System.out.print(obj);
    }
    public void println(Object obj){
    	System.out.println(obj);
    }
    
}
