package study.spring.hellospring.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.hellospring.model.ProfessorDepartment;
import study.spring.hellospring.service.ProfessorJoinService;

@Service
public class ProfessorJoinServiceImpl implements ProfessorJoinService {
	
	/** MyBatis의 Mapper를 호출하기 위한 SqlSession 객체 */
	//Spring으로 부터 주입받는다.
	@Autowired
	private SqlSession sqlSession;	
		
	@Override
	public ProfessorDepartment getProfessorJoinItem(ProfessorDepartment professor) throws Exception {
		ProfessorDepartment result = null;
		
		try {
			result = sqlSession.selectOne("ProfessorJoinMapper.selectProfessorJoinItem", professor);
			if(result == null) {
				throw new NullPointerException();
			}
		} catch(NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		} catch(Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}		
		return result;
	}

	@Override
	public List<ProfessorDepartment> getProfessorJoinList(ProfessorDepartment professor) throws Exception {
		List<ProfessorDepartment> result = null;
		
		try {
			result = sqlSession.selectList("ProfessorJoinMapper.selectProfessorJoinList", professor);
			if(result == null) {
				throw new NullPointerException();
			}
		} catch(NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		} catch(Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}		
		return result; 
	}

	public int getProfessorCount(ProfessorDepartment professor) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne("ProfessorJoinMapper.selectProfessorCount", professor);
		} catch(Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	
	
	
}
