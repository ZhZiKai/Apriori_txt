package algorithm;

import txt_work.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * apriori�㷨������
 * 
 * @author zzk
 * 
 */
/**
 * @author zzk
 *
 */
public class AprioriTool {
	// ��С֧�ֶȼ���
	private int minSupportCount;
	// ���������ļ���ַ
	private String filePath;
	// ÿ�������е���ƷID
	private ArrayList<String[]> totalGoodsIDs;
	// �����м������������Ƶ����б�
	private static ArrayList<FrequentItem> resultItem;
	// �����м������Ƶ�����ID����
	private ArrayList<String[]> resultItemID;
	
	private static ArrayList<related> rs;

	public AprioriTool(ArrayList<String> input_as,int minSupportCount) {
		this.minSupportCount = minSupportCount;
		getlist();
		readData(input_as);
	}

	
	public static  ArrayList<FrequentItem>  get_res(){
		return  resultItem;		
	}
	
	
	
	/**
	 * ���ļ��ж�ȡ����
	 */
	private void readData(ArrayList<String> temp) {
	
		ArrayList<String[]> dataArray = new ArrayList<String[]>();


			String str;
			String[] tempArray;
					
			ArrayList <String> test_as=new ArrayList<String>();
			test_as=temp;
		
			   Iterator<String> it1 = test_as.iterator();
		        while(it1.hasNext()){
		        	 str=it1.next();
		        	//System.out.println(str);
		            tempArray = str.split(" ");					
					dataArray.add(tempArray);
		            
		        }

		        
		        
	

		String[] temp1 = null;
		totalGoodsIDs = new ArrayList<>();
		for (String[] array : dataArray) {
			temp1 = new String[array.length - 1];
			System.arraycopy(array, 1, temp1, 0, array.length - 1);

			// ������ID�����б����
			totalGoodsIDs.add(temp1);
		}
	}

	/**
	 * �ж��ַ�����array2�Ƿ����������array1��
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public boolean iSStrContain(String[] array1, String[] array2) {
		if (array1 == null || array2 == null) {
			return false;
		}

		boolean iSContain = false;
		for (String s : array2) {
			// �µ���ĸ�Ƚ�ʱ�����³�ʼ������
			iSContain = false;
			// �ж�array2��ÿ���ַ���ֻҪ������array1�� ���������
			for (String s2 : array1) {
				if (s.equals(s2)) {
					iSContain = true;
					break;
				}
			}

			// ����Ѿ��жϳ��������ˣ���ֱ���ж�ѭ��
			if (!iSContain) {
				break;
			}
		}

		return iSContain;
	}
	
	/**
	 * ��txt_work����addDatalist()���� ��ȡԴ���� list --> test_ls.
	 */
	public static ArrayList<String> getlist(){
		
		ArrayList <String> test_ls=new ArrayList<String >();
		test_ls=work.addDatalist();			
		return test_ls;		
	}
	
