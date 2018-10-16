package study.spring.hellospring.service;

import java.util.List;
import study.spring.hellospring.model.ProfessorDepartment;

/** 교수 관리 기능을 제공하기 위한 Service 계층. */
public interface ProfessorJoinService {
	/**
	 * 교수 상세 조회
	 * @param professor 조회할 교수의 일련번호를 담고 있는 Beans
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 */
	public ProfessorDepartment getProfessorJoinItem(ProfessorDepartment professor) 
			throws Exception;
	/**
	 * 교수 목록 조회
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	// -> import java.util.List;
	public List<ProfessorDepartment> getProfessorJoinList(ProfessorDepartment professor) 
			throws Exception;
	
	/**
	 * 전체 목록 수 조회
	 * @return 조회 결과
	 * @throws Exception
	 */
	public int getProfessorCount(ProfessorDepartment professor) throws Exception;
}


