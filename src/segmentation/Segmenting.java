package segmentation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Segmenting {
	//使用情况：已训练好分类器
	//如未训练，可暂时用ClassifyTest训练
	private Training dict;
	private Classifying cla;
	
	public Segmenting() throws IOException {
		dict = new Training();
		cla = new Classifying();
		//词典和分类器的整备暂时在此进行
		dict.Train("/Users/xuan/Documents/workspace/FenCi/msr_training.txt");
		cla.Load("/Users/xuan/Documents/workspace/FenCi/msr_theta.txt");
	}
	
	private boolean isEngOrNum(char inchar) {
		if(inchar == 46 || inchar == 32) {
			return true;    //点或空格的情形
		}
		if(inchar >= 48 && inchar <= 57) {
			return true;    //0-9的情形
		}
		if(inchar >= 65 && inchar <= 90) {
			return true;    //大写字母的情形
		}
		if(inchar >= 97 && inchar <= 122) {
			return true;    //小写字母的情形
		}
		return false;       //其余情形
	}
	
	private boolean isPunc(char inchar) {
		char[] Puncs = {'。', '，', '、', '—', '“', '”', '《', '》'};
		int Punc_count = 8;
		for(int i = 0; i < Punc_count; i++) {
			if(inchar == Puncs[i]) {
				return true;
			}
		}
		return false;
	}
	
	public void SegInFile(String InPath, String OutPath) throws IOException {
		String line = null;
		Vector<Integer> result;
		Vector<Double> tmp;
		BufferedReader bw = null;
		FileWriter fw = null;
		//打开准备读入的文件
		try {
			bw = new BufferedReader(new FileReader(InPath));
		} catch (FileNotFoundException e) {
			System.out.println("未找到文件。");
		}
		//读入准备第一行（段）
		try {
			line = bw.readLine();
			//line = line + "\n";
		} catch (IOException e) {
			System.out.println("读源文件时发生错误。");
			e.printStackTrace();
		}
		//打开（新建）准备写入的文件
		try {
			fw = new FileWriter(OutPath);
		} catch (IOException e1) {
			System.out.println("无法新建或打开待写入文件。");
		}
		while(true) {
			int size = line.length();
			if(size < 5) {
				try {
					line = bw.readLine();
					//line = line + "\n";
				} catch (IOException e) {
					System.out.println("读源文件时发生错误。");
					e.printStackTrace();
				}
				if(line == null) {
					break;
				}
				continue;
			}
			result = new Vector<>();
			//开头
			tmp = new Vector<>();
			tmp.add(0.5);
			tmp.add(dict.getProbab("-" + line.charAt(0)));
			tmp.add(dict.getProbab("-+" + line.charAt(0) + line.charAt(1)));
			tmp.add(dict.getProbab("+" + line.charAt(1)));
			tmp.add(dict.getProbab("++" + line.charAt(1) + line.charAt(2)));
			result.add((int)cla.Classify(tmp));
			//中段
			for(int i = 1; i < size-2; i++) {
				tmp = new Vector<>();
				tmp.add(dict.getProbab("--" + line.charAt(i-1) + line.charAt(i)));
				tmp.add(dict.getProbab("-" + line.charAt(i)));
				tmp.add(dict.getProbab("-+" + line.charAt(i) + line.charAt(i+1)));
				tmp.add(dict.getProbab("+" + line.charAt(i+1)));
				tmp.add(dict.getProbab("++" + line.charAt(i+1) + line.charAt(i+2)));
				result.add((int)cla.Classify(tmp));
			}
			//结尾
			int i = size - 2;
			tmp.add(dict.getProbab("--" + line.charAt(i-1) + line.charAt(i)));
			tmp.add(dict.getProbab("-" + line.charAt(i)));
			tmp.add(dict.getProbab("-+" + line.charAt(i) + line.charAt(i+1)));
			tmp.add(dict.getProbab("+" + line.charAt(i+1)));
			tmp.add(0.5);
			result.add((int)cla.Classify(tmp));
			//result.add(0);
			//根据result向量的指示向文件输出分词结果
			for(int k = 0; k < size-1; k++) {
				if(isPunc(line.charAt(k))) {
					fw.write(line.charAt(k));
					fw.write(' ');
					continue;
				}
				if(isPunc(line.charAt(k+1))) {
					fw.write(line.charAt(k));
					fw.write(' ');
					continue;
				}
				if(isEngOrNum(line.charAt(k))) {
					if(isEngOrNum(line.charAt(k+1))) {
						fw.write(line.charAt(k));
						continue;
					}
					else {
						fw.write(line.charAt(k));
						fw.write(' ');
						continue;
					}
				}
				if(isEngOrNum(line.charAt(k+1))) {
					fw.write(line.charAt(k));
					fw.write(' ');
					continue;
				}
				if(result.elementAt(k) == 1) {
					fw.write(line.charAt(k));
					fw.write(' ');
					continue;
				} else {
					fw.write(line.charAt(k));
					continue;
				}
			}
			fw.write(line.charAt(size-1));
			fw.write("\n");
			fw.flush();
			//准备进入下一次循环
			try {
				line = bw.readLine();
				//line = line + "\n";
			} catch (IOException e) {
				System.out.println("读源文件时发生错误。");
				e.printStackTrace();
			}
			if(line == null) {
				break;
			}
		}
		fw.close();
	}
}
