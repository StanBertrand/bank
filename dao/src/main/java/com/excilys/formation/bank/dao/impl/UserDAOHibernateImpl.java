package com.excilys.formation.bank.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.excilys.formation.bank.bean.User;
import com.excilys.formation.bank.dao.UserDAO;

@Repository("userDAO")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserDAOHibernateImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("test");
		System.out.println(this.sessionFactory.getCurrentSession() == null);
		User user = (User) this.sessionFactory.getCurrentSession().get(
				User.class, username);
		System.out.println("test2");
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found");
		}
		return user;
	}

}
