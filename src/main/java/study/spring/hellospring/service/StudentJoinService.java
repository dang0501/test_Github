package study.spring.hellospring.service;

import java.util.List;

import study.spring.hellospring.model.StudentDepartment;

/**학생 관리 기능을 제공하기 위한 Service 계층.*/
public interface StudentJoinService {
	
	/**
	 * 학생 상세 조회
	 * @param student 조회할 학생의 일련번호를 담고 있는 Beans
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 */
	public StudentDepartment getStudentJoinItem(StudentDepartment student) throws Exception;

	
	/**
	 * 학생 목록 조회
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	public List<StudentDepartment> getStudentJoinList(StudentDepartment student) throws Exception;

	/**
	 * 전체 목록 수 조회
	 * @return 조회 결과
	 * @throws Exception
	 */
	public int getStudentCount(StudentDepartment student) throws Exception;

}
