package net.skhu.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class StudentDto {
    int id;

    @NotEmpty(message="이름을 입력하세요")
    @Size(min=2, max=45, message="2자리 이상 45지라 이하이어야 합니다")
    String name;

    @NotEmpty(message="학번을 입력하세요")
    @Size(min=7, max=9, message="7자리이거나 9자리이어야 합니다")
    String studentNo;

    @NotEmpty(message="이메일 주소를 입력하세요")
    @Email(message="이메일 주소가 올바르지 않습니다")
    String email;

    @Min(value=1, message="학과를 선택하세요")
    int departmentId;

    String phone;
    String sex;
    String departmentName;
}
