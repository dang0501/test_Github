package study.spring.hellospring;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import study.spring.hellospring.model.Department;
import study.spring.hellospring.model.Professor;
import study.spring.hellospring.model.Student;
import study.spring.hellospring.model.StudentDepartment;
import study.spring.hellospring.service.DepartmentService;
import study.spring.hellospring.service.ProfessorService;
import study.spring.hellospring.service.StudentJoinService;
import study.spring.hellospring.service.StudentService;
import study.spring.helper.PageHelper;
import study.spring.helper.WebHelper;

@Controller
public class StudentController {
	
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	//--> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	//-->import study.spring.helper.PageHelper;
	@Autowired
	PageHelper page;
	
	//목록, 상세보기에서 사용할 서비스 객체 --> Department와의 Join처리
	//-->import study.spring.hellospring.service.ProfessorService;
	@Autowired
	StudentJoinService studentJoinService;
	
	//등록 삭제 수정 에서 사용할 서비스 객체
	//-->import study.spring.hellospring.service.DepartmentService;
	@Autowired
	StudentService studentService;
	
	//등록, 수정시에 소속학과에 대한 드롭다운을 구현하기 위한 서비스객체
	//-->import study.spring.hellospring.service.DepartmentService;
	@Autowired
	DepartmentService departmentService;	
	
	@Autowired
	ProfessorService professorService;
	/** 학생 목록 페이지 */
	@RequestMapping(value="/student/stud_list.do", method=RequestMethod.GET)
	public ModelAndView StudList(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		//파라미터를 저장할 Beans
		StudentDepartment student = new StudentDepartment();
		
		//검색어 파라미터 받기 + Beans 설정
		String keyword = web.getString("keyword", "");
		student.setName(keyword);
		
		//현재 페이지 번호에 대한 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2) 페이지 번호 구현하기 */
		//전체 데이터 수 조회하기
		int totalCount = 0;
		try{
			totalCount = studentJoinService.getStudentCount(student);
		} catch(Exception e){						
			return web.redirect(null, e.getLocalizedMessage()); 
		}
		
		//페이지 번호에 대한 연산 수행 후 조회조건값 지정을 위한 Beans에 추가하기
		page.pageProcess(nowPage, totalCount, 10, 5);
		student.setLimitStart(page.getLimitStart());
		student.setListCount(page.getListCount());
		
		/** 3) Service를 통한 SQL 수행 */
		//조회 결과를 저장하기 위한 객체
		List<StudentDepartment> list = null;
		try{
			list = studentJoinService.getStudentJoinList(student);
		}catch(Exception e){
		 return web.redirect(null, e.getLocalizedMessage());
		}		
		
		/** 4) View 처리하기 */
		//조회결과를 View에게 전달한다.
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		return new ModelAndView("student/stud_list");
	}
	
	/** 학생 정보 상세보기 페이지 */
	@RequestMapping(value="/student/stud_view.do", method=RequestMethod.GET)
	public ModelAndView ProfView(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int studno = web.getInt("studno");
		logger.debug("studno=" + studno);
		
		if(studno == 0){
			return web.redirect(null, "학생번호가 없습니다.");
		}
		
		//전달된 파라미터를 beans에 저장한다.
		StudentDepartment student = new StudentDepartment();
		student.setStudno(studno);
		
		/** 2) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		StudentDepartment item = null;
		try{
			item = studentJoinService.getStudentJoinItem(student);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());	
		}
		
		/** 3) view 처리하기 */
		model.addAttribute("item", item);
		
		return new ModelAndView("student/stud_view");
	}
	
