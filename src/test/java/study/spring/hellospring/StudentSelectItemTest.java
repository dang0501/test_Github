package study.spring.hellospring;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.hellospring.model.StudentDepartment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class StudentSelectItemTest {
   
   @Autowired
   private SqlSession sqlSession; //객체 주입
   
   @Test
   public void testFactory() {
      
      //조회할 데이터의 조건값 설정
            StudentDepartment student = new StudentDepartment();
            student.setStudno(20102);
            
            //조회 결과가가 저장될 객체
            StudentDepartment result = null;
            
            try {
               result = sqlSession.selectOne("StudentJoinMapper.selectStudentJoinItem", student);
               if(result == null) {
                  throw new NullPointerException();
               }
            } catch (NullPointerException e) {
               System.out.println("조회된 데이터가 없습니다.");
            } catch (Exception e) {
               System.out.println(e.getLocalizedMessage());
               System.out.println("데이터 조회에 실패했습니다.");
            }
            
            System.out.println(result.toString());
   
   }

}