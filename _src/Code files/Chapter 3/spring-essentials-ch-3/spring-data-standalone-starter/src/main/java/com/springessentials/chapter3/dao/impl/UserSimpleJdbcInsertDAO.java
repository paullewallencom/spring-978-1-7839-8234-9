package com.springessentials.chapter3.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.springessentials.chapter3.dao.UserDAO;
import com.springessentials.chapter3.domain.File;
import com.springessentials.chapter3.domain.User;

@Repository("UserSimpleJdbcInsertDAO")
public class UserSimpleJdbcInsertDAO implements UserDAO {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert simpleInsert;

	@Autowired
	public UserSimpleJdbcInsertDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("tbl_user").usingGeneratedKeyColumns("id");
	}

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

		Map<String, Object> parameters = new HashMap<>(4);
		parameters.put("name", user.getName());
		parameters.put("user_name", user.getUserName());
		parameters.put("password", user.getPassword());
		parameters.put("dob", user.getDateOfBirth());
		Number newId = simpleInsert.executeAndReturnKey(parameters);
		user.setId(newId.longValue());

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
