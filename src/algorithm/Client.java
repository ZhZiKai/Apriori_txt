package algorithm;


import java.util.ArrayList;

import txt_work.work;

/**
 * apriori���������ھ��㷨������
 * @author zzk
 *
 */
public class Client { 
	
	
	public static ArrayList<String> get_input() throws Exception {
		  ArrayList<String> word_num1 = new ArrayList<String>();
		  word_num1= work.getas();
		  System.out.println();
		  return word_num1;                  // word_num1��   ArrayList<String>����  ��ΪApriori�㷨�����롣
												// 									
		
	}
		
	public static void main(String[] args) throws Exception{
		ArrayList<String> tempas=new ArrayList<String>();
		tempas=get_input();
		AprioriTool tool = new AprioriTool( tempas,2);
		tool.printAttachRule(0.7);
	}
}