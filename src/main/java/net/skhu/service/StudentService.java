package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;
import net.skhu.config.ModelMapperConfig.MyModelMapper;
import net.skhu.dto.Pagination;
import net.skhu.dto.StudentDto;
import net.skhu.entity.Student;
import net.skhu.repository.StudentRepository;

@Service
@Slf4j
public class StudentService {

    @Autowired StudentRepository studentRepository;
    @Autowired MyModelMapper modelMapper;

    public List<StudentDto> findAll(Pagination pagination) {
        var list = modelMapper.mapList(studentRepository.findAll(pagination), StudentDto.class);
        log.debug("findAll {} - return {} records, {}", pagination, list.size(), list);
        return list;
    }

    public StudentDto findById(int id) {
        Student student = studentRepository.findById(id).get();
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        log.debug("findById {} - return {}", id, studentDto);
        return studentDto;
    }

    public int count() {
        long count = studentRepository.count();
        log.debug("count - return {}", count);
        return (int)count;
    }

    public void insert(StudentDto studentDto) {
        log.debug("insert {}", studentDto);
        Student student = modelMapper.map(studentDto, Student.class);
        studentRepository.save(student);
    }

    public void update(StudentDto studentDto) {
        log.debug("update {}", studentDto);
        Student student = modelMapper.map(studentDto, Student.class);
        studentRepository.save(student);
}

    public void deleteById(int id) {
        log.debug("deleteById {}", id);
        studentRepository.deleteById(id);
    }

    public boolean hasErrors(StudentDto studentDto, BindingResult bindingResult) {
        boolean result = false;
        if (bindingResult.hasErrors())
            result = true;
        else {
            Student student = studentRepository.findByStudentNo(studentDto.getStudentNo());
            if (student != null) {
                if (studentDto.getId() == 0) { // insert
                    bindingResult.rejectValue("studentNo", null, "학번이 중복됩니다.");
                    result = true;
                }
                else if (studentDto.getId() != student.getId()) { // update
                    bindingResult.rejectValue("studentNo", null, "학번이 중복됩니다.");
                    result = true;
                }
            }
        }
        log.debug("hasErrorsd {} - return {} {}", studentDto, result, bindingResult.getAllErrors());
        return result;
    }

}

