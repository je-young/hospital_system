package hospital.service;

import hospital.dto.PatientDto;
import hospital.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientMapper patientMapper;

    // 환자 등록
    public PatientDto registerPatient(PatientDto patientDto) {
        System.out.println("[PatientService] registerPatient - PatientDto: " + patientDto); // [1] 입력 DTO 로깅
        System.out.println("[PatientService] registerPatient - Calling patientMapper.insertPatient with patientDto: " + patientDto); // [2] Mapper 호출 전 로깅
        int result = patientMapper.insertPatient(patientDto);
        System.out.println("[PatientService] registerPatient - patientMapper.insertPatient result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return patientDto;
        } else {
            // 예외 처리: 환자 등록 실패
            RuntimeException exception = new RuntimeException("환자 등록에 실패했습니다."); // [4] 예외 객체 생성
            System.out.println("[PatientService] registerPatient - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }

    // 전체 환자 목록 조회
    public List<PatientDto> getAllPatients() {
        System.out.println("[PatientService] getAllPatients - Calling patientMapper.selectAllPatients"); // [2] Mapper 호출 전 로깅
        List<PatientDto> patients = patientMapper.selectAllPatients();
        System.out.println("[PatientService] getAllPatients - patientMapper.selectAllPatients result: " + patients.size() + " patients"); // [3] Mapper 결과 로깅
        return patients;
    }

    // 특정 환자 상세 조회 (ID로 조회)
    public PatientDto getPatientById(int patientId) {
        System.out.println("[PatientService] getPatientById - patientId: " + patientId); // [1] 입력 파라미터 로깅
        System.out.println("[PatientService] getPatientById - Calling patientMapper.selectPatientById with patientId: " + patientId); // [2] Mapper 호출 전 로깅
        PatientDto patient = patientMapper.selectPatientById(patientId);
        System.out.println("[PatientService] getPatientById - patientMapper.selectPatientById result: " + patient); // [3] Mapper 결과 로깅
        return patient;
    }

    // 환자 정보 수정
    public PatientDto modifyPatient(PatientDto patientDto) {
        System.out.println("[PatientService] modifyPatient - PatientDto: " + patientDto); // [1] 입력 DTO 로깅
        System.out.println("[PatientService] modifyPatient - Calling patientMapper.updatePatient with patientDto: " + patientDto); // [2] Mapper 호출 전 로깅
        int result = patientMapper.updatePatient(patientDto);
        System.out.println("[PatientService] modifyPatient - patientMapper.updatePatient result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return patientDto;
        } else {
            // 예외 처리: 환자 정보 수정 실패
            RuntimeException exception = new RuntimeException("환자 정보 수정에 실패했습니다. patientId: " + patientDto.getPatientid()); // [4] 예외 객체 생성
            System.out.println("[PatientService] modifyPatient - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }

    // 환자 정보 삭제 (ID로 삭제)
    public boolean removePatient(int patientId) {
        System.out.println("[PatientService] removePatient - patientId: " + patientId); // [1] 입력 파라미터 로깅
        System.out.println("[PatientService] removePatient - Calling patientMapper.deletePatientById with patientId: " + patientId); // [2] Mapper 호출 전 로깅
        int result = patientMapper.deletePatientById(patientId);
        System.out.println("[PatientService] removePatient - patientMapper.deletePatientById result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return true; // 삭제 성공 시 true 반환 (boolean 타입으로 변경)
        } else {
            // 예외 처리: 환자 정보 삭제 실패
            RuntimeException exception = new RuntimeException("환자 정보 삭제에 실패했습니다. patientId: " + patientId); // [4] 예외 객체 생성
            System.out.println("[PatientService] removePatient - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }
}