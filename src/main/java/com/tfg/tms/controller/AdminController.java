package com.tfg.tms.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tfg.tms.dto.AdminEmployeeDTO;
import com.tfg.tms.dto.AdminMessageDTO;
import com.tfg.tms.dto.AdminTicketDTO;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Department;
import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Message;
import com.tfg.tms.entity.Ticket;
import com.tfg.tms.service.AdminService;
import com.tfg.tms.service.VisitorService;

import lombok.extern.slf4j.Slf4j;

/*
 * This class is a controller to give the admin full CRUD functionality for the entities in the site
 */

@Slf4j
@Controller
//declare the customer as a session attribute to be used throughout the controller
@SessionAttributes("customer")
@RequestMapping("/admin")
public class AdminController {

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

	private AdminService adminService;
	private VisitorService visitorService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public AdminController(AdminService adminService, VisitorService visitorService, PasswordEncoder passwordEncoder) {
		this.adminService = adminService;
		this.visitorService = visitorService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/list")
	public String listAdmin(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// add the list of employees to the model
		List<Employee> employees = adminService.getEmployees();
		model.addAttribute("employees", employees);

		// add the list of employees to the model
		List<Department> departments = adminService.getDepartments();
		model.addAttribute("departments", departments);

		// add the list of employees to the model
		List<Ticket> tickets = adminService.getTickets();
		model.addAttribute("tickets", tickets);

		// add the list of employees to the model
		List<Customer> customers = adminService.getCustomers();
		model.addAttribute("customers", customers);

		// add the list of messages to the model
		List<Message> messages = adminService.getMessages();
		model.addAttribute("messages", messages);

		// send the user to the list-employees page
		return "admin/list_admin";

	}

	/*
	 * 
	 * Customer actions
	 * 
	 */

	@GetMapping("/showCustomerFormForAdd")
	public String showCustomerFormForAdd(@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// create a customer object to bind the data to
		Customer newCustomer = new Customer();

		// attach the new object to the model
		model.addAttribute("newCustomer", newCustomer);

		// add flag for update form
		model.addAttribute("updateFlag", false);

		// send user to the page to add a new customer
		return "/admin/admin_form_customer";

	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("newCustomer") Customer newCustomer) {

		// save the model using the service
		adminService.saveCustomer(newCustomer);

		// log the transaction
		log.info("admin added " + newCustomer.getEmail());

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	@GetMapping("/showCustomerFormForUpdate")
	public String showCustomerFormForUpdate(@RequestParam("customerId") Integer id,
			@ModelAttribute("customer") Customer customer, Model model) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// create a customer object to bind the data to
		Customer newCustomer = adminService.getCustomer(id);

		// attach the new object to the model
		model.addAttribute("newCustomer", newCustomer);

		// add flag for update form re:password
		model.addAttribute("updateFlag", true);

		// send back to the form
		return "/admin/admin_form_customer";

	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@ModelAttribute("customerId") Integer id) {

		// remove the customer from the customer's tickets
		adminService.removeCustomerTickets(id);

		// delete the customer using the service
		adminService.deleteCustomer(id);

		// log the transaction
		log.info("admin deleted customer id " + id);

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	/*
	 * 
	 * Employee actions
	 * 
	 */

	@GetMapping("/showEmployeeFormForAdd")
	public String showEmployeeFormForAdd(Model model, @ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get list of all the departments from the database and add it to the model
		List<Department> departments = adminService.getDepartments();

		Map<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();

		for (Department department : departments) {
			departmentMap.put(department.getId(), department.getName());
		}

		model.addAttribute("departments", departmentMap);

		// create an employee object to bind the data to
		// Employee employee = new Employee();
		AdminEmployeeDTO employeeDTO = new AdminEmployeeDTO();

		// attach the new object to the model
		model.addAttribute("employeeDTO", employeeDTO);

		// add flag for update form re:password
		model.addAttribute("updateFlag", false);

		// send user to the page to add a new employee
		return "/admin/admin_form_employee";

	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employeeDTO") AdminEmployeeDTO employeeDTO) {

		// create an employee from the binder
		Employee employee = new Employee(employeeDTO);

		// add the department with the selected id to the employee
		if (employeeDTO.getDepartment_id() == null) {
			employee.setDepartment(null);
		} else {
			employee.setDepartment(adminService.getDepartment(employeeDTO.getDepartment_id()));
		}

		// encode the password
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));

		// save the model using the service
		adminService.saveEmployee(employee);

		// log transaction
		if (employeeDTO.getDepartment_id() == null) {
			log.info("admin added employee " + employee.getEmail());
		} else {
			log.info("admin updated employee " + employee.getEmail());
		}

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	@GetMapping("/showEmployeeFormForUpdate")
	public String showEmployeeFormForUpdate(@RequestParam("employeeId") Integer id, Model model,
			@ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get list of all the departments from the database and add it to the model
		List<Department> departments = adminService.getDepartments();
		Map<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			departmentMap.put(department.getId(), department.getName());
		}
		model.addAttribute("departments", departmentMap);

		// create a customer object to bind the data to
		Employee employee = adminService.getEmployee(id);

		// make a binder from the employee in the db
		AdminEmployeeDTO employeeDTO = new AdminEmployeeDTO(employee);

		// attach the new object to the model
		model.addAttribute("employeeDTO", employeeDTO);

		// add flag for update form re:password
		model.addAttribute("updateFlag", true);

		// send back to the form
		return "/admin/admin_form_employee";

	}

	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@ModelAttribute("employeeId") Integer id) {

		// remove the employee from their tickets
		adminService.clearEmployeeTickets(id);

		// delete the employee using the service
		adminService.deleteEmployee(id);

		log.info("admin deleted employee " + id);

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	/*
	 * 
	 * Department actions
	 * 
	 */

	@GetMapping("/showDepartmentFormForAdd")
	public String showDepartmentFormForAdd(Model model, @ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// create an department object to bind the data to
		Department department = new Department();

		// attach the new object to the model
		model.addAttribute("department", department);

		// send user to the page to add a new department
		return "/admin/admin_form_department";

	}

	@PostMapping("/saveDepartment")
	public String saveDepartment(@ModelAttribute("department") Department department) {

		// save the model using the service
		adminService.saveDepartment(department);

		log.info("admin added department " + department.getName());

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	@GetMapping("/showDepartmentFormForUpdate")
	public String showDepartmentFormForUpdate(@RequestParam("departmentId") Integer id, Model model,
			@ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// create a customer object to bind the data to
		Department department = adminService.getDepartment(id);

		// attach the new object to the model, @ModelAttribute("customer") Customer
		// customer
		model.addAttribute("department", department);

		// send back to the form
		return "/admin/admin_form_department";

	}

	@GetMapping("/deleteDepartment")
	public String deleteDepartment(@ModelAttribute("departmentId") Integer id) {

		// remove the department from the employees?
		adminService.clearDepartmentEmployees(id);

		// remove the department from the tickets?
		adminService.clearDepartmentTickets(id);

		// delete the department using the service
		adminService.deleteDepartment(id);

		log.info("admin deleted department " + id);

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	/*
	 * 
	 * Ticket actions
	 * 
	 */

	@GetMapping("/showTicketFormForAdd")
	public String showTicketFormForAdd(Model model, @ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get list of all the departments from the database and add it to the model
		List<Department> departments = adminService.getDepartments();
		Map<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			departmentMap.put(department.getId(), department.getName());
		}
		model.addAttribute("departments", departmentMap);

		// get list of all the employees from the database and add it to the model
		List<Employee> employees = adminService.getEmployees();

		Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();

		for (Employee employee : employees) {
			employeeMap.put(employee.getId(), employee.getFirstName() + " " + employee.getLastName());
		}

		model.addAttribute("employees", employeeMap);

		// get list of all the customers from the database and add it to the model
		List<Customer> customers = adminService.getCustomers();

		Map<Integer, String> customerMap = new LinkedHashMap<Integer, String>();

		for (Customer tempCustomer : customers) {
			customerMap.put(tempCustomer.getId(), tempCustomer.getFirstName() + " " + tempCustomer.getLastName());
		}

		model.addAttribute("customers", customerMap);

		// create a binding object to hold the form data and add it to the model
		AdminTicketDTO ticketDTO = new AdminTicketDTO();
		model.addAttribute("ticketDTO", ticketDTO);

		// send user to the form page
		return "/admin/admin_form_ticket";

	}

	@PostMapping("/saveTicket")
	public String saveTicket(@ModelAttribute("ticketDTO") AdminTicketDTO ticketDTO) {

		// create a new ticket from the binder info
		Ticket ticket = new Ticket(ticketDTO);

		// add the employee with the selected id to the ticket
		if (ticketDTO.getEmployee_id() == null) {
			ticket.setEmployee(null);
		} else {
			ticket.setEmployee(adminService.getEmployee(ticketDTO.getEmployee_id()));
		}

		// add the customer with the selected id to the ticket
		if (ticketDTO.getCustomer_id() == null) {
			ticket.setCustomer(null);
		} else {
			ticket.setCustomer(adminService.getCustomer(ticketDTO.getCustomer_id()));
		}

		// add the department with the selected id to the ticket
		if (ticketDTO.getDepartment_id() == null) {
			ticket.setDepartment(null);
		} else {
			ticket.setDepartment(adminService.getDepartment(ticketDTO.getDepartment_id()));
		}

		// keep the messages attached to the ticket
		// if it has an id, it is an update
		if (ticket.getId() != null) {
			// get the messages for the ticket
			Set<Message> mesageSet = adminService.getTicket(ticketDTO.getTicket_id()).getMessages();
			// attach them to the object to be updated
			ticket.setMessages(mesageSet);
		}

		// save the ticket using the service
		adminService.saveTicket(ticket);

		if (ticket.getId() == null) {
			log.info("admin added ticket " + ticket.getDetail());
		} else {
			log.info("admin updated ticket " + ticket.getId());
		}

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	@GetMapping("/showTicketFormForUpdate")
	public String showTicketFormForUpdate(@RequestParam("ticketId") Integer id, Model model,
			@ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get list of all the departments from the database and add it to the model
		List<Department> departments = adminService.getDepartments();
		Map<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			departmentMap.put(department.getId(), department.getName());
		}
		model.addAttribute("departments", departmentMap);

		// get list of all the employees from the database and add it to the model
		List<Employee> employees = adminService.getEmployees();
		Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
		for (Employee employee : employees) {
			employeeMap.put(employee.getId(), employee.getFirstName() + " " + employee.getLastName());
		}
		model.addAttribute("employees", employeeMap);

		// get list of all the customers from the database and add it to the model
		List<Customer> customers = adminService.getCustomers();
		Map<Integer, String> customerMap = new LinkedHashMap<Integer, String>();
		for (Customer tempCustomer : customers) {
			customerMap.put(tempCustomer.getId(), tempCustomer.getFirstName() + " " + tempCustomer.getLastName());
		}
		model.addAttribute("customers", customerMap);

		// get the ticket with that id
		Ticket ticket = adminService.getTicket(id);

		// create a binder for the form from the ticket
		AdminTicketDTO ticketDTO = new AdminTicketDTO(ticket);

		// attach the binder to the model
		model.addAttribute("ticketDTO", ticketDTO);

		// send back to the form
		return "/admin/admin_form_ticket";

	}

	@GetMapping("/deleteTicket")
	public String deleteTicket(@ModelAttribute("ticketId") Integer id) {

		// delete the ticket using the service
		adminService.deleteTicket(id);

		log.info("admin deleted ticket " + id);

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	/*
	 * 
	 * Message actions
	 * 
	 */

	@GetMapping("/showMessageFormForAdd")
	public String showMessageFormForAdd(Model model, @ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get list of all the employees from the database and add it to the model
		List<Employee> employees = adminService.getEmployees();
		Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
		for (Employee employee : employees) {
			employeeMap.put(employee.getId(), employee.getFirstName() + " " + employee.getLastName());
		}
		model.addAttribute("employees", employeeMap);

		// get list of all the customers from the database and add it to the model
		List<Customer> customers = adminService.getCustomers();
		Map<Integer, String> customerMap = new LinkedHashMap<Integer, String>();
		for (Customer tempCustomer : customers) {
			customerMap.put(tempCustomer.getId(), tempCustomer.getFirstName() + " " + tempCustomer.getLastName());
		}
		model.addAttribute("customers", customerMap);

		// get list of all the tickets from the database and add it to the model
		List<Ticket> tickets = adminService.getTickets();
		Map<Integer, String> ticketMap = new LinkedHashMap<Integer, String>();
		for (Ticket ticket : tickets) {
			ticketMap.put(ticket.getId(), ticket.getDetail());
		}
		model.addAttribute("tickets", ticketMap);

		// create a customer object to bind the data to
		AdminMessageDTO messageDTO = new AdminMessageDTO();

		// attach the new object to the model
		model.addAttribute("messageDTO", messageDTO);

		// send user to the page to add a new customer
		return "/admin/admin_form_message";

	}

	@PostMapping("/saveMessage")
	public String saveMessage(@ModelAttribute("messageDTO") AdminMessageDTO messageDTO) {

		// create a message from the DTO
		Message message = new Message(messageDTO);

		// add the ticket to the message
		message.setTicket(adminService.getTicket(messageDTO.getTicket_id()));

		// add the author of the message
		// if the customer id is not null,
		if (messageDTO.getCustomer_id() != null) {
			// add the customer to the message
			message.setCustomer(adminService.getCustomer(messageDTO.getCustomer_id()));
		} else {
			// Otherwise add the employee to the message
			message.setEmployee(adminService.getEmployee(messageDTO.getEmployee_id()));
		}

		// save the model using the service
		adminService.saveMessage(message);

		if (message.getId() == null) {
			log.info("admin added message " + message.getContent());
		} else {
			log.info("admin updated message " + message.getId());
		}

		// redirect back to the list page
		return "redirect:/admin/list";

	}

	@GetMapping("/showMessageFormForUpdate")
	public String showMessageFormForUpdate(@RequestParam("messageId") Integer id, Model model,
			@ModelAttribute("customer") Customer customer) {

		// add the customer to the model as the user
		model.addAttribute("user", customer);

		// get list of all the tickets from the database and add it to the model
		List<Ticket> tickets = adminService.getTickets();
		Map<Integer, String> ticketMap = new LinkedHashMap<Integer, String>();
		for (Ticket ticket : tickets) {
			ticketMap.put(ticket.getId(), ticket.getDetail());
		}
		model.addAttribute("tickets", ticketMap);

		// get list of all the employees from the database and add it to the model
		List<Employee> employees = adminService.getEmployees();
		Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
		for (Employee employee : employees) {
			employeeMap.put(employee.getId(), employee.getFirstName() + " " + employee.getLastName());
		}
		model.addAttribute("employees", employeeMap);

		// get list of all the customers from the database and add it to the model
		List<Customer> customers = adminService.getCustomers();
		Map<Integer, String> customerMap = new LinkedHashMap<Integer, String>();
		for (Customer tempCustomer : customers) {
			customerMap.put(tempCustomer.getId(), tempCustomer.getFirstName() + " " + tempCustomer.getLastName());
		}
		model.addAttribute("customers", customerMap);

		// get the message with that id
		Message message = adminService.getMessage(id);

		// create a messageDTO from the message
		AdminMessageDTO messageDTO = new AdminMessageDTO(message);
		// attach the new object to the model
		model.addAttribute("messageDTO", messageDTO);

		// send back to the form
		return "/admin/admin_form_message";

	}

	@GetMapping("/deleteMessage")
	public String deleteMessage(@ModelAttribute("messageId") Integer id) {

		// delete the message using the service
		adminService.deleteMessage(id);

		log.info("admin deleted message " + id);

		// redirect back to the list page
		return "redirect:/admin/list";

	}

}
