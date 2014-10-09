package test;

import java.io.IOException;
import java.util.Vector;
import segmentation.*;

public class ClassifyTest7 {

	public static void main(String[] args) throws IOException {
		VecMaking7 test;
		test = new VecMaking7();
		test.SetVecFrom("/Users/xuan/Documents/workspace/FenCi/msr_training.txt");
		Vector<Vector<Double>> result;
		result = test.getVecs();
		System.out.println("向量已生成，分类器训练中…");
		Classifying7 cla;
		cla = new Classifying7();
		cla.Train(result);
		cla.Save("/Users/xuan/Documents/workspace/FenCi/msr_theta_7.txt");
		System.out.println("测试完成，结果已保存");
	}

}
