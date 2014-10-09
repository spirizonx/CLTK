package test;

import java.io.IOException;
import java.util.Vector;
import segmentation.VecMaking;

public class VecTest {

	public static void main(String[] args) throws IOException {
		VecMaking test;
		test = new VecMaking();
		test.SetVecFrom("/Users/xuan/Documents/workspace/FenCi/MiniCorpus.txt");
		Vector<Vector<Double>> result;
		result = test.getVecs();
		int i;
		for(i = 0; i < 10; i++) {
			System.out.println(result.elementAt(20*i));
		}
		System.out.println();
		System.out.println("测试完成");
	}

}
