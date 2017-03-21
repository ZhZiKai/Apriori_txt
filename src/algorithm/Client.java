package algorithm;


import java.util.ArrayList;

import txt_work.work;

/**
 * apriori关联规则挖掘算法调用类
 * @author zzk
 *
 */



public class Client { 
	
	
	public static ArrayList<String> get() throws Exception {
		  ArrayList<String> word_num1 = new ArrayList<String>();
		  ArrayList<String> list =  work.getas();
		  return word_num1= work.getas();
			
		
	}
	
	
	
	
	public static void main(String[] args){
		
		AprioriTool tool = new AprioriTool( 2);
		tool.printAttachRule(0.7);
	}
}