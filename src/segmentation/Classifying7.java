package segmentation;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;

public class Classifying7 {
	private Vector<Double> theta;
	private int dim;
	
	public Classifying7() {
		theta = new Vector<>();
		dim = 7;
		for(int i = 0; i < dim; i++) {
			theta.add(0.0);
		}
	}
	
	public double h(Vector<Double> X) {
		double tmp = 0;
		for(int i = 0; i < dim; i++) {
			tmp = tmp + theta.elementAt(i) * X.elementAt(i);
		}
		tmp = 0 - tmp;
		tmp = Math.exp(tmp) + 1;
		tmp = 1.0 / tmp;
		return tmp;
	}
	
	public void Train(Vector<Vector<Double>> invec) {
		double alpha = 0.13;
		//这个alpha的设置其实很重要
		int vecCount = invec.size();
		Vector<Double> Y = new Vector<>();
		Vector<Vector<Double>> X = new Vector<>();
		Vector<Double> tmp;
		for(int i = 0; i < vecCount; i++) {
			Y.add(invec.elementAt(i).elementAt(0));
			tmp = new Vector<>();
			for(int j = 1; j <= dim; j++) {
				tmp.add(invec.elementAt(i).elementAt(j));
			}
			X.add(tmp);
		}
		//以上，接收传来的训练用向量集
		int trainTimes = 100000;
		double[] delta;
		delta = new double[dim];
		for(int i = 0; i < trainTimes; i++) {
			for(int j = 0; j < dim; j++) {
				delta[j] = 0;
				for(int k = 0; k < vecCount; k++) {
					delta[j] = delta[j] + (h(X.elementAt(k))-Y.elementAt(k))*X.elementAt(k).elementAt(j);
				}
				delta[j] = delta[j] * alpha / (double)vecCount;
			}
			for(int j = 0; j < dim; j++) {
				theta.set(j, theta.get(j) - delta[j]);
			}
		}
	}
	
	public void Save(String FilePath) throws IOException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(FilePath);
		} catch (IOException e) {
			System.out.println("写入存档错误。");
			e.printStackTrace();
		}
		fw.write(Double.toString(theta.elementAt(0)) + "\n");
		fw.write(Double.toString(theta.elementAt(1)) + "\n");
		fw.write(Double.toString(theta.elementAt(2)) + "\n");
		fw.write(Double.toString(theta.elementAt(3)) + "\n");
		fw.write(Double.toString(theta.elementAt(4)) + "\n");
		fw.write(Double.toString(theta.elementAt(5)) + "\n");
		fw.write(Double.toString(theta.elementAt(6)) + "\n");
		fw.close();
	}
	
	public void Load(String FilePath) throws IOException {
		String line;
		ArrayList<String> list = new ArrayList<>();
		BufferedReader bw = null;
		try {
			bw = new BufferedReader(new FileReader(FilePath));
		} catch (FileNotFoundException e) {
			System.out.println("未找到存档文件。");
		}
		line = bw.readLine();
		while(line != null) {
			list.add(line);
			line = bw.readLine();
		}
		for(int i = 0; i < dim; i++) {
			theta.set(i, Double.parseDouble(list.get(i)));
		}
	}
	
	public int Classify(Vector<Double> in) {
		if(h(in) < 0.5) {
			return 0;
		}
		else {
			return 1;
		}
	}
}




