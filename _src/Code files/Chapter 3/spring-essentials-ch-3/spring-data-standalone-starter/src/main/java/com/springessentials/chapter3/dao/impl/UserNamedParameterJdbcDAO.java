package com.springessentials.chapter3.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.springessentials.chapter3.dao.UserDAO;
import com.springessentials.chapter3.domain.File;
import com.springessentials.chapter3.domain.User;

@Repository("UserNamedParameterJdbcDAO")
public class UserNamedParameterJdbcDAO implements UserDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public UserNamedParameterJdbcDAO(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public User findById(Long userId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("USER_ID", userId);

		return jdbcTemplate.query(
				"SELECT NAME, USER_NAME, PASSWORD, DOB, PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME FROM TBL_USER WHERE ID = :USER_ID",
				namedParameters, new ResultSetExtractor<User>() {

					@Override
					public User extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							User user = new User(userId, rs.getString("NAME"), rs.getString("USER_NAME"),
									rs.getString("PASSWORD"), rs.getDate("DOB"));
							String profileImageName = rs.getString("PROFILE_IMAGE_NAME");
							if (!StringUtils.isEmpty(profileImageName)) {
								user.setProfileImage(new File(rs.getLong("PROFILE_IMAGE_ID"), profileImageName));
							}
							return user;
						} else {
							return null;
						}
					}

				});
	}

	public User findByUserName(String userName) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("USER_NAME", userName);
		return jdbcTemplate.queryForObject(
				"SELECT ID, NAME, USER_NAME, PASSWORD, DOB, PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME FROM TBL_USER WHERE USER_NAME = :USER_NAME",
				namedParameters, new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new User(rs.getLong("ID"), rs.getString("NAME"), userName, rs.getString("PASSWORD"),
								rs.getDate("DOB"));
					}

				});
	}

	@Override
	public void createUser(User user) {

		SqlParameterSource namedParameters = new MapSqlParameterSource("NAME", user.getName())
				.addValue("USER_NAME", user.getUserName()).addValue("PASSWORD", user.getPassword())
				.addValue("DOB", user.getDateOfBirth());

		jdbcTemplate.update(
				"INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) "
						+ "VALUES(:NAME, :USER_NAME, :PASSWORD, :DOB)",
				namedParameters);
		User savedUser = this.findByUserName(user.getUserName());
		user.setId(savedUser.getId());
	}

	@Override
	public void createUsers(List<User> users) {
		String sql = "INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) VALUES(:name,:userName,:password,:dateOfBirth)";
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(users.toArray());

		int[] updateCounts = jdbcTemplate.batchUpdate(sql, params);

	}

	@Override
	public void deleteUser(User user) {
		jdbcTemplate.update("DELETE FROM TBL_USER WHERE ID = :USER_ID",
				new MapSqlParameterSource("USER_ID", user.getId()));
	}

	@Override
	public List<User> findAllUsers() {
		return this.jdbcTemplate.query(
				"SELECT ID, NAME, USER_NAME, PASSWORD, DOB, PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME FROM TBL_USER ORDER BY NAME",
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

		SqlParameterSource namedParameters = new MapSqlParameterSource("NAME", user.getName())
				.addValue("USER_NAME", user.getUserName()).addValue("PASSWORD", user.getPassword())
				.addValue("DOB", user.getDateOfBirth()).addValue("PROFILE_IMAGE_ID", profileImageId)
				.addValue("PROFILE_IMAGE_NAME", profileImageName).addValue("USER_ID", userId);
		jdbcTemplate.update(
				"UPDATE TBL_USER SET NAME = :NAME, USER_NAME = :USER_NAME, PASSWORD = :PASSWORD, DOB = :DOB, PROFILE_IMAGE_ID = :PROFILE_IMAGE_ID, PROFILE_IMAGE_NAME = :PROFILE_IMAGE_NAME WHERE ID = :USER_ID",
				namedParameters);
	}

}
