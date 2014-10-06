package test;

import java.io.IOException;
import java.util.Vector;
import segmentation.*;

public class ClassifyTest {

	public static void main(String[] args) throws IOException {
		VecMaking test;
		test = new VecMaking();
		test.SetVecFrom("/Users/xuan/Documents/workspace/FenCi/msr_training.txt");
		Vector<Vector<Double>> result;
		result = test.getVecs();
		System.out.println("向量已生成，分类器训练中…");
		Classifying cla;
		cla = new Classifying();
		cla.Train(result);
		cla.Save("/Users/xuan/Documents/workspace/FenCi/msr_theta.txt");
		System.out.println("测试完成，结果已保存");
	}

}
