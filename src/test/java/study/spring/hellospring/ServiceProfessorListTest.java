package study.spring.hellospring;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.hellospring.model.ProfessorDepartment;
import study.spring.hellospring.service.ProfessorJoinService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ServiceProfessorListTest {
	
	// Service 객체 주입
	// --> 선언만 해 놓으면 할당은 자동으로 수행된다.
	@Autowired
	ProfessorJoinService professorJoinService;
	
	@Test
	public void testFactory() {
		
		// 조회 조건 설정 객체
		ProfessorDepartment professor = new ProfessorDepartment();
		
		// 조회범위를 설정--> 페이지 구현변수
		professor.setLimitStart(0);
		professor.setListCount(5);
		
		// 조회 결과를 저장하기 위한 객체
		List<ProfessorDepartment> list = null;
		try {
			list = professorJoinService.getProfessorJoinList(professor);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		for (int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
