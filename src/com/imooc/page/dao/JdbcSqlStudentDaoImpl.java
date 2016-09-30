package com.imooc.page.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;
import com.imooc.page.server.Constant;
import com.imooc.page.util.jdbcUtil;

/**
 * 使用MySql数据库limit实现分页
 * @author hjc
 *
 */
public class JdbcSqlStudentDaoImpl implements StudentDao{

	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum,
			int pageSize) {
		
		Pager<Student> result = null;
		//存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		String stuName = searchModel.getStuName();
		int gender = searchModel.getGender();
		
		StringBuilder sql = new StringBuilder("select * from t_student where 1=1");
		StringBuilder countSql = new StringBuilder("select count(id) as totalRecord from t_student where 1=1");

		if(stuName!=null && !stuName.equals("")){
			sql.append("and stu_name like ?");
			countSql.append("and stu_name like ?");
			paramList.add("%"+ stuName +"%");
		}
		if(gender == Constant.GENDER_FEMALE || gender == Constant.GENDER_MALE){
			sql.append(" and gender = ?");
			countSql.append(" and gender = ?");
			paramList.add(gender);
		}
		
		//起始索引
		int fromIndex = pageSize * (pageNum-1);
		
		//使用limit关键字，实现分页
		sql.append(" limit "+fromIndex+", "+pageSize);
		
		//存放所有查询到的学生对象
		List<Student> studentList = new ArrayList<Student>();
		jdbcUtil util=null;
		try {
			util = new jdbcUtil();
			util.getConnection();//获取数据库连接
			
			//获取总记录数
			List<Map<String, Object>> countResult = util.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap  = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			//获取查询的学生记录
			List<Map<String, Object>> studentResult = util.findResult(sql.toString(), paramList);
			if(studentResult!=null){
				for(Map<String, Object> map : studentResult){
					Student s = new Student(map);
					studentList.add(s);
				}
			}
			
			//获取总页数
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			result = new Pager<Student>(pageSize,pageNum
					,totalRecord,totalPage,studentList);
		} catch (Exception e) {
			throw new RuntimeException("查询所有数据异常",e);
		}finally{
			if(util!=null){
				util.releaseConn();
			}
		}
		return result;
	}
	
}
