package study.spring.hellospring;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.hellospring.model.StudentDepartment;
import study.spring.hellospring.service.StudentJoinService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ServiceStudentListTest {
	
	// Service 객체 주입
	// --> 선언만 해 놓으면 할당은 자동으로 수행된다.
	@Autowired
	StudentJoinService studentJoinService;
	
	@Test
	public void testFactory() {
		
		// 조회 조건 설정 객체
		StudentDepartment student = new StudentDepartment();
		
		// 조회범위를 설정--> 페이지 구현변수
		student.setLimitStart(0);
		student.setListCount(5);
		
		// 조회 결과를 저장하기 위한 객체
		List<StudentDepartment> list = null;
		try {
			list = studentJoinService.getStudentJoinList(student);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		for (int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
