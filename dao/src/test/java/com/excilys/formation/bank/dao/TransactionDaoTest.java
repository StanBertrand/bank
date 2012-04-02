package com.excilys.formation.bank.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.excilys.formation.bank.bean.Etat;
import com.excilys.formation.bank.bean.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:context/applicationContext*.xml",
		"classpath:contextTest/applicationContext-daoTest.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DataSetTestExecutionListener.class })
@DataSet("/datasets/dataSetTransactionOperationDao.xml")
@Transactional
@TransactionConfiguration
public class TransactionDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private TransactionDAO transactionDAO;

	private Transaction transaction;

	@Transactional(readOnly = true)
	@Before
	public final void init() {
		transaction = transactionDAO.getTransactionById(1);
	}

	@Test
	public final void getTransactionTest() {
		System.out.println(transaction.getEtat());
		assertThat(transaction.getEtat().name()).isEqualTo("VALIDATED");
	}

	@Test
	public void updateTransactionEtatTest() {
		transaction.setEtat(Etat.WAITING);
		transactionDAO.update(transaction);
		assertThat(transaction.getEtat()).isEqualTo(Etat.WAITING);

	}

	@Test
	public void insertTransactionTest() {

		Transaction transaction = new Transaction();
		transaction.setTransactionId(1);
		transaction.setEtat(Etat.VALIDATED);
		Date date = new Date();
		transaction.setDateValid(date);
		transaction.setDateInit(date);
		transactionDAO.insert(transaction);
		transaction = transactionDAO.getTransactionById(1);
		assertThat(transaction).isEqualTo(transaction);

	}
}
