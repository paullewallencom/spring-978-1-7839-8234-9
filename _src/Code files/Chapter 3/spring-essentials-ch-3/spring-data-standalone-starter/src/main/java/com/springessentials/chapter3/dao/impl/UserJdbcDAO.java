package com.springessentials.chapter3.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springessentials.chapter3.dao.UserDAO;
import com.springessentials.chapter3.domain.File;
import com.springessentials.chapter3.domain.User;

@Repository("UserJdbcDAO")
@Transactional
public class UserJdbcDAO implements UserDAO {

//	@Autowired
	private JdbcTemplate jdbcTemplate;
/*
	public UserJdbcDAO() {
	}
*/
	@Autowired
	public UserJdbcDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(readOnly = true)
	public User findById(Long userId) {
		return jdbcTemplate.query(
				"SELECT NAME, USER_NAME, PASSWORD, DOB, PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME FROM TBL_USER WHERE ID = ?",
				new Object[] { userId }, new ResultSetExtractor<User>() {

					@Override
					public User extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return new User(userId, rs.getString("NAME"), rs.getString("USER_NAME"),
									rs.getString("PASSWORD"), rs.getDate("DOB"));
						} else {
							return null;
						}
					}

				});
	}

	@Transactional(readOnly = true)
	public User findByUserName(String userName) {
		return jdbcTemplate.queryForObject(
				"SELECT ID, NAME, USER_NAME, PASSWORD, DOB, PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME FROM TBL_USER WHERE USER_NAME = ?",
				new Object[] { userName }, new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new User(rs.getLong("ID"), rs.getString("NAME"), userName, rs.getString("PASSWORD"),
								rs.getDate("DOB"));
					}

				});
	}

	@Override
	public void createUser(User user) {

		
		/*jdbcTemplate.update(
				"INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) VALUES(?,?,?,?)",
				new Object[] { user.getName(), user.getUserName(), user.getPassword(), user.getDateOfBirth()});
*/
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement("INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) VALUES(?,?,?,?)", new String[]{"ID"});
			            ps.setString(1, user.getName());
			            ps.setString(2, user.getUserName());
			            ps.setString(3, user.getPassword());
			            ps.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
			            return ps;
			        }
			    }, keyHolder);
		user.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void createUsers(List<User> users) {
		int[] updateCounts = jdbcTemplate.batchUpdate(
				"INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) VALUES(?,?,?,?)",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, users.get(i).getName());
						ps.setString(2, users.get(i).getUserName());
						ps.setString(3, users.get(i).getPassword());
						ps.setDate(4, new java.sql.Date(users.get(i).getDateOfBirth().getTime()));
					}

					public int getBatchSize() {
						return users.size();
					}
				});
	}

	@Override
	public void deleteUser(User user) {
		jdbcTemplate.update("DELETE FROM TBL_USER WHERE ID = ?", user.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAllUsers() {
		return this.jdbcTemplate.query(
				"SELECT ID, NAME, USER_NAME, PASSWORD, DOB, PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME FROM TBL_USER ORDER BY ID DESC",
				new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User(rs.getLong("ID"), rs.getString("NAME"), rs.getString("USER_NAME"),
								rs.getString("PASSWORD"), rs.getDate("DOB"));
						Object objProfileImageId = rs.getObject("PROFILE_IMAGE_ID");
						if (objProfileImageId != null) {
							user.setProfileImage(new File(Long.valueOf(objProfileImageId.toString()),
									rs.getString("PROFILE_IMAGE_NAME")));
						}
						return user;
					}

				});
	}

	@Override
	public File addProfileImage(Long userId, String fileName) {
		User user = this.findById(userId);
		user.setProfileImage(new File(1000 + user.getId(), fileName));
		this.updateUser(userId, user);
		return user.getProfileImage();
	}

	@Override
	public void removeProfileImage(Long userId) {
		this.findById(userId).setProfileImage(null);
	}

	@Override
	public void updateUser(Long userId, User user) {
		String profileImageName = null;
		Long profileImageId = null;

		if (user.getProfileImage() != null && !StringUtils.isEmpty(user.getProfileImage().getFileName())) {
			profileImageName = user.getProfileImage().getFileName();
			profileImageId = user.getId();
		} else {
			user.setProfileImage(null);
		}

		jdbcTemplate.update(
				"UPDATE TBL_USER SET NAME = ?, USER_NAME = ?, PASSWORD = ?, DOB = ?, PROFILE_IMAGE_ID = ?, PROFILE_IMAGE_NAME = ? WHERE ID = ?",
				user.getName(), user.getUserName(), user.getPassword(), user.getDateOfBirth(), profileImageId,
				profileImageName, userId);
	}

}
