package study.spring.hellospring.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.hellospring.model.Professor;
import study.spring.hellospring.service.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {
	
	/** MyBatis의 Mapper를 호출하기 위한 SqlSession 객체 */
	//Spring으로 부터 주입받는다.
	@Autowired
	private SqlSession sqlSession;	
	
	
	@Override
	public void addProfessor(Professor professor) throws Exception {
		try {
			int result = sqlSession.insert("ProfessorMapper.insertProfessorItem", professor);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("저장된 데이터가 없습니다.");
		}catch(Exception e) {
			
			throw new Exception("데이터 저장에 실패했습니다.");
		}
		
	}

	@Override
	public void editProfessor(Professor professor) throws Exception {
		try {
			int result = sqlSession.update("ProfessorMapper.updateProfessorItem", professor);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("변경된 데이터가 없습니다.");
		}catch(Exception e) {
			
			throw new Exception("데이터 수정에 실패했습니다.");
		}
		
	}

	@Override
	public void deleteProfessor(Professor professor) throws Exception {
		try {
			int result = sqlSession.delete("ProfessorMapper.deleteProfessorItem", professor);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("삭제된 데이터가 없습니다.");
		}catch(Exception e) {
			
			throw new Exception("데이터 삭제에 실패했습니다.");
		}finally {
			
		}
		
	}

	@Override
	public Professor getProfessorItem(Professor professor) throws Exception {
		Professor result = null;
		
		try {
			result = sqlSession.selectOne("ProfessorMapper.selectProfessorItem", professor);
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
	public List<Professor> getProfessorList(Professor professor) throws Exception {
		List<Professor> result = null;
		
		try {
			result = sqlSession.selectList("ProfessorMapper.selectProfessorList", professor);
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

}
