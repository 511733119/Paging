package com.imooc.page.server;

import com.imooc.page.dao.JdbcSqlStudentDaoImpl;
import com.imooc.page.dao.StudentDao;
import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;

public class JdbcSqlStudentServiceImpl implements StudentService {

	private StudentDao studentDao ;
	
	public JdbcSqlStudentServiceImpl() {
		studentDao = new JdbcSqlStudentDaoImpl();
	}
	
	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum,
			int pageSize) {
		Pager<Student> result = studentDao.findStudent(searchModel, pageNum, pageSize);
		return result;
	}

}
