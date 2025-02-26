package hospital.mapper; // <-- 패키지 선언을 hospital.mapper 로 지정

import hospital.dto.DoctorDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // 마이바티스 Mapper 인터페이스임을 선언하는 어노테이션
public interface DoctorMapper {
    // 의사 등록
    int insertDoctor(DoctorDto doctorDto);

    // 전체 의사 목록 조회
    List<DoctorDto> selectAllDoctors();

    // 특정 의사 상세 조회 (ID로 조회)
    DoctorDto selectDoctorById(int doctorid);

    // 의사 정보 수정
    int updateDoctor(DoctorDto doctorDto);

    // 의사 정보 삭제 (ID로 삭제)
    int deleteDoctorById(int doctorid);
}