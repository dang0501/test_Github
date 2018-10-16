package study.spring.hellospring;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import study.spring.helper.FileInfo;
import study.spring.helper.UploadHelper;
import study.spring.helper.WebHelper;

@Controller
public class UploadController {

	/** 객체 주입받기 */
	@Autowired
	WebHelper web;
	
	@Autowired
	UploadHelper upload;

	/** 업로드 폼 */
	@RequestMapping(value = "/upload/upload.do", method = RequestMethod.GET)
	public String upload(Locale locale, Model model) {
		// HTML만 표시할 것이므로 특별한 처리를 하지 않는다.
		return "upload/upload";
	}

	/** 업로드 처리 페이지 */
	@RequestMapping(value = "/upload/upload_ok.do", method = RequestMethod.POST)
	public ModelAndView uploadOk(Locale locale, Model model) {
		web.init();
		
		// 업로드 수행
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			e.printStackTrace();
			return web.redirect(null, "업로드 된 파일 저장에 실패했습니다.");
		}
		
		// 업로드를 통해 구분된 파라미터 컬렉션 받기
		List<FileInfo> fileList = upload.getFileList();
		Map<String, String> paramMap = upload.getParamMap();
		
		// 컬렉션에서 파라미터 추출하기
		String memo = paramMap.get("memo");
		
		// View에게 업로드 결과 전달하기
		model.addAttribute("memo", memo);
		model.addAttribute("fileList", fileList);
		
		return new ModelAndView("upload/upload_ok");
	}

}
