package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmployeeController {

    Map<Long, Employee> employeeMap = new HashMap<>();

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employeeHome", "employee", new Employee());
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("employee") final Employee employee, final BindingResult result, final ModelMap model) {
        if (result.hasErrors())
            return "error";


        model.addAttribute("name", employee.getName());
        model.addAttribute("contractNumber", employee.getContractNumber());
        model.addAttribute("id", employee.getId());
        employeeMap.put(employee.getId(), employee);
        return "index";

    }
}
