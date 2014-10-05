package test;

import java.io.IOException;
import segmentation.Segmenting;

public class SegTest {

	public static void main(String[] args) throws IOException {
		Segmenting test;
		test = new Segmenting();
		test.SegInFile("/Users/xuan/Documents/workspace/FenCi/Test.txt",
				"/Users/xuan/Documents/workspace/FenCi/Result.txt");
		System.out.println("测试完成。");
	}

}
