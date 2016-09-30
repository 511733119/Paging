package com.imooc.page.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;
import com.imooc.page.server.Constant;
import com.imooc.page.util.jdbcUtil;

public class SublistStudentDaoImpl implements StudentDao{

	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum,
			int pageSize) {
		List<Student> allStudentList = getAllStudent(searchModel);
		Pager<Student> pager = new Pager<Student>(pageNum,pageSize,allStudentList);;
		return pager;
	}

	/**
	 * ģ�»�ȡ��������
	 * @param searchModel  ��ѯ����
	 * @return  ��ѯ���
	 */
	private List<Student> getAllStudent(Student searchModel){
		List<Student> result = new ArrayList<Student>();
		List<Object> paramList = new ArrayList<Object>();
		
		String stuName = searchModel.getStuName();
		int gender = searchModel.getGender();
		
		StringBuilder sql = new StringBuilder("select * from t_student where 1=1");
		
		if(stuName!=null && !stuName.equals("")){
			sql.append("and stu_name like ?");
			paramList.add("%"+ stuName +"%");
		}
		if(gender == Constant.GENDER_FEMALE || gender == Constant.GENDER_MALE){
			sql.append(" and gender = ?");
			paramList.add(gender);
		}
		
		jdbcUtil util=null;
		try {
			util = new jdbcUtil();
			util.getConnection();//��ȡ���ݿ�����
			List<Map<String, Object>> maplist = util.findResult(sql.toString(), paramList);
			if(maplist!=null){
				for(Map<String, Object> map : maplist){
					Student s = new Student(map);
					result.add(s);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("��ѯ���������쳣",e);
		}finally{
			if(util!=null){
				util.releaseConn();
			}
		}
		return result;
	}
	
}
