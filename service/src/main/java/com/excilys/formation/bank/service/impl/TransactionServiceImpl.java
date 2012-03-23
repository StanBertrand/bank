package com.excilys.formation.bank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.bank.bean.Etat;
import com.excilys.formation.bank.bean.Transaction;
import com.excilys.formation.bank.dao.TransactionDAO;
import com.excilys.formation.bank.service.TransactionService;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionDAO transactionDAO;

	@Transactional(readOnly = true)
	@Override
	public final Transaction getTransactionById(Integer transactionId) {
		return transactionDAO.getTransactionById(transactionId);
	}

	@Override
	public final void insert(Transaction transaction) {
		transactionDAO.insert(transaction);
	}

	@Override
	public final void update(Transaction transaction, Etat etatType) {
		transaction.setEtat(etatType);
		transactionDAO.update(transaction);

	}

}
