package segmentation;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Training {
	private HashMap<String, Integer> r_yes;
	private HashMap<String, Integer> r_no;
	
	//功能：用于训练分词系统，训练完毕后的词典结果存储在该类的对象中
	public Training() {
		r_yes = new HashMap<>();
		r_no = new HashMap<>();
	}
	
	//目标：把每一个特性（如前一个/两个字导出后面是（和不是）词间隔的次数）存储进Map中
	//训练以下四类：前两个、前一个、后一个、后两个
	public void Train_Pccb(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历前两个
		int endTest = 0;
		char char1, char2, char3;
		char2 = (char)in.read();
		char3 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = char3;
			char3 = (char)endTest;
			if(char1 != ' ') {
				while(char2 == ' ') {
					char2 = char3;
					endTest = in.read();
					if(endTest != -1) {
						char3 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char3 == ' ') {
					tmp = r_yes.getOrDefault("--"+char1+char2, 0);
					if(tmp == 0) {
						r_yes.put("--"+char1+char2, 1);
					} else {
						r_yes.replace("--"+char1+char2, tmp, tmp+1);
					}
				} else {
					tmp = r_no.getOrDefault("--"+char1+char2, 0);
					if(tmp == 0) {
						r_no.put("--"+char1+char2, 1);
					} else {
						r_no.replace("--"+char1+char2, tmp, tmp+1);
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train_Pcb(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历前一个
		int endTest = 0;
		char char1, char2;
		char2 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = (char)endTest;
			if(char1 != ' ') {
				if(char2 == ' ') {
					tmp = r_yes.getOrDefault("-"+char1, 0);
					if(tmp == 0) {
						r_yes.put("-"+char1, 1);
					} else {
						r_yes.replace("-"+char1, tmp, tmp+1);
					}
				} else {
					tmp = r_no.getOrDefault("-"+char1, 0);
					if(tmp == 0) {
						r_no.put("-"+char1, 1);
					} else {
						r_no.replace("-"+char1, tmp, tmp+1);
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train_Pbc(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历后一个
		int endTest = 0;
		char char1, char2;
		char2 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = (char)endTest;
			if(char2 != ' ') {
				if(char1 == ' ') {
					tmp = r_yes.getOrDefault("+"+char2, 0);
					if(tmp == 0) {
						r_yes.put("+"+char2, 1);
					} else {
						r_yes.replace("+"+char2, tmp, tmp+1);
					}
				} else {
					tmp = r_no.getOrDefault("+"+char2, 0);
					if(tmp == 0) {
						r_no.put("+"+char2, 1);
					} else {
						r_no.replace("+"+char2, tmp, tmp+1);
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train_Pbcc(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历后两个
		int endTest = 0;
		char char1, char2, char3, char4;
		char2 = (char)in.read();
		char3 = (char)in.read();
		char4 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = char3;
			char3 = char4;
			char4 = (char)endTest;
			if(char2 != ' ') {
				while(char3 == ' ' && char4 == ' ') {
					endTest = in.read();
					if(endTest != -1) {
						char3 = char4;
						char4 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char1 == ' ') {
					if(char3 != ' ') {
						tmp = r_yes.getOrDefault("++"+char2+char3, 0);
						if(tmp == 0) {
							r_yes.put("++"+char2+char3, 1);
						} else {
							r_yes.replace("++"+char2+char3, tmp, tmp+1);
						}
					} else {
						tmp = r_yes.getOrDefault("++"+char2+char4, 0);
						if(tmp == 0) {
							r_yes.put("++"+char2+char4, 1);
						} else {
							r_yes.replace("++"+char2+char4, tmp, tmp+1);
						}
					}
				} else {
					if(char3 != ' ') {
						tmp = r_no.getOrDefault("++"+char2+char3, 0);
						if(tmp == 0) {
							r_no.put("++"+char2+char3, 1);
						} else {
							r_no.replace("++"+char2+char3, tmp, tmp+1);
						}
					} else {
						tmp = r_no.getOrDefault("++"+char2+char4, 0);
						if(tmp == 0) {
							r_no.put("++"+char2+char4, 1);
						} else {
							r_no.replace("++"+char2+char4, tmp, tmp+1);
						}
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train_Pcbc(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历前后各一个
		int endTest = 0;
		char char1, char2, char3;
		char2 = (char)in.read();
		char3 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = char3;
			char3 = (char)endTest;
			if(char1 != ' ') {
				while(char2 == ' ' && char3 == ' ') {
					char2 = char3;
					endTest = in.read();
					if(endTest != -1) {
						char3 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char2 == ' ') {
					tmp = r_yes.getOrDefault("-+"+char1+char3, 0);
					if(tmp == 0) {
						r_yes.put("-+"+char1+char3, 1);
					} else {
						r_yes.replace("-+"+char1+char3, tmp, tmp+1);
					}
				} else {
					tmp = r_no.getOrDefault("-+"+char1+char2, 0);
					if(tmp == 0) {
						r_no.put("-+"+char1+char2, 1);
					} else {
						r_no.replace("-+"+char1+char2, tmp, tmp+1);
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train(String CorpFile) throws IOException {
		//用这一个函数来整合四种的训练函数
		Train_Pccb(CorpFile);
		Train_Pcb(CorpFile);
		Train_Pbcc(CorpFile);
		Train_Pbc(CorpFile);
		Train_Pcbc(CorpFile);
	}
	
	//用法：String开头为一到两个加（减）号
	//加号表示间隔后的，减号表示间隔前的，符号个数表示查找的汉字个数
	public double getProbab(String req) {
		int a, b;
		a = r_yes.getOrDefault(req, 0);
		b = r_no.getOrDefault(req, 0);
		return ((double)a+1.0)/((double)a+(double)b+2.0);
	}
}


