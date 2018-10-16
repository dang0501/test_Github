package study.spring.hellospring.hello;

import java.util.Random;

public class Calc4 {
	private int x;
	private int y;
	
	public void init() {
		//랜덤객체 생성
		Random r = new Random(System.currentTimeMillis());
		//랜덤한int 값을 설정한다.
		x = r.nextInt();
		y = r.nextInt();
	}
	
	public int sum() {
		return this.x + this.y;
	}
}