	/**
	 * �������������
	 */
	private ArrayList<related> computeLink() {
		// ���Ӽ������ֹ����k������㵽k-1���Ϊֹ
		int endNum = 0;
		// ��ǰ�Ѿ������������㵽���,��ʼʱ����1�
		int currentNum = 1;
		// ��Ʒ��1Ƶ���ӳ��ͼ
		HashMap<String, FrequentItem> itemMap = new HashMap<>();
		FrequentItem tempItem;
		// ��ʼ�б�
		ArrayList<FrequentItem> list = new ArrayList<FrequentItem>();
		// �����������������Ľ���
		resultItem = new ArrayList<>();
		resultItemID = new ArrayList<>();
		// ��ƷID������
		ArrayList<String> idType = new ArrayList<>();
		for (String[] a : totalGoodsIDs) {
			for (String s : a) {
				if (!idType.contains(s)) {
					tempItem = new FrequentItem(new String[] { s }, 1);
					idType.add(s);
					resultItemID.add(new String[] { s });
				} else {
					// ֧�ֶȼ�����1
					tempItem = itemMap.get(s);
					tempItem.setCount(tempItem.getCount() + 1);
				}
				itemMap.put(s, tempItem);
			}
		}
		// ����ʼƵ���ת�뵽�б��У��Ա��������������
		for (Map.Entry entry : itemMap.entrySet()) {
			list.add((FrequentItem) entry.getValue());
		}
		// ������ƷID�������򣬷������Ӽ��������᲻һ�£��������
		Collections.sort(list);
		resultItem.addAll(list);


		String[] array1;
		String[] array2;
		String[] resultArray;
		ArrayList<String> tempIds;
		ArrayList<String[]> resultContainer;
		// �ܹ�Ҫ�㵽endNum�
		endNum = list.size() - 1;

		while (currentNum < endNum) {
			resultContainer = new ArrayList<>();
			for (int i = 0; i < list.size() - 1; i++) {
				tempItem = list.get(i);
				array1 = tempItem.getIdArray();
				for (int j = i + 1; j < list.size(); j++) {
					tempIds = new ArrayList<>();
					array2 = list.get(j).getIdArray();
					for (int k = 0; k < array1.length; k++) {
						// �����Ӧλ���ϵ�ֵ��ȵ�ʱ��ֻȡ����һ��ֵ������һ������ɾ������
						if (array1[k].equals(array2[k])) {
							tempIds.add(array1[k]);
						} else {
							tempIds.add(array1[k]);
							tempIds.add(array2[k]);
						}
					}
					resultArray = new String[tempIds.size()];
					tempIds.toArray(resultArray);

					boolean isContain = false;
					// ���˲����������ĵ�ID���飬�����ظ��ĺͳ��Ȳ�����Ҫ���
					if (resultArray.length == (array1.length + 1)) {
						isContain = isIDArrayContains(resultContainer,
								resultArray);
						if (!isContain) {
							resultContainer.add(resultArray);
						}
					}
				}
			}

			// ��Ƶ����ļ�֦�������뱣֤�µ�Ƶ��������Ҳ������Ƶ���
			list = cutItem(resultContainer);
			currentNum++;
		}
		

		
		/*
			չʾ������ resultItem �е�Ƶ�������  i ������������
			
		 */		
		int tempk=currentNum-1;
		
				// �� getlist() �����л�ȡ Դ���� , test_ls --> temp_ls.
		   ArrayList<String> temp_ls=getlist();			 
		
		
			System.out.println("Ƶ��" +2 + "���");
			
			// �˷����� ����  resultItem ��� �㷨 �����
			ArrayList<related> test_rs=new ArrayList<related>();
			
		
			for (FrequentItem i : resultItem) {	
				
				String single_str=new String();
				if (i.getLength() == 2) {	
					
					// ����һ��result ��� ʵ��������
					related rs_item=new related();
										
					System.out.print("支持度是"+i.getCount());
					rs_item.setSupport(i.getCount());
					
					System.out.print(" {");				
					single_str="";
					for (String t : i.getIdArray()) {  	//getIdArray�������� return idArray; 	���� private String[] idArray;					 
						//��ӡԴ���ݼ�
						
						int temp_key=Integer.valueOf(t);	
						String temp_str=temp_ls.get(temp_key);
						
					   	System.out.print(temp_str+"@ ");	
					   	single_str=single_str+" "+temp_str;	
					   	}
					rs_item.setRelated(single_str);
					
					System.out.print("} ");
					System.out.println();
					
					test_rs.add(rs_item);
					
				}
			}
		
					return  test_rs;
	}

