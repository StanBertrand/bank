package com.excilys.formation.webservicers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.dto.CompteDTO;

/**
 * UserServiceRs interface.
 * 
 * @author excilys
 * 
 */

@Path("/UserService/")
@Produces("application/xml")
public interface UserServiceRs {

	/**
	 * Get the compte by username.
	 * 
	 * @param login
	 *            the login
	 * @return a set of compte
	 * @throws UsernameNotFoundException
	 */
	@GET
	@Path("/myaccounts/{login}")
	List<CompteDTO> getComptesByUsername(@PathParam("login") String login);

	/**
	 * Get a specific account of a user.
	 * 
	 * @param login
	 *            the login of the user
	 * @param id
	 *            the id of the account
	 * @return the account (null if not found)
	 */
	@GET
	@Path("/myaccount/{login}/{id}/")
	CompteDTO getCompteByUsernameAndAccountId(@PathParam("login") String login,
			@PathParam("id") String id);

}
