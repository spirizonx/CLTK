package test;

import java.io.IOException;
import segmentation.*;

class TrainTest {
	public static void main(String[] args) throws IOException {
		Training test;
		test = new Training();
		test.Train("/Users/xuan/Documents/workspace/FenCi/MiniCorpus.txt");
		System.out.println(test.getProbab("-+努力"));
		System.out.println("测试完成。");
	}
}

