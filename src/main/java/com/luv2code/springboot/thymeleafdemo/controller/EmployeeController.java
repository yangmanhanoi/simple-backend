package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService theService)
    {
        this.employeeService = theService;
    }
    @GetMapping("/list")
    public String getEmployeeList(Model theModel)
    {
        List<Employee> employeeList = employeeService.findAll();
        theModel.addAttribute("employees", employeeList);
        return "employees/employee-list";
    }

    @GetMapping("/showFormForAdd")
    public String showAddForm(Model theModel)
    {
        // get the employee from the service
        Employee employee = new Employee();
        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", employee);
        return "employees/employee-add-form";
    }
    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("employeeId") int theId, Model theModel)
    {
        Employee theEmployee = employeeService.findById(theId);
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-add-form";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId)
    {
        employeeService.deleteById(theId);
        return "redirect:/employees/list";
    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, BindingResult result)
    {
        // save the employee to the db
        employeeService.save(employee);
        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
        /**
         * Post/Redirect/Get (PRG) is a web development design pattern that lets the page shown after a form submission
         * be reloaded, shared, or bookmarked without ill effects, such as submitting the form another time.
         *
         * Nói chung là khi POST xong nếu bình thường thì khi người dùng refresh lại page có thể
         * và gửi lại có thể dẫn đến gửi nhiều lần. Để tránh tình trạng đó thì redirect User đến một trang
         * GET thông báo đã thành công.
         * */
    }
}
