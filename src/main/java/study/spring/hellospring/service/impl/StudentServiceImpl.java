package study.spring.hellospring.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.hellospring.model.Student;
import study.spring.hellospring.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	

	/** MyBatis의 Mapper를 호출하기 위한 SqlSession 객체 */
	//Spring으로 부터 주입받는다.
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addStudent(Student student) throws Exception {
		try {
			int result = sqlSession.insert("StudentMapper.insertStudentItem", student);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("저장된 데이터가 없습니다.");
		} catch(Exception e) {			
			throw new Exception("데이터 저장에 실패했습니다.");
		} 
		
	}

	@Override
	public void editStudent(Student student) throws Exception {
		try {
			int result = sqlSession.update("StudentMapper.updateStudentItem", student);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("변경된 데이터가 없습니다.");
		} catch(Exception e) {
			
			
			throw new Exception("데이터 수정에 실패했습니다.");
		} 
	}

	@Override
	public void deleteStudent(Student student) throws Exception {
		try {
			int result = sqlSession.delete("StudentMapper.deleteStudentItem", student);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("삭제된 데이터가 없습니다.");
		} catch(Exception e) {
			
			
			throw new Exception("데이터 삭제에 실패했습니다.");
		} 
		
	}

	@Override
	public Student getStudentItem(Student student) throws Exception {
		Student result = null;
		
		try {
			result = sqlSession.selectOne("StudentMapper.selectStudentItem", student);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			
			throw new Exception("조회된 데이터가 없습니다.");
		} catch(Exception e) {
			
			throw new Exception("데이터 조회에 실패했습니다.");
		}		
		
		return result;
	}
	
	@Override
	public List<Student> getStudentList(Student student) throws Exception {
		List<Student> result = null;
		try {
			result = sqlSession.selectList("StudentMapper.selectStudentList", student);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		} catch(Exception e) {
			
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public int getStudentCount(Student student) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne("StudentMapper.selectStudentCount", student);
		}catch(Exception e) {
			
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}
	
}


