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
import study.spring.hellospring.model.ProfessorDepartment;
import study.spring.hellospring.service.DepartmentService;
import study.spring.hellospring.service.ProfessorJoinService;
import study.spring.hellospring.service.ProfessorService;
import study.spring.helper.PageHelper;
import study.spring.helper.WebHelper;

@Controller
public class ProfessorController {
	
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);
	
	//--> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	//-->import study.spring.helper.PageHelper;
	@Autowired
	PageHelper page;
	
	//목록, 상세보기에서 사용할 서비스 객체 --> Department와의 Join처리
	//-->import study.spring.hellospring.service.ProfessorService;
	@Autowired
	ProfessorJoinService professorJoinService;
	
	//등록 삭제 수정 에서 사용할 서비스 객체
	//-->import study.spring.hellospring.service.DepartmentService;
	@Autowired
	ProfessorService professorService;
	
	//등록, 수정시에 소속학과에 대한 드롭다운을 구현하기 위한 서비스객체
	//-->import study.spring.hellospring.service.DepartmentService;
	@Autowired
	DepartmentService departmentService;
	
	/** 교수 목록 페이지 */
	@RequestMapping(value="/professor/prof_list.do", method=RequestMethod.GET)
	public ModelAndView ProfList(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		//파라미터를 저장할 Beans
		ProfessorDepartment professor = new ProfessorDepartment();
		
		//검색어 파라미터 받기 + Beans 설정
		String keyword = web.getString("keyword", "");
		professor.setName(keyword);
		
		//현재 페이지 번호에 대한 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2) 페이지 번호 구현하기 */
		//전체 데이터 수 조회하기
		int totalCount = 0;
		try{
			totalCount = professorJoinService.getProfessorCount(professor);
		} catch(Exception e){						
			return web.redirect(null, e.getLocalizedMessage()); 
		}
		
		//페이지 번호에 대한 연산 수행 후 조회조건값 지정을 위한 Beans에 추가하기
		page.pageProcess(nowPage, totalCount, 10, 5);
		professor.setLimitStart(page.getLimitStart());
		professor.setListCount(page.getListCount());
		
		/** 3) Service를 통한 SQL 수행 */
		//조회 결과를 저장하기 위한 객체
		List<ProfessorDepartment> list = null;
		try{
			list = professorJoinService.getProfessorJoinList(professor);
		}catch(Exception e){
		 return web.redirect(null, e.getLocalizedMessage());
		}		
		
		/** 4) View 처리하기 */
		//조회결과를 View에게 전달한다.
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		return new ModelAndView("professor/prof_list");
	}
	
	/** 교수 정보 상세보기 페이지 */
	@RequestMapping(value="/professor/prof_view.do", method=RequestMethod.GET)
	public ModelAndView ProfView(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int profno = web.getInt("profno");
		logger.debug("profno=" + profno);
		
		if(profno == 0){
			return web.redirect(null, "교수번호가 없습니다.");
		}
		
		//전달된 파라미터를 beans에 저장한다.
		ProfessorDepartment professor = new ProfessorDepartment();
		professor.setProfno(profno);
		
		/** 2) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		ProfessorDepartment item = null;
		try{
			item = professorJoinService.getProfessorJoinItem(professor);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());	
		}
		
		/** 3) view 처리하기 */
		model.addAttribute("item", item);
		
		return new ModelAndView("professor/prof_view");
	}
	
	/** 교수 등록 페이지 */
	@RequestMapping(value="/professor/prof_add.do", method=RequestMethod.GET)
	public ModelAndView ProfAdd(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 2) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Department> deptList = null;
		try {
			deptList = departmentService.getDepartmentList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 3) view 처리하기 */
		model.addAttribute("deptList", deptList);
		
		return new ModelAndView("professor/prof_add");
	}
	
	/** 교수 등록 처리  페이지(action 페이지로 사용된다.) */
	@RequestMapping(value="/professor/prof_add_ok.do", method=RequestMethod.POST)
	public ModelAndView ProfAddOk(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		//input 태그의 name 속성에 명시된 값을 사용한다.
		String name = web.getString("name");
		String userid = web.getString("userid");
		String position = web.getString("position");
		int sal = web.getInt("sal");
		int comm = web.getInt("comm");
		String hiredate = web.getString("hiredate");
		int deptno = web.getInt("deptno");
				
		//전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("position=" + position);
		logger.debug("sal=" + sal);
		logger.debug("comm=" + comm);
		logger.debug("hiredate=" + hiredate);
		logger.debug("deptno=" + deptno);
		
		/** 2) 필수 항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
				if(name == null){
					return web.redirect(null, "이름을 입력하세요.");					
				}				
				if(userid == null){				
					return web.redirect(null, "아이디를 입력하세요.");
				}				
				if(position == null){					
					return web.redirect(null, "직급을 입력하세요.");
				}				
				if(sal == 0){					
					return web.redirect(null, "급여를 입력하세요.");
				}				
				if(hiredate == null){					
					return web.redirect(null, "입사일을 입력하세요.");
				}				
				if(deptno == 0){					
					return web.redirect(null, "학과번호를 입력하세요.");
				}
		
		/** 3) 저장을 위한 javaBeans 구성하기 */
		Professor professor = new Professor();
		professor.setName(name);
		professor.setUserid(userid);
		professor.setPosition(position);
		professor.setSal(sal);
		professor.setComm(comm);
		professor.setHiredate(hiredate);
		professor.setDeptno(deptno);				
				
		/** 4) Service를 통한 SQL 수행 */
		try{
			professorService.addProfessor(professor);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 5) 결과를 확인하기 위한 페이지로 이동하기 */
		String url = web.getRootPath() + "/professor/prof_view.do?profno=" + professor.getProfno();
		return web.redirect(url,"저장되었습니다.");
	}
	
	/** 교수 정보 삭제 페이지 */
	@RequestMapping(value="/professor/prof_delete.do", method=RequestMethod.GET)
	public ModelAndView ProfDelete(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int profno = web.getInt("profno");
		logger.debug("profno=" + profno);
		
		if(profno == 0){
			return web.redirect(null, "교수번호가 없습니다.");
		}
		
		//전달된 파라미터를 beans에 저장한다.
		Professor professor = new Professor();
		professor.setProfno(profno);
		
		/** 2) Service를 통한 SQL 수행 */
		try{
			professorService.deleteProfessor(professor);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());	
		}
		
		/** 3) 목록페이지로 이동 */
		return web.redirect(web.getRootPath() + "/professor/prof_list.do", "삭제되었습니다.");
	}
	
	/** 교수 정보 수정 페이지 */
	@RequestMapping(value="/professor/prof_edit.do", method=RequestMethod.GET)
	public ModelAndView ProfEdit(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int profno = web.getInt("profno");
		logger.debug("profno=" + profno);
		
		if(profno == 0){
			return web.redirect(null, "교수번호가 없습니다.");
		}
		
		//전달된 파라미터를 beans에 저장한다.
		ProfessorDepartment professor = new ProfessorDepartment();
		professor.setProfno(profno);
		
		/** 2) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		ProfessorDepartment item = null;
		List<Department> deptList = null;
		try {
			item = professorJoinService.getProfessorJoinItem(professor);
			deptList = departmentService.getDepartmentList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 3) view 처리하기 */
		model.addAttribute("item", item);
		model.addAttribute("deptList", deptList);
				
		return new ModelAndView("professor/prof_edit");
	}
		
	
	/** 교수 정보 수정 처리 페이지 (action페이지로 사용된다.) */
	@RequestMapping(value="/professor/prof_edit_ok.do", method=RequestMethod.POST)
	public ModelAndView ProfEditOk(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		//input 태그의 name 속성에 명시된 값을 사용한다.
		int profno = web.getInt("profno");
		String name = web.getString("name");
		String userid = web.getString("userid");
		String position = web.getString("position");
		int sal = web.getInt("sal");
		int comm = web.getInt("comm");
		String hiredate = web.getString("hiredate");
		int deptno = web.getInt("deptno");
						
		//전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("profno=" + profno);
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("position=" + position);
		logger.debug("sal=" + sal);
		logger.debug("comm=" + comm);
		logger.debug("hiredate=" + hiredate);
		logger.debug("deptno=" + deptno);

		/** 2) 필수 항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
		if(profno == 0){					
			return web.redirect(null, "교수번호를 입력하세요.");
		}
		if(name == null){
			return web.redirect(null, "이름을 입력하세요.");					
		}				
		if(userid == null){				
			return web.redirect(null, "아이디를 입력하세요.");
		}				
		if(position == null){					
			return web.redirect(null, "직급을 입력하세요.");
		}				
		if(sal == 0){					
			return web.redirect(null, "급여를 입력하세요.");
		}				
		if(hiredate == null){					
			return web.redirect(null, "입사일을 입력하세요.");
		}				
		if(deptno == 0){					
			return web.redirect(null, "학과번호를 입력하세요.");
		}
	
		/** 3) 저장을 위한 javaBeans 구성하기 */
		Professor professor = new Professor();
		professor.setProfno(profno);
		professor.setName(name);
		professor.setUserid(userid);
		professor.setPosition(position);
		professor.setSal(sal);
		professor.setComm(comm);
		professor.setHiredate(hiredate);
		professor.setDeptno(deptno);
		
		
		/** 4) Service를 통한 SQL 수행 */
		try{
			professorService.editProfessor(professor);
		} catch(Exception e){
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 5) 결과를 확인하기 위한 페이지로 이동하기 */
		String url = web.getRootPath() + "/professor/prof_view.do?profno=" + professor.getProfno();
		return web.redirect(url,"수정되었습니다.");
	}
}

