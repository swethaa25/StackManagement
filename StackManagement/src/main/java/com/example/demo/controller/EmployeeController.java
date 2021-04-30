package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Instance;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.InstanceRepository;

import java.util.List;
import java.util.Optional;
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeerepo;
	
	@Autowired
	private InstanceRepository instrepo;
	
	
	@GetMapping("/")
	public String home(Model model)
	{
		List<Instance> instances=instrepo.findAll();
		model.addAttribute("instance", instances);
		return "home";
	}
	
	@GetMapping("/addEmployee")
	public String addEmployee(Model model)
	{
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "addEmployee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee")  Employee employee)
	{
		employeerepo.save(employee);
		return "redirect:/showEmployee";
	}
	
	@GetMapping("/showEmployee")
	public String show(Model model)
	{
		List<Employee> employee=employeerepo.findAll();
		model.addAttribute("allemps", employee);
		return "showemployee";
	
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		employeerepo.deleteById(id);
		return "redirect:/showEmployee";
	}
	
	@GetMapping("/use")
	public String use(Model model)
	{
		Employee employee=new Employee();
		model.addAttribute("employee", employee);
		return "useinstance";

	}
	

	@GetMapping("/release")
	public String release(Model model)
	{
		Instance instance=new Instance();
		model.addAttribute("instance",instance);
		return "release";

	}
	
	@PostMapping("/saveInstance")
	public String save(@ModelAttribute("employee") Employee employee,Model model)
	{
		String firstName=employee.getFirstName();
		String email=employee.getEmail();
		String stack=employee.getStack();
		String time_stamp=employee.getTime_stamp();
		long Employeeid=employee.getEmployeeid();
		String instance_status= employeerepo.status(stack);
		if(instance_status.equals("A"))
		{
			employeerepo.savebyinsert(Employeeid, firstName, email, stack, time_stamp);
			instrepo.savebyname("NA",stack);
			List<Instance> instances=instrepo.findAll();
			model.addAttribute("instance", instances);
			return "home";
		}
		else
		{
			throw new RuntimeException(" VM IN USE!!");
		}
		
	}

	@PostMapping("/releaseInstance")
	public String release(@ModelAttribute("instance") Instance instance,Model model)
	{
		String stack= instance.getInstance_name();
		instrepo.savebyname("A",stack);
		List<Instance> instances=instrepo.findAll();
		model.addAttribute("instance", instances);
		return "home";
	}
	
	@GetMapping("/showRecent/{stack}")
	public String showRecent(@PathVariable ( value = "stack") String stack, Model model) {
		List<Employee> listEmployees = employeerepo.recent(stack);
		model.addAttribute("listEmployees", listEmployees);
		return  "recent";
	}
	@GetMapping("/{empid}")
	public String getten(@PathVariable ( value = "empid") Long Employeeid, Model model) {
		List<Employee> top = employeerepo.findemp(Employeeid);
		model.addAttribute("top", top);
		return  "getten";
	}
	
	@GetMapping("/logout")
	public String logout()
	{
		return "login";
	}
}
