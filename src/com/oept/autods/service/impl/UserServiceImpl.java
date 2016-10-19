package com.oept.autods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.autods.dao.userDao;
import com.oept.autods.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	private userDao userdao;

	//实现查询职位
	@Override
	public Map<String, Object> selectPosition(int id) {
		// TODO Auto-generated method stub
		return userdao.selectPosition(id);
	}

	/*
	 * 查询全部职称
	 */
	@Override
	public List<Map<String, Object>> selectAllPosition() {
		// TODO Auto-generated method stub
		return userdao.selectAllPosition();
	}

	/*
	 * 更改职级
	 */
	@Override
	public int updatePosition(String pid,String uid) {
		// TODO Auto-generated method stub
		return userdao.updatePosition(pid,uid);
	}
	
	
}
