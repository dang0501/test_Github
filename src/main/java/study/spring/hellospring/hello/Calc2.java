package study.spring.hellospring.hello;

public class Calc2 {
	private int x;
	private int y;
	
	public Calc2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int sum() {
		return this.x + this.y;
	}
}