	/** 학생 등록 페이지 */
	@RequestMapping(value="/student/stud_add.do", method=RequestMethod.GET)
	public ModelAndView StudAdd(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 2) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Department> deptList = null;
		List<Professor> profList = null;
		try {
			deptList = departmentService.getDepartmentList(null);
			profList = professorService.getProfessorList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 3) view 처리하기 */
		model.addAttribute("deptList", deptList);
		model.addAttribute("profList", profList);
		return new ModelAndView("student/stud_add");
	}
	
	/** 교수 등록 처리  페이지(action 페이지로 사용된다.) */
	@RequestMapping(value="/student/stud_add_ok.do", method=RequestMethod.POST)
	public ModelAndView StudAddOk(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		//input 태그의 name 속성에 명시된 값을 사용한다.
		String name = web.getString("name");
		String userid = web.getString("userid");
		int grade = web.getInt("grade");
		String idnum = web.getString("idnum");
		String birthdate = web.getString("birthdate");
		String tel = web.getString("tel");
		int height = web.getInt("height");
		int weight = web.getInt("weight");
		int deptno = web.getInt("deptno");
		int profno = web.getInt("profno");
				
		//전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("grade=" + grade);
		logger.debug("idnum=" + idnum);
		logger.debug("birthdate=" + birthdate);
		logger.debug("tel=" + tel);
		logger.debug("height=" + height);
		logger.debug("weight=" + weight);
		logger.debug("deptno=" + deptno);
		logger.debug("profno=" + profno);
		
		/** 2) 필수 항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
				if(name == null){
					return web.redirect(null, "이름을 입력하세요.");					
				}				
				if(userid == null){				
					return web.redirect(null, "아이디를 입력하세요.");
				}				
				if(grade == 0) {
					return web.redirect(null, "학년을 입력하세요.");
			 	}
			 	
			 	if(idnum == null) {
			 		return web.redirect(null, "주민등록번호를 입력하세요.");
			 	}
			 	
			 	if(birthdate == null) {
			 		return web.redirect(null, "생년월일을 입력하세요.");
			 	}

				if(tel == null) {
					return web.redirect(null, "전화번호를 입력하세요.");
			 	}

				if(height == 0) {
					return web.redirect(null, "키를 입력하세요.");
			 	}
				
				if(weight == 0) {
					return web.redirect(null, "몸무게를  입력하세요.");
			 	}
			 	
			 	if(deptno == 0) {
			 		return web.redirect(null, "학과번호를 입력하세요.");
			 	}

				
		
		/** 3) 저장을 위한 javaBeans 구성하기 */
				Student student = new Student();
				student.setName(name);
				student.setUserid(userid);
				student.setGrade(grade);
				student.setIdnum(idnum);
				student.setBirthdate(birthdate);
				student.setTel(tel);
				student.setHeight(height);
				student.setWeight(weight);
				student.setDeptno(deptno);
				student.setProfno(profno);				
				
		/** 4) Service를 통한 SQL 수행 */
		try{
			studentService.addStudent(student);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 5) 결과를 확인하기 위한 페이지로 이동하기 */
		String url = web.getRootPath() + "/student/stud_view.do?studno=" + student.getStudno();
		return web.redirect(url,"저장되었습니다.");
	}

	/** 학생 정보 삭제 페이지 */
	@RequestMapping(value="/student/stud_delete.do", method=RequestMethod.GET)
	public ModelAndView StudDelete(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int studno = web.getInt("studno");
		logger.debug("studno=" + studno);
		
		if(studno == 0){
			return web.redirect(null, "학생번호가 없습니다.");
		}
		
		//전달된 파라미터를 beans에 저장한다.
		Student student = new Student();
		student.setStudno(studno);
		
		/** 2) Service를 통한 SQL 수행 */
		try{
			studentService.deleteStudent(student);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());	
		}
		
		/** 3) 목록페이지로 이동 */
		return web.redirect(web.getRootPath() + "/student/stud_list.do", "삭제되었습니다.");
	}
	
	/** 학생 정보 수정 페이지 */
	@RequestMapping(value="/student/stud_edit.do", method=RequestMethod.GET)
	public ModelAndView StudEdit(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int studno = web.getInt("studno");
		logger.debug("studno=" + studno);
		
		if(studno == 0){
			return web.redirect(null, "교수번호가 없습니다.");
		}
		
		//전달된 파라미터를 beans에 저장한다.
		StudentDepartment student = new StudentDepartment();
		student.setStudno(studno);
		
		/** 2) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		StudentDepartment item = null;
		List<Department> deptList = null;
		List<Professor> profList = null;
		try {
			item = studentJoinService.getStudentJoinItem(student);
			deptList = departmentService.getDepartmentList(null);
			profList = professorService.getProfessorList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 3) view 처리하기 */
		model.addAttribute("item", item);
		model.addAttribute("deptList", deptList);
		model.addAttribute("profList", profList);
		return new ModelAndView("student/stud_edit");
	}
	
	/** 학생 정보 수정 처리 페이지 (action페이지로 사용된다.) */
	@RequestMapping(value="/student/stud_edit_ok.do", method=RequestMethod.POST)
	public ModelAndView StudEditOk(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		//input 태그의 name 속성에 명시된 값을 사용한다.
		int studno = web.getInt("studno");
		String name = web.getString("name");
		String userid = web.getString("userid");
		int grade = web.getInt("grade");
		String idnum = web.getString("idnum");
		String birthdate = web.getString("birthdate");
		String tel = web.getString("tel");
		int height = web.getInt("height");
		int weight = web.getInt("weight");
		int deptno = web.getInt("deptno");
		int profno = web.getInt("profno");
						
		//전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("studno=" + studno);
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("grade=" + grade);
		logger.debug("idnum=" + idnum);
		logger.debug("birthdate=" + birthdate);
		logger.debug("tel=" + tel);
		logger.debug("height=" + height);
		logger.debug("weight=" + weight);
		logger.debug("deptno=" + deptno);
		logger.debug("profno=" + profno);

		/** 2) 필수 항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
		if(name == null){
			return web.redirect(null, "이름을 입력하세요.");					
		}				
		if(userid == null){				
			return web.redirect(null, "아이디를 입력하세요.");
		}				
		if(grade == 0) {
			return web.redirect(null, "학년을 입력하세요.");
	 	}
	 	
	 	if(idnum == null) {
	 		return web.redirect(null, "주민등록번호를 입력하세요.");
	 	}
	 	
	 	if(birthdate == null) {
	 		return web.redirect(null, "생년월일을 입력하세요.");
	 	}

		if(tel == null) {
			return web.redirect(null, "전화번호를 입력하세요.");
	 	}

		if(height == 0) {
			return web.redirect(null, "키를 입력하세요.");
	 	}
		
		if(weight == 0) {
			return web.redirect(null, "몸무게를  입력하세요.");
	 	}
	 	
	 	if(deptno == 0) {
	 		return web.redirect(null, "학과번호를 입력하세요.");
	 	}
	
		/** 3) 저장을 위한 javaBeans 구성하기 */
	 	Student student = new Student();
		student.setStudno(studno);
		student.setName(name);
		student.setUserid(userid);
		student.setGrade(grade);
		student.setIdnum(idnum);
		student.setBirthdate(birthdate);
		student.setTel(tel);
		student.setHeight(height);
		student.setWeight(weight);
		student.setDeptno(deptno);
		student.setProfno(profno);
		
		
		/** 4) Service를 통한 SQL 수행 */
		try{
			studentService.editStudent(student);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 5) 결과를 확인하기 위한 페이지로 이동하기 */
		String url = web.getRootPath() + "/student/stud_view.do?studno=" + student.getStudno();
		return web.redirect(url,"수정되었습니다.");
	}
	
}

