package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.config.ModelMapperConfig.MyModelMapper;
import net.skhu.dto.DepartmentDto;
import net.skhu.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired DepartmentRepository departmentRepository;
    @Autowired MyModelMapper modelMapper;

    public List<DepartmentDto> findAll() {
        return modelMapper.mapList(departmentRepository.findAll(), DepartmentDto.class);
    }

}
