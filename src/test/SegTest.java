package test;

import java.io.IOException;
import segmentation.Segmenting;

public class SegTest {

	public static void main(String[] args) throws IOException {
		Segmenting test;
		test = new Segmenting();
		test.SegInFile("/Users/xuan/Documents/workspace/FenCi/msr_test.txt",
				"/Users/xuan/Documents/workspace/FenCi/msr_result_new.txt");
		System.out.println("测试完成。");
	}

}
