package com.photo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;
@WebServlet("/photo/*")
public class PhotoServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	private SessionInfo info;
	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		info = (SessionInfo)session.getAttribute("member");
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// 이미지파일 저장경로
		String root = session.getServletContext().getRealPath("/"); 
		pathname = root+File.separator+"uploads"+File.separator+"photo";
		File f = new File(pathname); // 폴더없으면 만들어줘
		if(! f.exists())
			f.mkdirs(); // 폴더 만들기
		
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			created(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			created_ok(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req,resp);
		} else if(uri.indexOf("update.do")!=-1){
			update(req,resp);
		} else if(uri.indexOf("update_ok.do")!=-1){
			update_ok(req,resp);
		}else if(uri.indexOf("delete.do")!=-1){
			delete(req,resp);
		}
	}
	
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		photoDAO dao = new photoDAO();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		int numPerPage=6;
		int dataCount, total_page;
		
		dataCount = dao.dataCount();
		
		total_page=util.pageCount(numPerPage, dataCount);
		if(current_page>total_page)
			current_page = total_page;
		
		int start=(current_page-1)*numPerPage+1;
		int end=current_page*numPerPage;
		
		List<photoDTO> list=dao.listBoard(start, end);
		String listUrl=cp+"/photo/list.do?";
		String articleUrl = cp+"/photo/article.do?page="+current_page;
		
		String paging=util.paging(current_page, total_page,listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);	
	    forward(req, resp, "/WEB-INF/views/photo/list.jsp");
	}
	
	private void created(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
	      req.setAttribute("mode", "created");
	      forward(req, resp, "/WEB-INF/views/photo/created.jsp");
	}
	
	private void created_ok(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String cp = req.getContextPath();
		
		String encType = "UTF-8";
		int maxSize = 5*1024*1024; // 5메가바이트
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize,encType, new DefaultFileRenamePolicy()); 
		// 받고, 경로, 크기,encType, 동일한파일이 잇을때  보호
		
		String saveFilename = mreq.getFilesystemName("upload");
		saveFilename = FileManager.doFilerename(pathname, saveFilename); // 파일이름을 년월일시분초로 바꾼다
		
		
		photoDAO dao = new photoDAO();
		photoDTO dto = new photoDTO();
		
		// db작업
		dto.setUserId(info.getUserId()); 
		dto.setSubject(mreq.getParameter("subject"));
		dto.setContent(mreq.getParameter("content"));
		dto.setImageFilename(saveFilename);
		
		dao.insertPhoto(dto);
		
		resp.sendRedirect(cp+"/photo/list.do");
	}
	
	private void article(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String cp = req.getContextPath();
		photoDAO dao = new photoDAO();
		
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		// 게시물 가져오기
		photoDTO dto=dao.readBoard(num);
		if(dto==null) { // 게시물이 없으면 다시 리스트로
			resp.sendRedirect(cp+"/photo/list.do?page="+page);
			return;
		}
					
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
					
					
					
					
		// JSP로 전달할 속성
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		
		forward(req, resp, "/WEB-INF/views/photo/article.jsp");
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setAttribute("mode", "update");
		forward(req, resp, "/WEB-INF/views/photo/created.jsp");
	}
	
	private void update_ok(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String cp = req.getContextPath();
		
		resp.sendRedirect(cp+"/photo/list.do");
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String cp = req.getContextPath();
		
		resp.sendRedirect(cp+"/photo/list.do");
	}
}
