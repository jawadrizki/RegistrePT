package com.poissontata.web;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.PaymentFournisseur;
import com.poissontata.entities.Reglement;
import com.poissontata.entities.VirementClient;
import com.poissontata.entities.AchatMarche;
import com.poissontata.entities.Bon;
import com.poissontata.entities.Client;
import com.poissontata.entities.Facture;
import com.poissontata.entities.Fournisseur;
import com.poissontata.entities.Operation;
import com.poissontata.metier.OperationManager;
import com.poissontata.metier.AchatMarcheManager;
import com.poissontata.metier.CollaborateurManager;
import com.poissontata.metier.ReglementManager;
import com.poissontata.metier.UserManager;




@Controller
public class MainController {
	@Autowired
	CollaborateurManager collaborateurManager;
	@Autowired
	ReglementManager reglementManager;
	@Autowired
	OperationManager operationManager;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	AchatMarcheManager achatMarcheManager;
	
	@RequestMapping("/home")
	public String index(Model model,@RequestParam(name = "days", defaultValue = "0") String d) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int days = 0;
		try {
			days = Integer.parseInt(d);
		}catch (Exception e) {
			System.out.println("erreur");
		}
		if (principal instanceof UserDetails) {
		UserDetails userDetails = ((UserDetails)principal);
		//System.out.println("roles : " + userDetails.getAuthorities());
		if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
			boolean stat = false;
			if(days == 0 || days == 1 ||days == 7 || days == 30) {
				stat = true;
				int a = 0,b = 0;
				if(days == 0) {
					a = 0;
					b = 0;
				}else if(days == 1) {
					a = -1;
					b = -1;
				}else if(days == 7) {
					a = -7;
					b = -1;
				}else {
					a = -30;
					b = -1;
				}
			
			final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, a);
		    Date dateDebut  = cal.getTime();
		    final Calendar cal2 = Calendar.getInstance();
		    cal2.add(Calendar.DATE, b);
		    Date dateFin = cal2.getTime();
		    String dateFinstring = new SimpleDateFormat("yyyy-MM-dd").format(dateFin);
		    String dateDebutstring = new SimpleDateFormat("yyyy-MM-dd").format(dateDebut);
		    try {
				dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutstring);
				dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(dateFinstring);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Double totalF = operationManager.getAllOperation(dateFin, dateDebut, "F");
		    Integer countF = operationManager.getCountAllOperation(dateFin, dateDebut, "F");
		    Double totalB = operationManager.getAllOperation(dateFin, dateDebut, "B");
		    Integer countB = operationManager.getCountAllOperation(dateFin, dateDebut, "B");
		    Double totalV = operationManager.getAllOperation(dateFin, dateDebut, "V");
		    Integer countV = operationManager.getCountAllOperation(dateFin, dateDebut, "V");
		    Double totalP = operationManager.getAllOperation(dateFin, dateDebut, "P");
		    Integer countP = operationManager.getCountAllOperation(dateFin, dateDebut, "P");
		    model.addAttribute("days", days);
		    model.addAttribute("totalF", totalF);
		    model.addAttribute("countF", countF);
		    model.addAttribute("totalB", totalB);
		    model.addAttribute("countB", countB);
		    model.addAttribute("totalV", totalV);
		    model.addAttribute("countV", countV);
		    model.addAttribute("totalP", totalP);
		    model.addAttribute("countP", countP);
			}else {
				return "redirect:/inexistants";
			}
			
			/*Date dateDebut = 
			if(days == 0) */
			
