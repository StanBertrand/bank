package com.excilys.formation.webservice;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.excilys.formation.bank.bean.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:contextTest/applicationContext-*.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class TestWebService {

	@Autowired
	private VirementServiceWs virementServiceWs;

	@Autowired
	private UserServiceWs userServiceWs;

	@Test
	public void userServiceWsNotNull() {
		assertThat(userServiceWs).isNotNull();
	}

	@Test
	public void virementServiceWsNotNull() {
		assertThat(virementServiceWs).isNotNull();
	}

	@Test
	public void getUserByLogin() {
		String login = "robert";
		User user = userServiceWs.loadUserByUsername(login);
		assertThat(user.getLogin()).isEqualTo(login);

	}
}
