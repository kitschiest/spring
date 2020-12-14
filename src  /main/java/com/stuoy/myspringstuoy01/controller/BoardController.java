package com.stuoy.myspringstuoy01.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.stuoy.myspringstuoy01.model.domain.Board;
import com.stuoy.myspringstuoy01.model.service.BoardService;

@Controller
@RequestMapping(value="/board") //이렇게 또 걸어도 된다.
public class BoardController {
	@Autowired
	private BoardService bService;
	
	//게시글 작성 페이지
	@RequestMapping(value="/writeForm.do", method = RequestMethod.GET)
	public String boardInsertForm(ModelAndView mv) {
		return "board/writeForm"; //view페이지에서 작성 후 form action = "bInsert.do" 로 들어오도록 설계.
		
	}
	
	//작성된 글을 insert
	@RequestMapping(value="/bInsert.do", method = RequestMethod.POST)
	public ModelAndView boardInsert(Board b, @RequestParam(name="upfile") MultipartFile report, HttpServletRequest request, ModelAndView mv) {
		
		//첨부파일 저장
		if(report != null && !report.equals("")) {
			saveFile(report, request);
		}
		b.setBoard_file(report.getOriginalFilename());
		
		bService.insertBoard(b);
		mv.setViewName("redirect:bList.do");
		// insertBoard에 성공했다면   !!!
		//View페이지로 이동하는 것이 아니라 컨트롤러 url 중 "게시글 리스트 select로 이동" 하는 "/bList.do"
		return mv;
		
		//실패하면
		//mv.setViewName("errorPage");
		
	}
	// 게시글 리스트 select
	@RequestMapping(value="/bList.do")
	public ModelAndView boardListService(ModelAndView mv) {
		mv.addObject("list", bService.selectList());
		mv.setViewName("board/blist");    // board/blist View페이지가 보여짐
		return mv;
	}
		
	//게시글 detail
	//bDetail.do?board_num=${vo.board_num}&page=${currentPage}
	@RequestMapping(value="/bDetail.do")
	public ModelAndView boardDetail(@RequestParam(name = "board_num") String board_num, 
			@RequestParam(name="page", defaultValue="1") int page, ModelAndView mv) { //첫 번째 인자에서 가져온거 변환을 위한 인자이다.
		//조회수 하나 올리는 코드
		
		
		//board_num을 가지고 탐색
		
		mv.addObject("board", bService.selectOne(board_num));
		//어디로 갈꺼냐
		mv.setViewName("board/boardDetail");
		return mv;
		
	} //위 아래 인자값이 달라서 메소드 이름 같아도 괜찮대****************************
	
	@RequestMapping(value="/bRenew.do")
	public ModelAndView boardDetail(@RequestParam(name = "board_num") String board_num, ModelAndView mv) { //첫 번째 인자에서 가져온거 변환을 위한 인자이다. //자바 빈즈 덕에 Board b 그대로 가지고 들어올 수 있다
		//조회수 하나 올리는 코드
		//board_num을 가지고 탐색
		mv.addObject("board", bService.selectOne(board_num)); 
		//어디로 갈꺼냐
		mv.setViewName("board/boardRenew");
		return mv;
		
	}
	
	@RequestMapping(value="/bUpdate.do", method=RequestMethod.POST)
	public ModelAndView boardUpdate(Board b, @RequestParam(name="upfile") MultipartFile report, HttpServletRequest request, ModelAndView mv) {
		//첨부파일 저장
		if(report != null && !report.equals("")) {
			removeFile(b.getBoard_file(), request); //기존 파일 삭제도 해야해
			saveFile(report, request);
			b.setBoard_file(report.getOriginalFilename());
		}
		if(bService.updateBoard(b)!=null) {
			mv.addObject("board_num", bService.updateBoard(b).getBoard_num()); //교재 방법인데, 쌤이 여기에 대한 의문이 든다고 하심. (b가 null이 가능성이 있으면, 이렇게 하면 안돼) > 그래서 if절로 묶었다.
			mv.setViewName("redirect:bDetail.do");			
		} else {
			//이전화면으로 이동
		}
		
		// insertBoard에 성공했다면   !!!
		//View페이지로 이동하는 것이 아니라 컨트롤러 url 중 "게시글 리스트 select로 이동" 하는 "/bList.do"
		return mv;
		
		//실패하면
		//mv.setViewName("errorPage");
		
	}
	
	@RequestMapping(value="/bDelete.do", method=RequestMethod.GET)
	public ModelAndView boadrDelete(@RequestParam(name = "board_num") String board_num,
			@RequestParam(name = "page", defaultValue = "1") int page, HttpServletRequest request, ModelAndView mv) {
		try {
			Board b = bService.selectOne(board_num); 
			removeFile(b.getBoard_file(),request);
			bService.deleteBoard(board_num);
//			mv.addObject("currentPage", page);  지금은 페이지 없어서,,
			System.out.println(board_num+"번 째 게시글이 삭제되었습니다.");
			mv.setViewName("redirect:bList.do");
			} catch (Exception e) { 
				mv.addObject("msg", e.getMessage()); mv.setViewName("errorPage");
			}
			return mv;
	}
	
	private void saveFile(MultipartFile report, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdir(); // 폴더가 없다면 생성한다.
		}
		String filePath = null;
		try {
			// 파일 저장
			System.out.println(report.getOriginalFilename() + "을 저장합니다.");
			System.out.println("저장 경로 : " + savePath);

			filePath = folder + "\\" + report.getOriginalFilename();
			report.transferTo(new File(filePath)); // 파일을 저장한다
			System.out.println("파일 명 : " + report.getOriginalFilename());
			System.out.println("파일 경로 : " + filePath);
			System.out.println("파일 전송이 완료되었습니다.");
		} catch (Exception e) {
			System.out.println("파일 전송 에러 : " + e.getMessage());
		}
	}
	
	private void removeFile(String board_file, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		String filePath = savePath + "\\" + board_file;
		try { //삭제 시에 오류 날 가능성 다분해서 try - catch문
			System.out.println(board_file + "을 삭제합니다.");
			System.out.println("기존 저장 경로: "+filePath);
		File delFile = new File(filePath); //찾아 나온 file을 delFile에 넣는다.
		delFile.delete();
		} catch(Exception e) {
			System.out.println("파일 삭제 에러: " + e.getMessage());
		}
		
		
	}
	

}
