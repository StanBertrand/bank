package com.excilys.formation.bank.service.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.bank.bean.Compte;
import com.excilys.formation.bank.bean.Etat;
import com.excilys.formation.bank.bean.Operation;
import com.excilys.formation.bank.bean.OperationType;
import com.excilys.formation.bank.bean.Transaction;
import com.excilys.formation.bank.bean.TransactionCategorie;
import com.excilys.formation.bank.dao.CompteDAO;
import com.excilys.formation.bank.dao.OperationDAO;
import com.excilys.formation.bank.dao.TransactionDAO;
import com.excilys.formation.bank.service.OperationCarteService;

/**
 * Implémentation de l'interface OperationCarteService.
 * 
 * @author excilys
 * 
 */
@Service("operationCarteService")
@Transactional
public class OperationCarteServiceImpl implements OperationCarteService {

	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private OperationDAO operationDAO;

	@Autowired
	private CompteDAO compteDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Transaction createOperationCarte(String login,
			String compteDebiteurId, long montant, String libelle) {

		Compte compteDebiteur = compteDAO.loadCompteByUsernameAndCompteId(
				login, compteDebiteurId);
		if (compteDebiteur != null) {
			if (compteDebiteur.hasCarte()) {

				Transaction transaction = createTransaction(compteDebiteur,
						libelle);

				createOperations(compteDebiteur, transaction, montant);

				compteDAO.updateSolde(compteDebiteurId, -montant);
				return transaction;
			}
		}
		return null;
	}

	/**
	 * Création de l'opération associée à la transaction de type carte.
	 * 
	 * @param compteDebiteur
	 *            : le compte débiteur
	 * @param transaction
	 *            : la transaction associée
	 * @param montant
	 *            : le montant
	 */
	private void createOperations(Compte compteDebiteur,
			Transaction transaction, long montant) {
		Operation operationDebit = new Operation();

		operationDebit.setMontant(montant);

		operationDebit.setOperationType(OperationType.DEBIT);

		operationDebit.setTransaction(transaction);

		operationDebit.setCompte(compteDebiteur);

		operationDAO.insert(operationDebit);
	}

	/**
	 * Création d'une transaction de type carte.
	 * 
	 * @param compteDebiteur
	 *            : compte débiteur
	 * @param libelle
	 *            : libellé
	 * @return Transaction
	 */
	private Transaction createTransaction(Compte compteDebiteur, String libelle) {

		Transaction transaction = new Transaction();
		Date now = new Date(DateTime.now().getMillis());
		transaction.setDateInit(now);
		transaction.setDateValid(now);

		if ("".equals(libelle)) {
			transaction.setLibelle("opération carte depuis "
					+ compteDebiteur.getCompteId());
		} else {
			transaction.setLibelle(libelle);
		}
		transaction.setEtat(Etat.VALIDATED);

		transaction.setTransactionCategorie(TransactionCategorie.CARTE);

		// Insertion de la transaction
		transactionDAO.insert(transaction);

		return transaction;
	}
}