			/*Double totalFactures = operationManager.getTotalByMonth(new Date(), "F");
			model.addAttribute("totalFactures", totalFactures);
			Integer countFactures = operationManager.getCountByMonth(new Date(), "F");
			model.addAttribute("countFactures", countFactures);
			
			Double totalBons = operationManager.getTotalByMonth(new Date(), "B");
			model.addAttribute("totalBons", totalBons);
			Integer countBons = operationManager.getCountByMonth(new Date(), "B");
			model.addAttribute("countBons", countBons);
			
			Double totalPaiements = operationManager.getTotalByMonth(new Date(), "P");
			model.addAttribute("totalPaiements", totalPaiements);
			Integer countPaiements = operationManager.getCountByMonth(new Date(), "P");
			model.addAttribute("countPaiements", countPaiements);
			
			Double totalVirements = operationManager.getTotalByMonth(new Date(), "V");
			model.addAttribute("totalVirements", totalVirements);
			Integer countVirements = operationManager.getCountByMonth(new Date(), "V");
			model.addAttribute("countVirements", countVirements);
			
			
			model.addAttribute("SituationClients", collaborateurManager.getSituationClients());
			model.addAttribute("SituationFournisseurs", collaborateurManager.getSituationFournisseurs());*/
			
		}else {
			
			Collaborateur collaborateur = userManager.getAccountOwner(userDetails.getUsername());
			model.addAttribute("partenaire", collaborateur);
			Long totalDocuments;
			Long totalFinance;
			if(collaborateur.getClass().getSimpleName().equals("Client")) {
				totalDocuments = reglementManager.getTotalFacturesOf(collaborateur.getId());
				totalFinance = reglementManager.getTotalVirmentsOf(collaborateur.getId());
				
				model.addAttribute("totalDocuments", totalDocuments);
				model.addAttribute("totalFinance", totalFinance);
				model.addAttribute("Documents", "Factures");
				model.addAttribute("Finance", "Virments");
			}else {
				totalDocuments = reglementManager.getTotalBonsOf(collaborateur.getId());
				totalFinance = reglementManager.getTotalPaiementsOf(collaborateur.getId());
				
				model.addAttribute("totalDocuments", totalDocuments);
				model.addAttribute("totalFinance", totalFinance);
				model.addAttribute("Documents", "Bons");
				model.addAttribute("Finance", "Paiements");
			}
			
			
		}
		}
		return "index";
	}
	/*
	 * Bloque 1
	 * Liste des clients.
	 * RequestMapping /clients
	 * Search Clients
	 * Liste des fournisseurs.
	 * RequestMapping /fournisseurs
	 * */
	@RequestMapping("/Coclients")
	public String clients(Model model,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		Page<Collaborateur> clients = collaborateurManager.findAllClients(page,size);
		int[] pages = new int[clients.getTotalPages()];
		model.addAttribute("listClients", clients);
		model.addAttribute("pages", pages);
		return "clients";
	}
	@RequestMapping("/CosearchClient")
	public String searchClient(Model model,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "8") int size, String name) {
		name = "%" + name + "%";
		Page<Collaborateur> clients = collaborateurManager.findClientByName(name, page,size);
		if(clients.getNumberOfElements() == 0){
			model.addAttribute("result", 0);
		}
		int[] pages = new int[clients.getTotalPages()];
		model.addAttribute("listClients", clients);
		model.addAttribute("pages", pages);
		return "clients";
	}
	@RequestMapping("/CosearchFournisseur")
	public String searchFournisseur(Model model,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "8") int size, String name) {
		name = "%" + name + "%";
		Page<Collaborateur> fournisseurs = collaborateurManager.findFournisseurByName(name, page,size);
		int[] pages = new int[fournisseurs.getTotalPages()];
		model.addAttribute("listFournisseurs", fournisseurs);
		model.addAttribute("pages", pages);
		return "fournisseurs";
	}
	
	@RequestMapping("/Cofournisseurs")
	public String fournisseurs(Model model,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		Page<Collaborateur> fournisseurs = collaborateurManager.findAllFournisseurs(page, size);
		int[] pages = new int[fournisseurs.getTotalPages()];
		model.addAttribute("listFournisseurs", fournisseurs);
		model.addAttribute("pages", pages);
		return "fournisseurs";
	}
	/*
	 * Bloque 2
	 * Ajouter un client.
	 * RequestMapping /addClient
	 * Ajouter un fournisseur.
	 * RequestMapping /addFournisseur
	 * */
	@RequestMapping("/CoaddFournisseur")
	public String addFournisseur(Model model){
		Collaborateur fournisseur = new Fournisseur();
		model.addAttribute("fournisseur", fournisseur);
		return "addFournisseur";
	}
	@RequestMapping(value="CosaveFournisseur", method = RequestMethod.POST)
	public String saveFournisseer(Model model, @Valid Fournisseur fournisseur, BindingResult bindingResult){
		if(!bindingResult.hasErrors()){
			fournisseur.setDateAjoute(new Date());
			collaborateurManager.addPartenaire(fournisseur);
			return "redirect:/Cofournisseurs";
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("client", fournisseur);
			return "addfournisseur";
		}
		
	}
	
	@RequestMapping("CoaddClient")
	public String addClient(Model model){
		Collaborateur client = new Client();
		model.addAttribute("client", client);
		
		return "addclient";
	}
	@RequestMapping(value="/CosaveClient", method = RequestMethod.POST)
	public String saveClient(Model model, @Valid Client client, BindingResult bindingResult){
		if(!bindingResult.hasErrors()){
			client.setDateAjoute(new Date());
			
			collaborateurManager.addPartenaire(client);
			
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("client", client);
		}
		return "addclient";
	}
	/*
	 * Bloque 2
	 * Modifier un client
	 * supprimer un client
	 * */
	@RequestMapping(value="/CoeditClient")
	public String editClient(Model model, String idClient){
		Long id = Long.parseLong(idClient);
		Collaborateur client = collaborateurManager.findPartenaire(id);
		model.addAttribute("client", client);
		return "editClient";
	}
	@RequestMapping(value="CosaveEditClient", method = RequestMethod.POST)
	public String saveEditClient(Model model, @Valid Client client,String date, BindingResult bindingResult){
		if(!bindingResult.hasErrors()){
			SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date dateAjoute = formatter.parse(date);
				client.setDateAjoute(dateAjoute);
				collaborateurManager.updatePartenaire(client);
				return "redirect:/Coclients";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "editClient";
			}
			
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("client", client);
			return "editClient";
		}
		
	}
	/*
	 * Bloque 3
	 * Modifier un fournisseur
	 * supprimer un fournisseur
	 * */
	@RequestMapping(value="/CoeditFournisseur")
	public String editFournisseur(Model model, String idFournisseur){
		Long id = Long.parseLong(idFournisseur);
		Collaborateur fournisseur = collaborateurManager.findPartenaire(id);
		model.addAttribute("fournisseur", fournisseur);
		return "editFournisseur";
	}
	@RequestMapping(value="/CosaveEditFournisseur", method = RequestMethod.POST)
	public String saveEditFournisseur(Model model, @Valid Fournisseur fournisseur,String date, BindingResult bindingResult){
		if(!bindingResult.hasErrors()){
			SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date dateUpdate = formatter.parse(date);
				fournisseur.setDateAjoute(dateUpdate);
				collaborateurManager.updatePartenaire(fournisseur);
				return "redirect:/Cofournisseurs";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "editFournisseurs";
			}
			
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("fournisseur", fournisseur);
			return "editFournisseur";
		}
		
	}
	/*
	 * Operations(Documents)
	 * Facture
	 * Bon d'achat
	 * */
	@RequestMapping("/operationsFournisseurs")
	public String operationsFinance(Model model) {
		List<Collaborateur> fournisseurs = collaborateurManager.findAllFourinsseursList();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("bon", new Bon());
		model.addAttribute("paymentFournisseur", new PaymentFournisseur());
		return "operationsFournisseurs";
	}
	@RequestMapping("/operationsClients")
	public String operationsDocuments(Model model) {
		List<Collaborateur> clients = collaborateurManager.findAllClientsList();
		model.addAttribute("clients", clients);
		model.addAttribute("facture", new Facture());	
		model.addAttribute("virementClient", new VirementClient());
		return "operationsClients";
	}
	@RequestMapping("/addFacture")
	public String addFacture(Model model) {
		
		List<Collaborateur> clients = collaborateurManager.findAllClientsList();
		model.addAttribute("clients", clients);
		model.addAttribute("facture", new Facture());
		return "operationsClients";
	}
	@RequestMapping(value ="/saveFacture", method = RequestMethod.POST)
	public String saveFacture(Model model, @Valid Facture facture, String partenaire, String dateF, BindingResult bindingResult) {
		String success = "Operation bien efectuee.";
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateF);
				facture.setDate(dateAjoute);
				Long partenaireID = Long.parseLong(partenaire);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaireID);
				facture.setReglement(reglement);
				operationManager.addDocument(facture);
				model.addAttribute("success", success);
				List<Collaborateur> clients = collaborateurManager.findAllClientsList();
				model.addAttribute("clients", clients);
				model.addAttribute("facture", new Facture());	
				model.addAttribute("virementClient", new VirementClient());
				return "operationsClients";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "operationsClients";
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("facture", facture);
			return "operationsClients";
		}
	}
	/*@RequestMapping("/addBon")
	public String addBon(Model model) {
		
		List<Collaborateur> fournisseurs = collaborateurManager.findAllFourinsseursList();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("bon", new Bon());
		return "operationsCli";
	}*/
	@RequestMapping(value ="/saveBon", method = RequestMethod.POST)
	public String saveFacture(Model model, @Valid Bon bon, String partenaire, String dateB, BindingResult bindingResult) {
		
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateB);
				bon.setDate(dateAjoute);
				Long partenaireID = Long.parseLong(partenaire);
				System.out.println(partenaireID);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaireID);
				
				bon.setReglement(reglement);
				operationManager.addDocument(bon);
				return "redirect:/operationsFournisseurs";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "operationsFournisseurs";
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("bon", bon);
			return "operationsFournisseurs";
		}
	}
	/*
	 * Operations(Documents)
	 * PaymentFournisseur
	 * VirmentClient
	 * 
	 * */
	@RequestMapping("/addPaymentFournisseur")
	public String addPayemntFournisseur(Model model) {
		
		List<Collaborateur> fournisseurs = collaborateurManager.findAllFourinsseursList();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("paymentFournisseur", new PaymentFournisseur());
		model.addAttribute("bon", new Bon());
		return "operationsFournisseurs";
	}
	@RequestMapping(value ="/savePayemntFournisseur", method = RequestMethod.POST)
	public String savePayemntFournisseur(Model model, @Valid PaymentFournisseur paymentFournisseur, String partenaire, String dateF, BindingResult bindingResult) {
		
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateF);
				paymentFournisseur.setDate(dateAjoute);
				Long partenaireID = Long.parseLong(partenaire);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaireID);
				
				paymentFournisseur.setReglement(reglement);
				operationManager.addDocument(paymentFournisseur);
				return "redirect:/addPaymentFournisseur";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "redirect:/addPaymentFournisseur";
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("paymentFournisseur", paymentFournisseur);
			return "redirect:/operationsFournisseurs";
		}
	}
	@RequestMapping("/addVirementClient")
	public String addVirmentClient(Model model) {
		
		List<Collaborateur> clients = collaborateurManager.findAllClientsList();
		model.addAttribute("clients", clients);
		model.addAttribute("virementClient", new VirementClient());
		return "operationsClients";
	}
	@RequestMapping(value ="/saveVirmentClient", method = RequestMethod.POST)
	public String saveVirmentClient(Model model, @Valid VirementClient virementClient, String partenaire, String dateB, BindingResult bindingResult) {
		
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateB);
				virementClient.setDate(dateAjoute);
				Long partenaireID = Long.parseLong(partenaire);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaireID);
				
				virementClient.setReglement(reglement);
				operationManager.addDocument(virementClient);
				return "redirect:/addVirementClient";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "operationsClients";
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("virementClient", virementClient);
			return "operationsClients";
		}
	}
	
	@RequestMapping("/deleteOperation")
	public String deleteOperation(Model model,String idPartenaire,String idOperation,String type){
		Long idO = Long.parseLong(idOperation);
		Operation operation = operationManager.findDocument(idO);
		operationManager.deleteDocument(operation);
		return "redirect:/CoshowOperations?idPartenaire=" + idPartenaire + "&type=" + type;
		
	}
	@RequestMapping("/editOperation")
	public String editFacture(RedirectAttributes redirectAttributes,Model model,String idPartenaire, String idOperation, String type,int page){
			
			Long id = Long.parseLong(idOperation);
			Long idC = Long.parseLong(idPartenaire);
			Operation facture = operationManager.findDocument(id);
			Collaborateur client = collaborateurManager.findPartenaire(idC);
			String date = facture.getDate().toString();
			redirectAttributes.addFlashAttribute("date", date.substring(0, 10));
			redirectAttributes.addFlashAttribute("client", client);
			redirectAttributes.addFlashAttribute("operation", facture);
			redirectAttributes.addFlashAttribute("modeEdit", true);
			if(type.equals("F")) redirectAttributes.addFlashAttribute("editF", true);
			if(type.equals("P")) redirectAttributes.addFlashAttribute("editP", true);
			if(type.equals("V")) redirectAttributes.addFlashAttribute("editV", true);
			if(type.equals("B")) redirectAttributes.addFlashAttribute("editB", true);
			
			return "redirect:/CoshowOperations?idPartenaire=" + idPartenaire + "&type="+ type + "&page=" + page;
	}
	@RequestMapping(value ="/saveEditFacture", method = RequestMethod.POST)
	public String saveEditFacture(RedirectAttributes redirectAttributes,Model model, 
			@Valid Facture facture, String partenaire, String dateO,
			BindingResult bindingResult, String typeOp, int page) {
		Long partenaireID = Long.parseLong(partenaire);
		//int p = Integer.parseInt(page);
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateO);
				facture.setDate(dateAjoute);
				
				Reglement reglement = reglementManager.getActiveReglementOf(partenaireID);
				
				facture.setReglement(reglement);
				operationManager.addDocument(facture);
				return "redirect:/CoshowOperations?idPartenaire=" + partenaireID + "&type=" + typeOp + "&page=" + page;
				//return "redirect:/viewClient?idClient=" + partenaireID;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "redirect:/CoshowOperations?idPartenaire=" + partenaireID + "&type=" + typeOp + "&page=" + page;
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			redirectAttributes.addFlashAttribute("errors", errors);
			redirectAttributes.addFlashAttribute("facture", facture);
			redirectAttributes.addFlashAttribute("modeEdit", true);
			return "redirect:/CoshowOperations?idPartenaire=" + partenaireID + "&type=" + typeOp + "&page=" + page;
		}
	}
	@RequestMapping("/editBon")
	public String editBon(Model model,String idPartenaire, String idOperation){
		
			Long id = Long.parseLong(idOperation);
			Long idP = Long.parseLong(idPartenaire);
			Operation bon = operationManager.findDocument(id);
			Collaborateur fournisseur = collaborateurManager.findPartenaire(idP);
			String date = bon.getDate().toString();
			model.addAttribute("date", date.substring(0, 10));
			model.addAttribute("fournisseur", fournisseur);
			model.addAttribute("bon", bon);

		return "editBon";
	}
	@RequestMapping(value ="/saveEditBon", method = RequestMethod.POST)
	public String saveEditBon(Model model, @Valid Bon bon, Long partenaire,
			String dateO, BindingResult bindingResult, String typeOp, int page) {
		
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateO);
				bon.setDate(dateAjoute);
				//Long partenaireID = Long.parseLong(partenaire);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaire);
				
				bon.setReglement(reglement);
				operationManager.addDocument(bon);
				return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("bon", bon);
			return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
		}
	}
	@RequestMapping("/editPaymentFournisseur")
	public String editPaymentFournisseur(Model model,String idPartenaire, String idOperation){
		
			Long id = Long.parseLong(idOperation);
			Long idP = Long.parseLong(idPartenaire);
			Operation paiement = operationManager.findDocument(id);
			Collaborateur fournisseur = collaborateurManager.findPartenaire(idP);
			String date = paiement.getDate().toString();
			model.addAttribute("date", date.substring(0, 10));
			model.addAttribute("fournisseur", fournisseur);
			model.addAttribute("paiement", paiement);

		return "editPaymentFournisseur";
	}
	@RequestMapping(value ="/saveEditPaymentFournisseur", method = RequestMethod.POST)
	public String saveEditPaymentFournisseur(Model model, @Valid PaymentFournisseur paymentFournisseur,
			Long partenaire, String dateO, BindingResult bindingResult, String typeOp, int page) {
		
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateO);
				paymentFournisseur.setDate(dateAjoute);
				//Long partenaireID = Long.parseLong(partenaire);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaire);
				
				paymentFournisseur.setReglement(reglement);
				operationManager.addDocument(paymentFournisseur);
				return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("paymentFournisseur", paymentFournisseur);
			return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
		}
	}
	@RequestMapping("/editVirmentClient")
	public String editVirmentClient(Model model,String idPartenaire, String idOperation){
		
			Long id = Long.parseLong(idOperation);
			Long idP = Long.parseLong(idPartenaire);
			Operation virmentClient = operationManager.findDocument(id);
			Collaborateur client = collaborateurManager.findPartenaire(idP);
			String date = virmentClient.getDate().toString();
			model.addAttribute("date", date.substring(0, 10));
			model.addAttribute("client", client);
			model.addAttribute("virmentClient", virmentClient);

		return "editVirmentClient";
	}
	@RequestMapping(value ="/saveEditVirmentClient", method = RequestMethod.POST)
	public String saveEditvirmentClient(Model model, @Valid VirementClient virmentClient,
			Long partenaire, String dateO, BindingResult bindingResult, String typeOp, int page) {
		
		if(!bindingResult.hasErrors()){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		try {
				Date dateAjoute = formatter.parse(dateO);
				virmentClient.setDate(dateAjoute);
				//Long partenaireID = Long.parseLong(partenaire);
				Reglement reglement = reglementManager.getActiveReglementOf(partenaire);
				
				virmentClient.setReglement(reglement);
				operationManager.addDocument(virmentClient);
				return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
			}
		}else{
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			List<String> errors = new ArrayList<>();
			for (ObjectError e : listErrors)
				errors.add(e.getDefaultMessage());
			model.addAttribute("errors", errors);
			model.addAttribute("virmentClient", virmentClient);
			return "redirect:/CoshowOperations?idPartenaire=" + partenaire + "&type=" + typeOp + "&page=" + page;
		}
	}
	/*
	 * View Client
	 * View Fournisseur
	 * View Operations
	 * */
	@RequestMapping(value ="/CoviewRegl", method = RequestMethod.POST)
	public String viewRegl(String regl){
		Long idR = Long.parseLong(regl);
		System.out.println(regl);
		Reglement reglement = reglementManager.findReglement(idR);
		Collaborateur collaborateur = collaborateurManager.findPartenaire(reglement.getOwner().getId());
		if(collaborateur.getClass().getSimpleName().equals("Client"))
			return "redirect:/CoviewClient?idClient=" + reglement.getOwner().getId() + "&regl=" + idR;
		else
			return "redirect:/CoviewFournisseur?idFournisseur=" + reglement.getOwner().getId() + "&regl=" + idR;
			
	}
	@RequestMapping(value ="/CoviewClient")
	public String viewClient(Model model, String idClient, String regl){
		Long id = Long.parseLong(idClient);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetails = ((UserDetails)principal);
			if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
			Collaborateur collaborateur = collaborateurManager.findPartenaire(userManager.getAccountOwner(userDetails.getUsername()).getId());
			if(collaborateur != null) {
				if(collaborateur.getId() != id) {
					return "redirect:/accessDenied";
				}
			}
			}
		}
		
		Collaborateur client = collaborateurManager.findPartenaire(id);
		model.addAttribute("partenaire", client);	
		List<Reglement> reglements = reglementManager.findReglementsByPartenaire(id);
		
		Reglement activeRegl = reglementManager.getActiveReglementOf(id);
		Reglement selectRegl;
		if(regl == null){
			selectRegl = activeRegl;
		}else{
			Long idR = Long.parseLong(regl);
			
			selectRegl = reglementManager.findReglement(idR);
			
			
		}
		if(client.getReglements().isEmpty()){
			
			model.addAttribute("message", true);
			model.addAttribute("client", client);
			return "viewClient";
		}
		if(!client.getReglements().contains(selectRegl) || selectRegl == null) {
			
			return "redirect:/inexistants";
		}
		Long tf = operationManager.getTotalFactures(selectRegl.getId());
		if(tf == null) tf = 0L;
		Long tv = operationManager.getTotalVirement(selectRegl.getId());
		if(tv == null) tv = 0L;
		Long situation = reglementManager.getTotalVirmentsOf(id) - reglementManager.getTotalFacturesOf(id);
		
		model.addAttribute("situation", situation);
		model.addAttribute("reglements", reglements);
		model.addAttribute("activeRegl", activeRegl);
		model.addAttribute("selectRegl", selectRegl);
		model.addAttribute("totalFactures", tf);
		model.addAttribute("totalVirments", tv);
		model.addAttribute("client", client);
		
		return "viewClient";
	}
	@RequestMapping(value ="/CoviewFournisseur")
	public String viewFournisseur(Model model, String idFournisseur, String regl){
		Long id = Long.parseLong(idFournisseur);
		Collaborateur fournisseur = collaborateurManager.findPartenaire(id);
		List<Reglement> reglements = reglementManager.findReglementsByPartenaire(id);
		
		Reglement activeRegl = reglementManager.getActiveReglementOf(id);
		System.out.println(activeRegl);
		Reglement selectRegl;
		if(regl == null){
			selectRegl = activeRegl;
		}else{
			Long idR = Long.parseLong(regl);
			selectRegl = reglementManager.findReglement(idR);
		}
		Long tb = operationManager.getTotalBons(selectRegl.getId());
		if(tb == null) tb = 0L;
		Long tp = operationManager.getTotalPaiements(selectRegl.getId());
		if(tp == null) tp = 0L;
		Long situation = reglementManager.getTotalBonsOf(id) - reglementManager.getTotalPaiementsOf(id);
		
		model.addAttribute("situation", situation);
		model.addAttribute("reglements", reglements);
		model.addAttribute("activeRegl", activeRegl);
		model.addAttribute("selectRegl", selectRegl);
		model.addAttribute("totalBons", tb);
		model.addAttribute("totalPaiements", tp);
		model.addAttribute("fournisseur", fournisseur);
		/*
		Long id = Long.parseLong(idFournisseur);
		Partenaire fournisseur = partenaireManager.findPartenaire(id);
		Long tb = reglementManager.getTotalBonsOf(id);
		Long tp = reglementManager.getTotalPaiementsOf(id);
		//Reglement reglement = reglementManager.getActiveReglementOf(id);
		
		model.addAttribute("totalBons", tb);
		model.addAttribute("totalPaiements", tp);
		model.addAttribute("fournisseur", fournisseur);
		*/
		return "viewFournisseur";
	}
	private void checkSession() {
		
	}
	@RequestMapping(value ="/CoshowOperations")
	public String showOperations(Model model, String idPartenaire, String type, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, String r){
		Long id = Long.parseLong(idPartenaire);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetails = ((UserDetails)principal);
			if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				Collaborateur collaborateur = collaborateurManager.findPartenaire(userManager.getAccountOwner(userDetails.getUsername()).getId());
				if(collaborateur != null) {
					if(collaborateur.getId() != id) {
						return "redirect:/accessDenied";
					}
				}
				}
		}
		//System.out.println("dslk,m" + r);
		Collaborateur collaborateur = collaborateurManager.findPartenaire(id);
		Long idr = Long.parseLong(r); 
		Reglement regl = reglementManager.findReglement(idr);
		String t = "", title = "";
		if(type.equals("F")){
			t = "F";
			title = "Liste des factures";
		}else if(type.equals("V")){
			t = "V";
			title = "Liste des virments";
		}else if(type.equals("B")){
			t = "B";
			title = "Liste des bons";
		}else if(type.equals("P")){
			t = "P";
			title = "Liste des paiements";
		}
		Page<Operation> operations = operationManager.getAllOperationByType(regl.getId(), t, size, page);
		int[] pages = new int[operations.getTotalPages()];
		model.addAttribute("listOperations", operations);
		model.addAttribute("pages", pages);
		model.addAttribute("partenaire", collaborateur);
		model.addAttribute("title", title);
		model.addAttribute("type", t);
		model.addAttribute("page", page);	
		return "showOperations";
	}
	
	@RequestMapping("/addReglement")
	public String addReglement(Model model,String partenaire){
		Long id = Long.parseLong(partenaire);
		Collaborateur p = collaborateurManager.findPartenaire(id);
		reglementManager.addReglement(p);
		return "redirect:/CoviewClient?idClient=" + partenaire;
		
	}
	
	@RequestMapping("/achatMarche")
	public String achatMarche(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		
		Page<AchatMarche> achatMarche = achatMarcheManager.getAll(page, size);
		model.addAttribute("achats", achatMarche);
		int[] pages = new int[achatMarche.getTotalPages()];
		model.addAttribute("pages", pages);
		return "achatMarche";
	}
	
	@RequestMapping("/addAchat")
	public String addAchat(Model model, @Valid AchatMarche achatMarche, BindingResult bindingResult
			, String dateA) {
		
		if(!bindingResult.hasErrors()){
			SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
			try {
					Date dateAjoute = formatter.parse(dateA);
					achatMarche.setDate(dateAjoute);
					achatMarcheManager.addAchatMarche(achatMarche);
					return "redirect:/achatMarche";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "achatMarche";
				}
			}else{
				List<ObjectError> listErrors = bindingResult.getAllErrors();
				System.out.println(listErrors);
				List<String> errors = new ArrayList<>();
				for (ObjectError e : listErrors)
					errors.add(e.getDefaultMessage());
				model.addAttribute("errors", errors);
				return "achatMarche";
			}
	}
	@RequestMapping("/achatByMonth")
	public String achatByMonth(String month) {
		System.out.println(month);
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM");
		try {
			Date date = formatter.parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int m= cal.get(Calendar.MONTH);
			int y = cal.get(Calendar.YEAR);
			System.out.println("month "+ m + " year " + y);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/achatMarche";
		
	}
	@RequestMapping("/deleteCollaborateur")
	public String deleteCollaborateur(String id) {
		
		if(id.matches("\\d*")) {
			Long idc = Long.parseLong(id);
			Collaborateur collaborateur = collaborateurManager.findPartenaire(idc);
			if(collaborateur == null) {
				return "redirect:/inexistants";
			}
			
			collaborateurManager.deletePartenaire(collaborateur);
			
			if(collaborateur.getClass().getSimpleName().equals("Client"))
				return "redirect:/Coclients";
			else
				return "redirect:/Cofournisseurs";
		} else {
			return "redirect:/inexistants";
	}
	}
	
	
	
	
	
	
	
	
}