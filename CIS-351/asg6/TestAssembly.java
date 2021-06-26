import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;

public class TestAssembly {
	
	private int wackySum(int a, int b, int c) {
		int sum = 0;
		for (int i = a; i <= b; i += c) {
			sum += combineFour(i, (i + 1) / 2, (i + 2) / 2, i + 3);
		}
		return sum;
	}

	private int combineFour(int a, int b, int c, int d) {
		int sum = a + b + c + d;
		if (sum % 2 == 0) return sum;
		else return sum / 2;
	}

	public void verify(int a, int b, int c) {
		int expect = wackySum(a, b, c);
		run("wackySum", a, b, c);
		Assert.assertEquals(expect, get(v0));
	}

	@Test
	public void test() {
		verify(21, 26, 1);
		verify(33, 42, 2);
	}
}

