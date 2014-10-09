package segmentation;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import segmentation.Training;

public class VecMaking7 {
	private Vector<Vector<Double>> Vecs;
	private Training Dict;
	
	public VecMaking7() {
		Vecs = new Vector<>();
		Dict = new Training();
	}
	
	public VecMaking7(Training inDict) {
		Vecs = new Vector<>();
		Dict = inDict;
	}
	
	public void SetVecFrom(String CorpFile) throws IOException {
		try {
			Dict.Train(CorpFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(CorpFile));
		} catch (FileNotFoundException e) {
			System.out.println("未找到对应文件。");
			return;
		}
		Vector<Double> tmp;
		char chars[];
		chars = new char[14];
		//直观起见，chars[0]不使用
		int endTest, i = 0;
		for(int t = 2; t < 14; t++) {
			chars[t] = (char)in.read();
		}
		endTest = in.read();
		while(endTest != -1 && i < 5000) {
			for(int t = 1; t < 13; t++) {
				chars[t] = chars[t+1];
			}
			chars[13] = (char)endTest;
			if(chars[1] != ' ') {
				for(int t = 2; t < 14; t = t + 2) {
					while(chars[t] == ' ' && chars[t+1] == ' ') {
						for(int tt = t; tt < 13; tt++) {
							chars[t] = chars[t+1];
						}
						endTest = in.read();	
						if(endTest != -1) {
							chars[13] = (char)endTest;
						} else {
							break;
						}
					}
					if(endTest == -1) {
						break;
					}
				}	//以下部分尚未改写
				
				//下面正式开始生成向量
				if(char2 == ' ') {
					if(char4 == ' ') {
						if(char6 == ' ') {
							tmp = new Vector<>();
							tmp.add(1.0);
							tmp.add(Dict.getProbab("--"+char1+char3));
							tmp.add(Dict.getProbab("-"+char3));
							tmp.add(Dict.getProbab("-+"+char3+char5));
							tmp.add(Dict.getProbab("+"+char5));
							tmp.add(Dict.getProbab("++"+char5+char7));
							Vecs.add(tmp);
						}
						else {
							tmp = new Vector<>();
							tmp.add(1.0);
							tmp.add(Dict.getProbab("--"+char1+char3));
							tmp.add(Dict.getProbab("-"+char3));
							tmp.add(Dict.getProbab("-+"+char3+char5));
							tmp.add(Dict.getProbab("+"+char5));
							tmp.add(Dict.getProbab("++"+char5+char6));
							Vecs.add(tmp);
						}
					}
					else {
						if(char5 == ' ') {
							if(char6 == ' ') {
								tmp = new Vector<>();
								tmp.add(0.0);
								tmp.add(Dict.getProbab("--"+char1+char3));
								tmp.add(Dict.getProbab("-"+char3));
								tmp.add(Dict.getProbab("-+"+char3+char4));
								tmp.add(Dict.getProbab("+"+char4));
								tmp.add(Dict.getProbab("++"+char4+char7));
								Vecs.add(tmp);
							}
							else {
								tmp = new Vector<>();
								tmp.add(0.0);
								tmp.add(Dict.getProbab("--"+char1+char3));
								tmp.add(Dict.getProbab("-"+char3));
								tmp.add(Dict.getProbab("-+"+char3+char4));
								tmp.add(Dict.getProbab("+"+char4));
								tmp.add(Dict.getProbab("++"+char4+char6));
								Vecs.add(tmp);
							}
						}
						else {
							tmp = new Vector<>();
							tmp.add(0.0);
							tmp.add(Dict.getProbab("--"+char1+char3));
							tmp.add(Dict.getProbab("-"+char3));
							tmp.add(Dict.getProbab("-+"+char3+char4));
							tmp.add(Dict.getProbab("+"+char4));
							tmp.add(Dict.getProbab("++"+char4+char5));
							Vecs.add(tmp);
						}
					}
				}
				else {
					if(char3 == ' ') {
						if(char4 == ' ') {
							if(char6 == ' ') {
								tmp = new Vector<>();
								tmp.add(1.0);
								tmp.add(Dict.getProbab("--"+char1+char2));
								tmp.add(Dict.getProbab("-"+char2));
								tmp.add(Dict.getProbab("-+"+char2+char5));
								tmp.add(Dict.getProbab("+"+char5));
								tmp.add(Dict.getProbab("++"+char5+char7));
								Vecs.add(tmp);
							}
							else {
								tmp = new Vector<>();
								tmp.add(1.0);
								tmp.add(Dict.getProbab("--"+char1+char2));
								tmp.add(Dict.getProbab("-"+char2));
								tmp.add(Dict.getProbab("-+"+char2+char5));
								tmp.add(Dict.getProbab("+"+char5));
								tmp.add(Dict.getProbab("++"+char5+char6));
								Vecs.add(tmp);
							}
						}
						else {
							if(char5 == ' ') {
								if(char6 == ' ') {
									tmp = new Vector<>();
									tmp.add(1.0);
									tmp.add(Dict.getProbab("--"+char1+char2));
									tmp.add(Dict.getProbab("-"+char2));
									tmp.add(Dict.getProbab("-+"+char2+char4));
									tmp.add(Dict.getProbab("+"+char4));
									tmp.add(Dict.getProbab("++"+char4+char7));
									Vecs.add(tmp);
								}
								else {
									tmp = new Vector<>();
									tmp.add(1.0);
									tmp.add(Dict.getProbab("--"+char1+char2));
									tmp.add(Dict.getProbab("-"+char2));
									tmp.add(Dict.getProbab("-+"+char2+char4));
									tmp.add(Dict.getProbab("+"+char4));
									tmp.add(Dict.getProbab("++"+char4+char6));
									Vecs.add(tmp);
								}
							}
							else {
								tmp = new Vector<>();
								tmp.add(1.0);
								tmp.add(Dict.getProbab("--"+char1+char2));
								tmp.add(Dict.getProbab("-"+char2));
								tmp.add(Dict.getProbab("-+"+char2+char4));
								tmp.add(Dict.getProbab("+"+char4));
								tmp.add(Dict.getProbab("++"+char4+char5));
								Vecs.add(tmp);
							}
						}
					}
					else {
						if(char4 == ' ') {
							tmp = new Vector<>();
							tmp.add(0.0);
							tmp.add(Dict.getProbab("--"+char1+char2));
							tmp.add(Dict.getProbab("-"+char2));
							tmp.add(Dict.getProbab("-+"+char2+char3));
							tmp.add(Dict.getProbab("+"+char3));
							tmp.add(Dict.getProbab("++"+char3+char5));
							Vecs.add(tmp);
						}
						else {
							tmp = new Vector<>();
							tmp.add(0.0);
							tmp.add(Dict.getProbab("--"+char1+char2));
							tmp.add(Dict.getProbab("-"+char2));
							tmp.add(Dict.getProbab("-+"+char2+char3));
							tmp.add(Dict.getProbab("+"+char3));
							tmp.add(Dict.getProbab("++"+char3+char4));
							Vecs.add(tmp);
						}
					}
				}
			}
			endTest = in.read();
		}
		
		try {
			in.close();
			i++;
		} catch (IOException e) {
			//do nothing...
		}
	}

	public Vector<Vector<Double>> getVecs() {
		return Vecs;
	}
}


