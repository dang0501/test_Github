package study.spring.hellospring.service;

import java.util.List;
import study.spring.hellospring.model.Department;

/** 학과 관리 기능을 제공하기 위한 Service 계층. */
public interface DepartmentService {
	/**
	 * 학과 목록 조회
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	// -> import java.util.List;
	public List<Department> getDepartmentList(Department professor) throws Exception;
}
