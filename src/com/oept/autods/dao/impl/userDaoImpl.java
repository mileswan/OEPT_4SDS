package com.oept.autods.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.oept.autods.dao.userDao;
import com.oept.autods.model.Position;
import com.oept.autods.model.User;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author mwan
 *
 */
@Repository("userDao")
public class userDaoImpl implements userDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate get_jdbc11() {
		return jdbcTemplate;
	}
	public void set_jdbc11(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/* (non-Javadoc)
	 * @see com.oept.autods.cm.dao.userDao#insertUser(com.oept.autods.cm.model.User)
	 */
	@Override
	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		String sql = "insert into admin (NAME,PASSWORD) values (?,?)";

		Object[] params = new Object[] {
				user.get_NAME(),
				user.get_PASSWORD() };
		jdbcTemplate.update(sql,params);
		return true;
	}
	/**
	 * @author jjzhu
	 * 实现查询职位接口
	 */
	@Override
	public Map<String,Object> selectPosition(int id) {
//		// TODO Auto-generated method stub
		String sql = "select * from uic_user u,uic_position p where u.uic_position_id=p.uic_position_id and u.uic_user_id = ?;";
		Map<String,Object> map = new HashMap<String,Object>();
		map = jdbcTemplate.queryForMap(sql,id);
		return map;
	}
	
	/**
	 * @author jjzhu
	 * 读取全部职称
	 */
	@Override
	public List<Map<String,Object>> selectAllPosition() {
		// TODO Auto-generated method stub
		String sql = "select * from uic_position  where uic_position_id>1";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * @author jjzhu
	 * 更改职级
	 */
	public int updatePosition(String pid,String uid){
		String sql = "UPDATE uic_user set uic_position_id=? WHERE  uic_user_id=?";
		return jdbcTemplate.update(sql, pid,uid);
	}
}
