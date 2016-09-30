package com.imooc.page.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;
import com.imooc.page.server.Constant;
import com.imooc.page.server.JdbcSqlStudentServiceImpl;
import com.imooc.page.server.StudentService;

public class JdbcSqlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private StudentService studentService = new JdbcSqlStudentServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����request��Ĳ���
		
		//ѧ������
		String stuName = request.getParameter("stuName");
		
		//��ȡѧ���Ա�
		int gender = Constant.DEFAULT_GENDER;
		String genderStr = request.getParameter("gender");
		if(genderStr!=null && !"".equals(genderStr.trim())){
			 gender = Integer.parseInt(genderStr);
		}
		
		//��ʾ�ڼ�ҳ����
		int pageNum = Constant.DEFAULT_PAGE_NUM;
		String pageNumStr = request.getParameter("pageNum");
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		//ÿҳ��ʾ��������¼
		int pageSize = Constant.DEFAULT_PAGE_SIZE;
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		//��װ��ѯ����
		 Student searchModel = new Student();
		 searchModel.setStuName(stuName);
		 searchModel.setGender(gender);
		 
		//����service��ȡ��ѯ���
		Pager<Student> result = studentService.findStudent(searchModel, pageNum, pageSize);
		//���ؽ����ҳ��
		request.setAttribute("result", result);
		request.getRequestDispatcher("/jdbcSqlStudent.jsp").forward(request, response);;
	}
}
