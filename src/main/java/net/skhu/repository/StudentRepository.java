package net.skhu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.dto.Pagination;
import net.skhu.entity.Student;

public interface StudentRepository  extends JpaRepository<Student, Integer> {

    Student findByStudentNo(String studentNo);

    public default List<Student> findAll(Pagination pagination) {
        Page<Student> page = this.findAll(PageRequest.of(pagination.getPg() - 1, pagination.getSz(),
                                           Sort.Direction.ASC, "id"));
        pagination.setRecordCount((int)page.getTotalElements());
        return page.getContent();
    }
}
