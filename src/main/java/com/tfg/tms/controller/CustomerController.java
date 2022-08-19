package com.tfg.tms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfg.tms.dto.CustomerMessageDTO;
import com.tfg.tms.dto.CustomerTicketDTO;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Department;
import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Message;
import com.tfg.tms.entity.Ticket;
import com.tfg.tms.service.AdminService;
import com.tfg.tms.service.VisitorService;

/*
 * This class is a controller to give customers access to the functions they can perform on this site
 */

@Controller
// declare the customer as a session attribute to be used throughout the controller
@SessionAttributes("customer")
@RequestMapping("/customer")
public class CustomerController {

	// by making the customer a model attribute, we can easily reference it in the
	// methods we write for the controller
	@ModelAttribute("customer")
	public Customer getCustomerFromLogin(HttpServletRequest request) {
		try {
			// get the principal for the currently logged in user from the request
			Principal principal = request.getUserPrincipal();
			// get the customer by the email used to log in
			Customer customer = visitorService.findByEmail(principal.getName());
			// return the customer
			return customer;
		} catch (Exception e) {
			return null;
		}
	}

	// declare the attributes that will be used; using the admin & visitor services
	// because they already have everything I need for now
	private AdminService adminService;
	private VisitorService visitorService;

	// but autowire the constructor for better functionality
	@Autowired
	public CustomerController(VisitorService visitorService, AdminService adminService) {
		this.adminService = adminService;
		this.visitorService = visitorService;
	}

	// Now I can reference the @ModelAttribute in @SessionAttributes without needing
	// to jump through the steps each time
	@GetMapping("/welcome")
	public String successCustomers(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// you can also directly reference the email for the currently logged in user in
		// thymeleaf using <span sec:authentication="name"></span>

		return "/customer/success.html";
	}

	@GetMapping("/tickets")
	public String showCustomerTickets(@ModelAttribute("customer") Customer customer, Model model) {

		// get the customer's tickets
		Set<Ticket> custTickets = adminService.getCustomerTickets(customer.getId());

		// create a list to sort the set by createdAt
		List<Ticket> sortedList = new ArrayList<Ticket>(custTickets);

		// this will sort the list
		Collections.sort(sortedList, new Comparator<Ticket>() {
			@Override
			public int compare(Ticket o1, Ticket o2) {
				return o1.getCreatedAt().compareTo(o2.getCreatedAt());
			}
		});

		// add it to the model
		model.addAttribute("custTickets", sortedList);
		// add the customer to the model as the user
		model.addAttribute("user", customer);
		// return to the page
		return "/customer/customer_tickets";
	}

	@GetMapping("/notifications")
	public String showCustomerNotifications(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		return "/customer/customer_notifications";
	}

	@GetMapping("/submit")
	public String showTicketForm(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// add a ticket dto to the form
		CustomerTicketDTO custTickDTO = new CustomerTicketDTO();
		model.addAttribute("ticket", custTickDTO);

		// get list of all the departments from the database and add it to the model
		List<Department> departments = adminService.getDepartments();
		Map<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			departmentMap.put(department.getId(), department.getName());
		}
		model.addAttribute("departments", departmentMap);

		return "/customer/customer_submit";
	}

	@PostMapping("/submit")
	public String submitTicketForm(@ModelAttribute("customer") Customer customer,
			@ModelAttribute("ticket") CustomerTicketDTO ticketDTO, Model model) {

		// create a ticket from the dto to pass the easy fields
		Ticket tempTicket = new Ticket(ticketDTO);
		tempTicket.setStatus("open");

		// get the department and set it to the ticket
		Department department = adminService.getDepartment(ticketDTO.getDepartmentId());
		tempTicket.setDepartment(department);

		// set the ticket customer as the customer in session
		tempTicket.setCustomer(customer);

		// get the employee with the fewest tickets in the selected department
		Employee employee = adminService.getEmployeeWithFewestTicketsFromDept(department.getId());
		tempTicket.setEmployee(employee);

		// save the ticket
		adminService.saveTicket(tempTicket);

		// redirect back to their ticket list
		return "redirect:/customer/tickets";

	}

	@GetMapping("/detail")
	public String showTicketDetail(@RequestParam("ticketId") Integer id, @ModelAttribute("customer") Customer customer,
			Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get the ticket from the id they clicked on
		Ticket ticket = adminService.getTicket(id);
		model.addAttribute("ticket", ticket);

		// create a new customermessageDTO for adding a new message
		CustomerMessageDTO custMessageDTO = new CustomerMessageDTO();
		model.addAttribute("newMessage", custMessageDTO);

		if (ticket != null) {

			// grab all the messages so they can be sorted
			List<Message> messages = adminService.getMessagesFromTicket(id);

			// this will sort the list
			Collections.sort(messages, new Comparator<Message>() {
				@Override
				public int compare(Message o1, Message o2) {
					return o1.getCreatedAt().compareTo(o2.getCreatedAt());
				}
			});

			model.addAttribute("messages", messages);
		} else {
			model.addAttribute("messages", null);
		}

		return "/customer/ticket_detail";
	}

	@PostMapping("/message")
	public String submitTicketMessage(@ModelAttribute("customer") Customer customer, Model model,
			@ModelAttribute("newMessage") CustomerMessageDTO custMessageDTO, RedirectAttributes redirectAttributes) {

		// make a new message
		Message message = new Message();

		// set the content
		message.setContent(custMessageDTO.getContent());

		// set the customer
		message.setCustomer(customer);

		// set the ticket
		Ticket ticket = adminService.getTicket(custMessageDTO.getTicketId());
		message.setTicket(ticket);

		// save the message
		adminService.saveMessage(message);

		// add the ticket id to the redirect parameter
		redirectAttributes.addAttribute("ticketId", ticket.getId());

		// redirect to the ticket detail page
		return "redirect:/customer/detail";
	}

	@GetMapping("/update")
	public String showUpdateForm(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// TODO: use the same form, but use the updateFlag trick from admin controller
		// to hide the pw

		return "/customer/customer_update";

	}

	@GetMapping("/delete")
	public String showDeleteForm(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		return "/customer/customer_delete";

	}

}