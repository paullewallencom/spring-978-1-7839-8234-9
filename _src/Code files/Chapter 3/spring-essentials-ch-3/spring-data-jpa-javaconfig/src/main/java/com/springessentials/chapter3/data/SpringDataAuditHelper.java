package com.springessentials.chapter3.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.springessentials.chapter3.dao.UserDAO;
import com.springessentials.chapter3.domain.User;

@Component
public class SpringDataAuditHelper implements AuditorAware<User> {

	@Autowired
	private UserDAO userDAO;

	private User currentUser;

	/**
	 * TODO Currently this method just returns the first user in the database.
	 * Instead, it should return the user from the Spring Security Context.
	 */
	@Override
	public User getCurrentAuditor() {
		// Return the current user from the context somehow.
	    /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	      return null;
	    }

	    return ((User) authentication.getPrincipal()).getUser();
		*/
		
		
		
		if (currentUser == null) {
			List<User> allUsers = userDAO.findAll();
			if (CollectionUtils.isEmpty(allUsers)) {
				return null;
			}
			this.currentUser = allUsers.get(0);
		} else {
			return currentUser;
		}
		return this.currentUser;
	}

}
