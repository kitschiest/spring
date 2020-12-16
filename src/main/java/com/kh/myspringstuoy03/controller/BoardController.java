package com.kh.myspringstuoy03.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.myspringstuoy03.model.domain.Board;
import com.kh.myspringstuoy03.model.service.BoardReplyService;
import com.kh.myspringstuoy03.model.service.BoardService;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	@Autowired
	private BoardService bService;

	@Autowired
	private BoardReplyService brService;
	public static final int LIMIT = 10;

	// 게시글 작성 페이지
	@RequestMapping(value = "/writeForm.do", method = RequestMethod.GET)
	public String boardInsertForm(ModelAndView mv) {
		return "board/writeForm"; // view페이지에서 작성 후 form action = "bInsert.do" 로 들어오도록 설계.
	}

	// 작성된 글을 insert
	@RequestMapping(value = "/bInsert.do", method = RequestMethod.POST)
	public ModelAndView boardInsert(Board b, @RequestParam(name = "upfile") MultipartFile report,
			HttpServletRequest request, ModelAndView mv) {

		// 첨부파일을 저장하는 코드
		if (report != null && !report.equals("")) {
			saveFile(report, request);
		}
		b.setBoard_file(report.getOriginalFilename());

		bService.insertBoard(b);
		mv.setViewName("redirect:bList.do");
		return mv;
	}

	// 게시글 리스트 출력
	@RequestMapping(value = "/bList.do")
	public ModelAndView boardListService(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "keyword", required = false) String keyword, ModelAndView mv) {
		try {
			int currentPage = page;
			// 한 페이지당 출력할 목록 갯수
			int listCount = bService.listCount();
			int maxPage = (int) ((double) listCount / LIMIT + 0.9);
			if (keyword != null && !keyword.equals(""))
				mv.addObject("list", bService.selectOne(keyword));
			else
				mv.addObject("list", bService.selectList(currentPage, LIMIT));
			mv.addObject("currentPage", currentPage);
			mv.addObject("maxPage", maxPage);
			mv.addObject("listCount", listCount);
			mv.setViewName("board/blist");
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("errorPage");
		}
		return mv;
	}

	// 게시글 Detail
	@RequestMapping(value = "bDetail.do", method = RequestMethod.GET)
	public ModelAndView boardDetail(@RequestParam(name = "board_num") String board_num,
			@RequestParam(name = "page", defaultValue = "1") int page, ModelAndView mv) {
		try {
			int currentPage = page;
// 한 페이지당 출력할 목록 갯수
			mv.addObject("board", bService.selectBoard(0, board_num));
			mv.addObject("commentList", brService.selectBoardReplyAll(board_num));
			mv.addObject("currentPage", currentPage);
			mv.setViewName("board/boardDetail");
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("errorPage");
		}
		return mv;
	}

	@RequestMapping(value = "bRenew.do", method = RequestMethod.GET)
	public ModelAndView boardDetail(@RequestParam(name = "board_num") String board_num, ModelAndView mv) {
		try {
			mv.addObject("board", bService.selectBoard(1, board_num));
			mv.setViewName("board/boardRenew");
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("errorPage");
		}
		return mv;
	}

	@RequestMapping(value = "/bUpdate.do", method = RequestMethod.POST)
	public ModelAndView boardUpdate(Board b, @RequestParam(name = "upfile") MultipartFile report,
			HttpServletRequest request, ModelAndView mv) {
		if (report != null && !report.equals("")) {
			removeFile(b.getBoard_file(), request);
			saveFile(report, request);
			b.setBoard_file(report.getOriginalFilename());
		}

		if (bService.updateBoard(b) != null) {
			mv.addObject("board_num", bService.updateBoard(b).getBoard_num());
			mv.setViewName("redirect:bDetail.do");
		} else {
			// 이전화면으로 이동?
		}

		return mv;
	}

	@RequestMapping(value = "/bDelete.do", method = RequestMethod.GET)
	public ModelAndView boardDelete(@RequestParam(name = "board_num") String board_num,
			@RequestParam(name = "page", defaultValue = "1") int page, HttpServletRequest request, ModelAndView mv) {
		try {
			Board b = bService.selectOne(board_num);
			removeFile(b.getBoard_file(), request);
			bService.deleteBoard(board_num);
			System.out.println(board_num + "번 째 게시글이 삭제되었습니다.");
			mv.setViewName("redirect:bList.do");
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("errorPage");

		}
		return mv;

	}

	private void saveFile(MultipartFile report, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdir();
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
		try { // 삭제 시에 오류 날 가능성 다분해서 try - catch문
			System.out.println(board_file + "을 삭제합니다.");
			System.out.println("기존 저장 경로: " + filePath);
			File delFile = new File(filePath); // 찾아 나온 file을 delFile에 넣는다.
			delFile.delete();
		} catch (Exception e) {
			System.out.println("파일 삭제 에러: " + e.getMessage());
		}

	}

}
