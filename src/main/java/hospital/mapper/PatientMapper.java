package hospital.mapper; // <-- 패키지 선언을 hospital.mapper 로 지정

import hospital.dto.PatientDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // 마이바티스 Mapper 인터페이스임을 선언하는 어노테이션
public interface PatientMapper {
    // 환자 등록
    int insertPatient(PatientDto patientDto);

    // 전체 환자 목록 조회
    List<PatientDto> selectAllPatients();

    // 특정 환자 상세 조회 (ID로 조회)
    PatientDto selectPatientById(int patientid);

    // 환자 정보 수정
    int updatePatient(PatientDto patientDto);

    // 환자 정보 삭제 (ID로 삭제)
    int deletePatientById(int patientid);
}