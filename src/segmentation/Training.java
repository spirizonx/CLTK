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
	
	//增加两个直接访问词典结果的函数
	public int getYES(String que) {
		return r_yes.getOrDefault(que, 0);
	}
	
	public int getNO(String que) {
		return r_no.getOrDefault(que, 0);
	}
	
	//目标：把每一个特性（如前一个/两个字导出后面是（和不是）词间隔的次数）存储进Map中
	//最早训练以下五类：前两个、前一个、前后各一、后一个、后两个
	//增补见下方
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
	
	//增补前三个、后三个的情形
	public void Train_Pcccb(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历前三个
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
			if(char1 != ' ') {
				while(char2 == ' ') {
					char2 = char3;
					char3 = char4;
					endTest = in.read();
					if(endTest != -1) {
						char4 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				while(char3 == ' ') {
					char3 = char4;
					endTest = in.read();
					if(endTest != -1) {
						char4 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char4 == ' ') {
					tmp = r_yes.getOrDefault("---"+char1+char2+char3, 0);
					if(tmp == 0) {
						r_yes.put("---"+char1+char2+char3, 1);
					} else {
						r_yes.replace("---"+char1+char2+char3, tmp, tmp+1);
					}
				} else {
					tmp = r_no.getOrDefault("---"+char1+char2+char3, 0);
					if(tmp == 0) {
						r_no.put("---"+char1+char2+char3, 1);
					} else {
						r_no.replace("---"+char1+char2+char3, tmp, tmp+1);
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train_Pbccc(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历后三个
		int endTest = 0;
		char char1, char2, char3, char4, char5, char6;
		char2 = (char)in.read();
		char3 = (char)in.read();
		char4 = (char)in.read();
		char5 = (char)in.read();
		char6 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = char3;
			char3 = char4;
			char4 = char5;
			char5 = char6;
			char6 = (char)endTest;
			if(char2 != ' ') {
				while(char3 == ' ' && char4 == ' ') {
					endTest = in.read();
					if(endTest != -1) {
						char3 = char4;
						char4 = char5;
						char5 = char6;
						char6 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				while(char5 == ' ' && char6 == ' ') {
					endTest = in.read();
					if(endTest != -1) {
						char5 = char6;
						char6 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char1 == ' ') {
					if(char3 == ' ') {
						if(char5 == ' ') {
							tmp = r_yes.getOrDefault("+++"+char2+char4+char6, 0);
							if(tmp == 0) {
								r_yes.put("+++"+char2+char4+char6, 1);
							} else {
								r_yes.replace("+++"+char2+char4+char6, tmp, tmp+1);
							}
						}
						else {
							tmp = r_yes.getOrDefault("+++"+char2+char4+char5, 0);
							if(tmp == 0) {
								r_yes.put("+++"+char2+char4+char5, 1);
							} else {
								r_yes.replace("+++"+char2+char4+char5, tmp, tmp+1);
							}
						}
					}
					else {
						if(char4 == ' ') {
							if(char5 == ' ') {
								tmp = r_yes.getOrDefault("+++"+char2+char3+char6, 0);
								if(tmp == 0) {
									r_yes.put("+++"+char2+char3+char6, 1);
								} else {
									r_yes.replace("+++"+char2+char3+char6, tmp, tmp+1);
								}
							}
							else {
								tmp = r_yes.getOrDefault("+++"+char2+char3+char5, 0);
								if(tmp == 0) {
									r_yes.put("+++"+char2+char3+char5, 1);
								} else {
									r_yes.replace("+++"+char2+char3+char5, tmp, tmp+1);
								}
							}
						}
						else {
							tmp = r_yes.getOrDefault("+++"+char2+char3+char4, 0);
							if(tmp == 0) {
								r_yes.put("+++"+char2+char3+char4, 1);
							} else {
								r_yes.replace("+++"+char2+char3+char4, tmp, tmp+1);
							}
						}
					}
				} else {
					if(char3 == ' ') {
						if(char5 == ' ') {
							tmp = r_no.getOrDefault("+++"+char2+char4+char6, 0);
							if(tmp == 0) {
								r_no.put("+++"+char2+char4+char6, 1);
							} else {
								r_no.replace("+++"+char2+char4+char6, tmp, tmp+1);
							}
						}
						else {
							tmp = r_no.getOrDefault("+++"+char2+char4+char5, 0);
							if(tmp == 0) {
								r_no.put("+++"+char2+char4+char5, 1);
							} else {
								r_no.replace("+++"+char2+char4+char5, tmp, tmp+1);
							}
						}
					}
					else {
						if(char4 == ' ') {
							if(char5 == ' ') {
								tmp = r_no.getOrDefault("+++"+char2+char3+char6, 0);
								if(tmp == 0) {
									r_no.put("+++"+char2+char3+char6, 1);
								} else {
									r_no.replace("+++"+char2+char3+char6, tmp, tmp+1);
								}
							}
							else {
								tmp = r_no.getOrDefault("+++"+char2+char3+char5, 0);
								if(tmp == 0) {
									r_no.put("+++"+char2+char3+char5, 1);
								} else {
									r_no.replace("+++"+char2+char3+char5, tmp, tmp+1);
								}
							}
						}
						else {
							tmp = r_no.getOrDefault("+++"+char2+char3+char4, 0);
							if(tmp == 0) {
								r_no.put("+++"+char2+char3+char4, 1);
							} else {
								r_no.replace("+++"+char2+char3+char4, tmp, tmp+1);
							}
						}
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	//增补前二后一、前一后二的情形
	public void Train_Pccbc(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历前二后一
		int endTest = 0;
		char char1, char2, char3, char4, char5;
		char2 = (char)in.read();
		char3 = (char)in.read();
		char4 = (char)in.read();
		char5 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = char3;
			char3 = char4;
			char4 = char5;
			char5 = (char)endTest;
			if(char1 != ' ') {
				while(char2 == ' ' && char3 == ' ') {
					char2 = char3;
					char3 = char4;
					char4 = char5;
					endTest = in.read();
					if(endTest != -1) {
						char5 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				while(char4 == ' ' && char5 == ' ') {
					char4 = char5;
					endTest = in.read();
					if(endTest != -1) {
						char5 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char2 == ' ') {
					if(char4 == ' ') {
						tmp = r_yes.getOrDefault("--+"+char1+char3+char5, 0);
						if(tmp == 0) {
							r_yes.put("--+"+char1+char3+char5, 1);
						} else {
							r_yes.replace("--+"+char1+char3+char5, tmp, tmp+1);
						}
					}
					else {
						tmp = r_no.getOrDefault("--+"+char1+char3+char4, 0);
						if(tmp == 0) {
							r_no.put("--+"+char1+char3+char4, 1);
						} else {
							r_no.replace("--+"+char1+char3+char4, tmp, tmp+1);
						}
					}
				} else {
					if(char3 == ' ') {
						if(char4 == ' ') {
							tmp = r_yes.getOrDefault("--+"+char1+char2+char5, 0);
							if(tmp == 0) {
								r_yes.put("--+"+char1+char2+char5, 1);
							} else {
								r_yes.replace("--+"+char1+char2+char5, tmp, tmp+1);
							}
						}
						else {
							tmp = r_no.getOrDefault("--+"+char1+char2+char4, 0);
							if(tmp == 0) {
								r_no.put("--+"+char1+char2+char4, 1);
							} else {
								r_no.replace("--+"+char1+char2+char4, tmp, tmp+1);
							}
						}
					}
					else {
						tmp = r_no.getOrDefault("--+"+char1+char2+char3, 0);
						if(tmp == 0) {
							r_no.put("--+"+char1+char2+char3, 1);
						} else {
							r_no.replace("--+"+char1+char2+char3, tmp, tmp+1);
						}
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train_Pcbcc(String CorpFile) throws IOException {
		int tmp;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		//遍历前一后二
		int endTest = 0;
		char char1, char2, char3, char4, char5;
		char2 = (char)in.read();
		char3 = (char)in.read();
		char4 = (char)in.read();
		char5 = (char)in.read();
		endTest = in.read();
		while(endTest != -1) {
			char1 = char2;
			char2 = char3;
			char3 = char4;
			char4 = char5;
			char5 = (char)endTest;
			if(char1 != ' ') {
				while(char2 == ' ' && char3 == ' ') {
					char2 = char3;
					char3 = char4;
					char4 = char5;
					endTest = in.read();
					if(endTest != -1) {
						char5 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				while(char4 == ' ' && char5 == ' ') {
					char4 = char5;
					endTest = in.read();
					if(endTest != -1) {
						char5 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				if(char2 == ' ') {
					if(char4 == ' ') {
						tmp = r_yes.getOrDefault("-++"+char1+char3+char5, 0);
						if(tmp == 0) {
							r_yes.put("-++"+char1+char3+char5, 1);
						} else {
							r_yes.replace("-++"+char1+char3+char5, tmp, tmp+1);
						}
					}
					else {
						tmp = r_yes.getOrDefault("-++"+char1+char3+char4, 0);
						if(tmp == 0) {
							r_yes.put("-++"+char1+char3+char4, 1);
						} else {
							r_yes.replace("-++"+char1+char3+char4, tmp, tmp+1);
						}
					}
				} else {
					if(char3 == ' ') {
						if(char4 == ' ') {
							tmp = r_no.getOrDefault("-++"+char1+char2+char5, 0);
							if(tmp == 0) {
								r_no.put("-++"+char1+char2+char5, 1);
							} else {
								r_no.replace("-++"+char1+char2+char5, tmp, tmp+1);
							}
						}
						else {
							tmp = r_no.getOrDefault("-++"+char1+char2+char4, 0);
							if(tmp == 0) {
								r_no.put("-++"+char1+char2+char4, 1);
							} else {
								r_no.replace("-++"+char1+char2+char4, tmp, tmp+1);
							}
						}
					}
					else {
						tmp = r_no.getOrDefault("-++"+char1+char2+char3, 0);
						if(tmp == 0) {
							r_no.put("-++"+char1+char2+char3, 1);
						} else {
							r_no.replace("-++"+char1+char2+char3, tmp, tmp+1);
						}
					}
				}
			}
			endTest = in.read();
		}
		in.close();
		return;
	}
	
	public void Train(String CorpFile) throws IOException {
		//用这一个函数来整合所有的训练函数
		Train_Pccb(CorpFile);
		Train_Pcb(CorpFile);
		Train_Pbcc(CorpFile);
		Train_Pbc(CorpFile);
		Train_Pcbc(CorpFile);
		Train_Pcccb(CorpFile);
		Train_Pbccc(CorpFile);
		
		//最后两个增补的训练函数
		Train_Pccbc(CorpFile);
		Train_Pcbcc(CorpFile);
	}
	
	//用法：String开头为一到三个加（减）号
	//加号表示间隔后的，减号表示间隔前的，符号个数表示查找的汉字个数
	public double getProbab(String req) {
		int a, b;
		a = r_yes.getOrDefault(req, 0);
		b = r_no.getOrDefault(req, 0);
		return ((double)a+1.0)/((double)a+(double)b+2.0);
	}
}


