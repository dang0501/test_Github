package study.spring.hellospring;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/** 컨트롤러 지정*/
@Controller
public class SessionController {
	/**
	 * 세션, 쿠키 저장을 위한 작성 페이지
	 * --> 이 페이지를 "/session/write.do" URL에 GET 방식으로 노출시킨다.
	 * */	
	@RequestMapping(value="/session/write.do", method=RequestMethod.GET)
	public String home(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** 세션값 처리*/
		//세션값은 request 내장객체를 통해서 HttpSession객체를 생성해야 접근할 수 있다.
		//-->Servlet과 동일함
		HttpSession session = request.getSession();
		String mySession = (String) session.getAttribute("my_session");
		if(mySession == null) {
			mySession = "";
		}
		
		/** 추출한 값 view에 전달*/
		model.addAttribute("my_session_value", mySession);
		
		//"/src/main/webapp/WEB-INF/views/session/write.jsp"파일을 View로 지정한다.
		return "session/write";
	}
	
	/** 세션을 저장하기 위한 action 페이지*/
	@RequestMapping(value= "/session/save.do", method = RequestMethod.POST)
	public String SessionSave(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="memo", defaultValue="") String memo) {
		
		//request 객체를 사용해서 세션 객체 만들기
		HttpSession session = request.getSession();
		
		if(memo.equals("")) {
			/** 입력내용이 없는 경우 세션 삭제 처리 */
			session.removeAttribute("my_session");
		}else {
			/** 입력 내용이 있다면 세션 저장 처리 */
			session.setAttribute("my_session", memo);
		}
		
		/** Spring 방식의 페이지 이동*/
		//Servlet의 response.sendRedirect(url)과 동일 */
		//->"/"부터 시작할 경우ContextPath는 자동으로 아펭 추가된다.
		String url ="/session/write.do";
		return "redirect:" + url;
	}
}
