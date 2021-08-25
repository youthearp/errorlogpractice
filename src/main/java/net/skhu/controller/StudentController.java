package net.skhu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.skhu.dto.Pagination;
import net.skhu.dto.StudentDto;
import net.skhu.service.DepartmentService;
import net.skhu.service.StudentService;

@Controller
@RequestMapping("student")
@Slf4j
public class StudentController {

    @Autowired StudentService studentService;
    @Autowired DepartmentService departmentService;

    @RequestMapping("list")
    public String list(Model model, Pagination pagination) {
        log.debug("list {}", pagination);
        model.addAttribute("students", studentService.findAll(pagination));
        return "student/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
        log.debug("create {}", pagination);
        model.addAttribute("student", new StudentDto());
        model.addAttribute("departments", departmentService.findAll());
        return "student/edit";
    }

    @PostMapping("create")
    public String create(Model model, Pagination pagination,
            @Valid @ModelAttribute("student") StudentDto studentDto, BindingResult bindingResult) {
        log.debug("create {} {}", studentDto, pagination);
        try {
            if (studentService.hasErrors(studentDto, bindingResult) == false) {
                studentService.insert(studentDto);
                int lastPage = (int)Math.ceil((double)studentService.count() / pagination.getSz());
                pagination.setPg(lastPage);
                return "redirect:list?" + pagination.getQueryString();
            }
        }
        catch (Exception exception) {
            bindingResult.reject("error", "등록 실패");
            log.error("등록 실패", exception);
        }
        model.addAttribute("departments", departmentService.findAll());
        return "student/edit";
    }

    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id, Pagination pagination) {
        log.debug("edit {} {}", id, pagination);
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("departments", departmentService.findAll());
        return "student/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Pagination pagination,
            @Valid @ModelAttribute("student") StudentDto studentDto, BindingResult bindingResult) {
        log.debug("save {} {}", studentDto, pagination);
        try {
            if (studentService.hasErrors(studentDto, bindingResult) == false) {
                studentService.update(studentDto);
                return "redirect:list?" + pagination.getQueryString();
            }
        }
        catch (Exception exception) {
            bindingResult.reject("error", "저장 실패");
            log.error("저장 실패", exception);
        }
        model.addAttribute("departments", departmentService.findAll());
        return "student/edit";
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, @RequestParam("id") int id, Pagination pagination,
            @Valid @ModelAttribute("student") StudentDto studentDto, BindingResult bindingResult) {
        log.debug("delete {} {} {}", id, studentDto, pagination);
        try {
            studentService.deleteById(id);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            bindingResult.reject("error", "삭제 실패");
            log.error("삭제 실패", exception);
        }
        model.addAttribute("departments", departmentService.findAll());
        return "student/edit";
    }
}

