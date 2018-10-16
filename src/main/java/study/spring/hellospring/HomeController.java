package study.spring.hellospring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 컨트롤러를 구성하는 클래스 */
// 이 클래스가 컨트롤러로 동작하도록 어노테이션을 설정한다.
@Controller
public class HomeController {
	
	// log4j가 기본으로 포함되어 있다.
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 페이지를 구성하는 메서드
	 * @param locale : 국가설정
	 * @param model : View에게 전달할 데이터의 참조객체
	 * @return String : View의 파일이름
	 */
	//value --> 이 메서드의 URL, method --> 이 메서드가 응답할 요청방식
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		//로그를 출력한다.
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//현재 날짜를 국가설정(locale)에 맞추어 생성한다.
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		//model에게 데이터를 전달한다.
		model.addAttribute("serverTime", formattedDate );
		
		//view파일이름을 리턴한다.
		return "home";
	}
	
}
