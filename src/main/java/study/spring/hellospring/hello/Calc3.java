package study.spring.hellospring.hello;

public class Calc3 {
	private Value v;
	
	public Calc3(Value v) {
		this.v = v;
	}
	
	public int sum() {
		return v.getX() + v.getY();
	}
}
