package segmentation;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import segmentation.Training;

public class VecMaking {
	private Vector<Vector<Double>> Vecs;
	private Training Dict;
	
	public VecMaking() {
		Vecs = new Vector<>();
		Dict = new Training();
	}
	
	public VecMaking(Training inDict) {
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
		char char1, char2, char3, char4, char5, char6, char7;
		int endTest, i = 0;
		char2 = (char)in.read();
		char3 = (char)in.read();
		char4 = (char)in.read();
		char5 = (char)in.read();
		char6 = (char)in.read();
		char7 = (char)in.read();
		endTest = in.read();
		while(endTest != -1 && i < 5000) {
			char1 = char2;
			char2 = char3;
			char3 = char4;
			char4 = char5;
			char5 = char6;
			char6 = char7;
			char7 = (char)endTest;
			if(char1 != ' ') {
				while(char2 == ' ' && char3 == ' ') {
					char2 = char3;
					char3 = char4;
					char4 = char5;
					char5 = char6;
					char6 = char7;
					endTest = in.read();
					if(endTest != -1) {
						char7 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				while(char4 == ' ' && char5 == ' ') {
					char4 = char5;
					char5 = char6;
					char6 = char7;
					endTest = in.read();
					if(endTest != -1) {
						char7 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				while(char6 == ' ' && char7 == ' ') {
					char6 = char7;
					endTest = in.read();
					if(endTest != -1) {
						char7 = (char)endTest;
					} else {
						break;
					}
				}
				if(endTest == -1) {
					break;
				}
				
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
			i++;
		}
		
		try {
			in.close();
			
		} catch (IOException e) {
			//do nothing...
		}
	}

	public Vector<Vector<Double>> getVecs() {
		return Vecs;
	}
}


