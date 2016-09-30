package com.imooc.page.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.page.dao.SublistStudentDaoImpl;
import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;
import com.imooc.page.server.Constant;
import com.imooc.page.server.StudentService;
import com.imooc.page.server.SublistStudentServiceImpl;

public class SublistServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private StudentService studentService = new SublistStudentServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//接收request里的参数
		
		//学生姓名
		String stuName = request.getParameter("stuName");
		
		//获取学生性别
		int gender = Constant.DEFAULT_GENDER;
		String genderStr = request.getParameter("gender");
		if(genderStr!=null && !"".equals(genderStr.trim())){
			 gender = Integer.parseInt(genderStr);
		}
		
		//显示第几页数据
		int pageNum = Constant.DEFAULT_PAGE_NUM;
		String pageNumStr = request.getParameter("pageNum");
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		//每页显示多少条记录
		int pageSize = Constant.DEFAULT_PAGE_SIZE;
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		//组装查询条件
		 Student searchModel = new Student();
		 searchModel.setStuName(stuName);
		 searchModel.setGender(gender);
		 
		//调用service获取查询结果
		Pager<Student> result = studentService.findStudent(searchModel, pageNum, pageSize);
		//返回结果到页面
		request.setAttribute("result", result);
		request.getRequestDispatcher("/sublistStudent.jsp").forward(request, response);;
	}
}
