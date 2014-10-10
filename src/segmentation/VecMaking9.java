package segmentation;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import segmentation.Training;

public class VecMaking9 {
	private Vector<Vector<Double>> Vecs;
	private Training Dict;
	
	public VecMaking9() {
		Vecs = new Vector<>();
		Dict = new Training();
	}
	
	public VecMaking9(Training inDict) {
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
							chars[tt] = chars[tt+1];
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
				}
				
				//下面正式开始生成向量
				char before[], after[];
				before = new char[3];
				after = new char[3];
				int ii, jj, count = 0;
				for(ii = 0; ; ii++) {
					if(chars[ii] != ' ') {
						before[count] = chars[ii];
						count = count + 1;
						if(count == 3) {
							break;
						}
					}
				}
				count = 0;
				if(chars[ii+1] == ' ') {
					for(jj = ii+2; ; jj++) {
						if(chars[jj] != ' ') {
							after[count] = chars[jj];
							count++;
							if(count == 3) {
								break;
							}
						}
					}
					tmp = new Vector<>();
					tmp.add(1.0);
					tmp.add(Dict.getProbab("---"+before[0]+before[1]+before[2]));
					tmp.add(Dict.getProbab("--"+before[1]+before[2]));
					tmp.add(Dict.getProbab("-"+before[2]));
					tmp.add(Dict.getProbab("-+"+before[2]+after[0]));
					tmp.add(Dict.getProbab("+"+after[0]));
					tmp.add(Dict.getProbab("++"+after[0]+after[1]));
					tmp.add(Dict.getProbab("+++"+after[0]+after[1]+after[2]));
					//注意最后增补的两个为向量的最后两维
					tmp.add(Dict.getProbab("--+"+before[1]+before[2]+after[0]));
					tmp.add(Dict.getProbab("-++"+before[2]+after[0]+after[1]));
					Vecs.add(tmp);
				}
				else {
					for(jj = ii+1; ; jj++) {
						if(chars[jj] != ' ') {
							after[count] = chars[jj];
							count++;
							if(count == 3) {
								break;
							}
						}
					}
					tmp = new Vector<>();
					tmp.add(0.0);
					tmp.add(Dict.getProbab("---"+before[0]+before[1]+before[2]));
					tmp.add(Dict.getProbab("--"+before[1]+before[2]));
					tmp.add(Dict.getProbab("-"+before[2]));
					tmp.add(Dict.getProbab("-+"+before[2]+after[0]));
					tmp.add(Dict.getProbab("+"+after[0]));
					tmp.add(Dict.getProbab("++"+after[0]+after[1]));
					tmp.add(Dict.getProbab("+++"+after[0]+after[1]+after[2]));
					//注意最后增补的两个为向量的最后两维
					tmp.add(Dict.getProbab("--+"+before[1]+before[2]+after[0]));
					tmp.add(Dict.getProbab("-++"+before[2]+after[0]+after[1]));
					Vecs.add(tmp);
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


