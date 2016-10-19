/**
 * 
 */
package com.oept.autods.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.oept.autods.model.Position;
import com.oept.autods.model.User;

/**
 * @author mwan
 *
 */
public interface userDao {
	
	public void set_jdbc11(JdbcTemplate jdbcTemplate);
	
	public boolean insertUser(User user);
	
	/**
	 * @author jjzhu
	 * 查询职位接口
	 */
	public Map<String, Object> selectPosition(int id);
	
	/**
	 * @author jjzhu
	 * 读取全部职称
	 */
	public List<Map<String,Object>> selectAllPosition();
	
	/**
	 * @author jjzhu
	 * 更改职级
	 */
	public int updatePosition(String pid,String uid);

}
