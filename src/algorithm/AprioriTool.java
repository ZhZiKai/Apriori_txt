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
 * apriori算法工具类
 * 
 * @author zzk
 * 
 */
/**
 * @author zzk
 *
 */
public class AprioriTool {
	// 最小支持度计数
	private int minSupportCount;
	// 测试数据文件地址
	private String filePath;
	// 每个事务中的商品ID
	private ArrayList<String[]> totalGoodsIDs;
	// 过程中计算出来的所有频繁项集列表
	private static ArrayList<FrequentItem> resultItem;
	// 过程中计算出来频繁项集的ID集合
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
	 * 从文件中读取数据
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

			// 将事务ID加入列表吧中
			totalGoodsIDs.add(temp1);
		}
	}

	/**
	 * 判读字符数组array2是否包含于数组array1中
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
			// 新的字母比较时，重新初始化变量
			iSContain = false;
			// 判读array2中每个字符，只要包括在array1中 ，就算包含
			for (String s2 : array1) {
				if (s.equals(s2)) {
					iSContain = true;
					break;
				}
			}

			// 如果已经判断出不包含了，则直接中断循环
			if (!iSContain) {
				break;
			}
		}

		return iSContain;
	}
	
	/**
	 * 从txt_work包中addDatalist()方法 获取源数据 list --> test_ls.
	 */
	public static ArrayList<String> getlist(){
		
		ArrayList <String> test_ls=new ArrayList<String >();
		test_ls=work.addDatalist();			
		return test_ls;		
	}
	
	/**
	 * 项集进行连接运算
	 */
	private ArrayList<related> computeLink() {
		// 连接计算的终止数，k项集必须算到k-1子项集为止
		int endNum = 0;
		// 当前已经进行连接运算到几项集,开始时就是1项集
		int currentNum = 1;
		// 商品，1频繁项集映射图
		HashMap<String, FrequentItem> itemMap = new HashMap<>();
		FrequentItem tempItem;
		// 初始列表
		ArrayList<FrequentItem> list = new ArrayList<FrequentItem>();
		// 经过连接运算后产生的结果项集
		resultItem = new ArrayList<>();
		resultItemID = new ArrayList<>();
		// 商品ID的种类
		ArrayList<String> idType = new ArrayList<>();
		for (String[] a : totalGoodsIDs) {
			for (String s : a) {
				if (!idType.contains(s)) {
					tempItem = new FrequentItem(new String[] { s }, 1);
					idType.add(s);
					resultItemID.add(new String[] { s });
				} else {
					// 支持度计数加1
					tempItem = itemMap.get(s);
					tempItem.setCount(tempItem.getCount() + 1);
				}
				itemMap.put(s, tempItem);
			}
		}
		// 将初始频繁项集转入到列表中，以便继续做连接运算
		for (Map.Entry entry : itemMap.entrySet()) {
			list.add((FrequentItem) entry.getValue());
		}
		// 按照商品ID进行排序，否则连接计算结果将会不一致，将会减少
		Collections.sort(list);
		resultItem.addAll(list);


		String[] array1;
		String[] array2;
		String[] resultArray;
		ArrayList<String> tempIds;
		ArrayList<String[]> resultContainer;
		// 总共要算到endNum项集
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
						// 如果对应位置上的值相等的时候，只取其中一个值，做了一个连接删除操作
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
					// 过滤不符合条件的的ID数组，包括重复的和长度不符合要求的
					if (resultArray.length == (array1.length + 1)) {
						isContain = isIDArrayContains(resultContainer,
								resultArray);
						if (!isContain) {
							resultContainer.add(resultArray);
						}
					}
				}
			}

			// 做频繁项集的剪枝处理，必须保证新的频繁项集的子项集也必须是频繁项集
			list = cutItem(resultContainer);
			currentNum++;
		}
		

		
		/*
			展示所有在 resultItem 中的频繁项集对象  i ，即关联规则。
			
		 */		
		int tempk=currentNum-1;
		
				// 从 getlist() 方法中获取 源数据 , test_ls --> temp_ls.
		   ArrayList<String> temp_ls=getlist();			 
		
		
			System.out.println("频繁" +2 + "项集：");
			
			// 此方法中 属性  resultItem 存放 算法 结果。
			ArrayList<related> test_rs=new ArrayList<related>();
			
		
			for (FrequentItem i : resultItem) {	
				
				String single_str=new String();
				if (i.getLength() == 2) {	
					
					// 创建一个result 类的 实例化对象。
					related rs_item=new related();
										
					System.out.print("支持度 "+i.getCount());
					rs_item.setSupport(i.getCount());
					
					System.out.print(" {");				
					single_str="";
					for (String t : i.getIdArray()) {  	//getIdArray方法返回 return idArray; 	类型 private String[] idArray;					 
						//打印源数据集
						
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
	 * 判断列表结果中是否已经包含此数组
	 * 
	 * @param container
	 *            ID数组容器
	 * @param array
	 *            待比较数组
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
			// 比较的视乎必须保证长度一样
			if (s.length != array.length) {
				continue;
			}

			isContain = true;
			for (int i = 0; i < s.length; i++) {
				// 只要有一个id不等，就算不相等
				if (s[i] != array[i]) {
					isContain = false;
					break;
				}
			}

			// 如果已经判断是包含在容器中时，直接退出
			if (isContain) {
				break;
			}
		}

		return isContain;
	}

	/**
	 * 对频繁项集做剪枝步骤，必须保证新的频繁项集的子项集也必须是频繁项集
	 */
	private ArrayList<FrequentItem> cutItem(ArrayList<String[]> resultIds) {
		String[] temp;
		// 忽略的索引位置，以此构建子集
		int igNoreIndex = 0;
		FrequentItem tempItem;
		// 剪枝生成新的频繁项集
		ArrayList<FrequentItem> newItem = new ArrayList<>();
		// 不符合要求的id
		ArrayList<String[]> deleteIdArray = new ArrayList<>();
		// 子项集是否也为频繁子项集
		boolean isContain = true;

		for (String[] array : resultIds) {
			// 列举出其中的一个个的子项集，判断存在于频繁项集列表中
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

		// 移除不符合条件的ID组合
		resultIds.removeAll(deleteIdArray);

		// 移除支持度计数不够的id集合
		int tempCount = 0;
		for (String[] array : resultIds) {
			tempCount = 0;
			for (String[] array2 : totalGoodsIDs) {
				if (isStrArrayContain(array2, array)) {
					tempCount++;
				}
			}

			// 如果支持度计数大于等于最小最小支持度计数则生成新的频繁项集，并加入结果集中
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
	 * 数组array2是否包含于array1中，不需要完全一样
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
				// 只要s2字符存在于array1中，这个字符就算包含在array1中
				if (s2.equals(s1)) {
					isContain = true;
					break;
				}
			}

			// 一旦发现不包含的字符，则array2数组不包含于array1中
			if (!isContain) {
				break;
			}
		}

		return isContain;
	}

	/**
	 * 根据产生的频繁项集输出关联规则
	 * 
	 * @param minConf
	 *            最小置信度阈值
	 */
	public ArrayList<related> showRelated(double minConf) {
		// 进行连接和剪枝操作
		ArrayList<related> temp_rs=new ArrayList<related>();
		temp_rs=computeLink();
		get_res();
		return temp_rs;

	}

	/**
	 * 数字转为二进制形式
	 * 
	 * @param binaryArray
	 *            转化后的二进制数组形式
	 * @param num
	 *            待转化数字
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
