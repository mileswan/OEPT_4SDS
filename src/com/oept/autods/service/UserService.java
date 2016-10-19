package com.oept.autods.service;

import java.util.List;
import java.util.Map;

/**
 * @author jjzhu
 * date:15/10/20
 */
public interface UserService {
	
	/*
	 * 查询职位接口
	 */
	public Map<String, Object> selectPosition(int id);
	
	/*
	 * 查询全部职称
	 */
	public List<Map<String,Object>> selectAllPosition();
	
	/*
	 * 更改职级
	 */
	public int updatePosition(String pid,String uid);
}
