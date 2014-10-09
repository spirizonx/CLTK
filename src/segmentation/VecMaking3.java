package segmentation;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import segmentation.Training;

public class VecMaking3 {
	private Vector<Vector<Double>> Vecs;
	private Training Dict;
	
	public VecMaking3() {
		Vecs = new Vector<>();
		Dict = new Training();
	}
	
	public VecMaking3(Training inDict) {
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
		char char1, char2, char3;
		int endTest, i = 0;
		char2 = (char)in.read();
		char3 = (char)in.read();
		endTest = in.read();
		while(endTest != -1 && i < 5000) {
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
				
				//下面正式开始生成向量
				if(char2 == ' ') {
					tmp = new Vector<>();
					tmp.add(1.0);
					tmp.add(Dict.getProbab("-"+char1));
					tmp.add(Dict.getProbab("-+"+char1+char3));
					tmp.add(Dict.getProbab("+"+char3));
					Vecs.add(tmp);
				}
				else {
					tmp = new Vector<>();
					tmp.add(0.0);
					tmp.add(Dict.getProbab("-"+char1));
					tmp.add(Dict.getProbab("-+"+char1+char2));
					tmp.add(Dict.getProbab("+"+char2));
					Vecs.add(tmp);
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


