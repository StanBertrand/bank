package com.excilys.formation.bank.web.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.bank.bean.Compte;
import com.excilys.formation.bank.service.UserService;
import com.excilys.formation.bank.service.VirementService;
import com.excilys.formation.bank.web.validator.VirementValidator;

@Controller
@RequestMapping("/secure/virement/{type}.html")
public class VirementController {

	@Autowired
	private UserService userService;

	@Autowired
	private VirementService virementService;

	@RequestMapping(method = RequestMethod.GET)
	public final String showVirementForm(ModelMap model,
			@PathVariable String type) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Set<Compte> comptes = userService.getComptesByUsername(userDetails
				.getUsername());
		model.put("comptes", comptes);
		model.put("type", type);
		VirementValidator virementValidator = new VirementValidator();
		model.put("virementValidator", virementValidator);

		return "virement";
	}

	@RequestMapping(method = RequestMethod.POST)
	public final String processVirementFormValidation(
			@Valid @ModelAttribute("virementValidator") VirementValidator virementValidator,
			BindingResult result, ModelMap model, @PathVariable String type) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		if (result.hasErrors()) {
			Set<Compte> comptes = userService.getComptesByUsername(userDetails
					.getUsername());
			model.put("comptes", comptes);
			model.put("type", type);
			model.put("virementValidator", virementValidator);
			return "virement";
		}

		return doVirement(virementValidator, type);
	}

	private final String doVirement(VirementValidator virementValidator,
			String type) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String login = userDetails.getUsername();
		try {
			virementService.createVirement(login,
					virementValidator.getCompteDebiteur(),
					virementValidator.getCompteCrediteur(),
					virementValidator.getLongMontant(),
					virementValidator.getLibelle());
		} catch (Exception e) {
			return new StringBuilder("redirect:/secure/virement/").append(type)
					.append(".html?error=1").toString();
		}
		StringBuilder stringBuilder = new StringBuilder(
				"redirect:/secure/account/0/");
		stringBuilder.append(virementValidator.getCompteDebiteur());
		stringBuilder.append(".html");
		return stringBuilder.toString();

	}

}
