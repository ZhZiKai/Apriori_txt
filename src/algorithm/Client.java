package algorithm;


import java.util.ArrayList;

import txt_work.work;
import updateDB.save_DB;     //����save_DB��
import algorithm.AprioriTool;

/**
 * apriori���������ھ��㷨������
 * @author zzk
 *
 */
public class Client { 
	
	
	/**�Ӱ� txt_work.work �л�ȡ Apriori�㷨������� word_num --> word_num1.
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> get_input() throws Exception {
		  ArrayList<String> word_num1 = new ArrayList<String>();
		  word_num1= work.getas();
		  return word_num1;                 													  									
	}
		
	
	public static void main(String[] args) throws Exception{
		
		// �� get_input() ������ȡ�㷨������ temp_as.
		ArrayList<String> temp_as=new ArrayList<String>();     
		temp_as=get_input();
		
		AprioriTool tool = new AprioriTool( temp_as,2);
		ArrayList<related> rs=new ArrayList<related>();
		rs=tool.showRelated(0.7);	
		
		save_DB.save_res(rs);
		
	}
}