	/**
	 * �ж��б������Ƿ��Ѿ�����������
	 * 
	 * @param container
	 *            ID��������
	 * @param array
	 *            ���Ƚ�����
	 * @return
	 */
	private boolean isIDArrayContains(ArrayList<String[]> container,
			String[] array) {
		boolean isContain = true;
		if (container.size() == 0) {
			isContain = false;
			return isContain;
		}

		for (String[] s : container) {
			// �Ƚϵ��Ӻ����뱣֤����һ��
			if (s.length != array.length) {
				continue;
			}

			isContain = true;
			for (int i = 0; i < s.length; i++) {
				// ֻҪ��һ��id���ȣ����㲻���
				if (s[i] != array[i]) {
					isContain = false;
					break;
				}
			}

			// ����Ѿ��ж��ǰ�����������ʱ��ֱ���˳�
			if (isContain) {
				break;
			}
		}

		return isContain;
	}

	/**
	 * ��Ƶ�������֦���裬���뱣֤�µ�Ƶ��������Ҳ������Ƶ���
	 */
	private ArrayList<FrequentItem> cutItem(ArrayList<String[]> resultIds) {
		String[] temp;
		// ���Ե�����λ�ã��Դ˹����Ӽ�
		int igNoreIndex = 0;
		FrequentItem tempItem;
		// ��֦�����µ�Ƶ���
		ArrayList<FrequentItem> newItem = new ArrayList<>();
		// ������Ҫ���id
		ArrayList<String[]> deleteIdArray = new ArrayList<>();
		// ����Ƿ�ҲΪƵ�����
		boolean isContain = true;

		for (String[] array : resultIds) {
			// �оٳ����е�һ������������жϴ�����Ƶ����б���
			temp = new String[array.length - 1];
			for (igNoreIndex = 0; igNoreIndex < array.length; igNoreIndex++) {
				isContain = true;
				for (int j = 0, k = 0; j < array.length; j++) {
					if (j != igNoreIndex) {
						temp[k] = array[j];
						k++;
					}
				}

				if (!isIDArrayContains(resultItemID, temp)) {
					isContain = false;
					break;
				}
			}

			if (!isContain) {
				deleteIdArray.add(array);
			}
		}

		// �Ƴ�������������ID���
		resultIds.removeAll(deleteIdArray);

		// �Ƴ�֧�ֶȼ���������id����
		int tempCount = 0;
		for (String[] array : resultIds) {
			tempCount = 0;
			for (String[] array2 : totalGoodsIDs) {
				if (isStrArrayContain(array2, array)) {
					tempCount++;
				}
			}

			// ���֧�ֶȼ������ڵ�����С��С֧�ֶȼ����������µ�Ƶ�����������������
			if (tempCount >= minSupportCount) {
				tempItem = new FrequentItem(array, tempCount);
				newItem.add(tempItem);
				resultItemID.add(array);
				resultItem.add(tempItem);
			}
		}

		return newItem;
	}

	/**
	 * ����array2�Ƿ������array1�У�����Ҫ��ȫһ��
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	private boolean isStrArrayContain(String[] array1, String[] array2) {
		boolean isContain = true;
		for (String s2 : array2) {
			isContain = false;
			for (String s1 : array1) {
				// ֻҪs2�ַ�������array1�У�����ַ����������array1��
				if (s2.equals(s1)) {
					isContain = true;
					break;
				}
			}

			// һ�����ֲ��������ַ�����array2���鲻������array1��
			if (!isContain) {
				break;
			}
		}

		return isContain;
	}

	/**
	 * ���ݲ�����Ƶ��������������
	 * 
	 * @param minConf
	 *            ��С���Ŷ���ֵ
	 */
	public ArrayList<related> showRelated(double minConf) {
		// �������Ӻͼ�֦����
		ArrayList<related> temp_rs=new ArrayList<related>();
		temp_rs=computeLink();
		get_res();
		return temp_rs;

	}

	/**
	 * ����תΪ��������ʽ
	 * 
	 * @param binaryArray
	 *            ת����Ķ�����������ʽ
	 * @param num
	 *            ��ת������
	 */
	private void numToBinaryArray(int[] binaryArray, int num) {
		int index = 0;
		while (num != 0) {
			binaryArray[index] = num % 2;
			index++;
			num /= 2;
		}
	}

}
