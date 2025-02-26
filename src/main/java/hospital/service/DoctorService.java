package hospital.service;

import hospital.dto.DoctorDto;
import hospital.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    // 의사 등록
    public DoctorDto registerDoctor(DoctorDto doctorDto) {
        System.out.println("[DoctorService] registerDoctor - DoctorDto: " + doctorDto); // [1] 입력 DTO 로깅
        System.out.println("[DoctorService] registerDoctor - Calling doctorMapper.insertDoctor with doctorDto: " + doctorDto); // [2] Mapper 호출 전 로깅
        int result = doctorMapper.insertDoctor(doctorDto);
        System.out.println("[DoctorService] registerDoctor - doctorMapper.insertDoctor result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return doctorDto;
        } else {
            // 예외 처리: 의사 등록 실패
            RuntimeException exception = new RuntimeException("의사 등록에 실패했습니다."); // [4] 예외 객체 생성
            System.out.println("[DoctorService] registerDoctor - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }

    // 전체 의사 목록 조회
    public List<DoctorDto> getAllDoctors() {
        System.out.println("[DoctorService] getAllDoctors - Calling doctorMapper.selectAllDoctors"); // [2] Mapper 호출 전 로깅
        List<DoctorDto> doctors = doctorMapper.selectAllDoctors();
        System.out.println("[DoctorService] getAllDoctors - doctorMapper.selectAllDoctors result: " + doctors.size() + " doctors"); // [3] Mapper 결과 로깅
        return doctors;
    }

    // 특정 의사 상세 조회 (ID로 조회)
    public DoctorDto getDoctorById(int doctorid) {
        System.out.println("[DoctorService] getDoctorById - doctorId: " + doctorid); // [1] 입력 파라미터 로깅
        System.out.println("[DoctorService] getDoctorById - Calling doctorMapper.selectDoctorById with doctorId: " + doctorid); // [2] Mapper 호출 전 로깅
        DoctorDto doctor = doctorMapper.selectDoctorById(doctorid);
        System.out.println("[DoctorService] getDoctorById - doctorMapper.selectDoctorById result: " + doctor); // [3] Mapper 결과 로깅
        return doctor;
    }

    // 의사 정보 수정
    public DoctorDto modifyDoctor(DoctorDto doctorDto) {
        System.out.println("[DoctorService] modifyDoctor - DoctorDto: " + doctorDto); // [1] 입력 DTO 로깅
        System.out.println("[DoctorService] modifyDoctor - Calling doctorMapper.updateDoctor with doctorDto: " + doctorDto); // [2] Mapper 호출 전 로깅
        int result = doctorMapper.updateDoctor(doctorDto);
        System.out.println("[DoctorService] modifyDoctor - doctorMapper.updateDoctor result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return doctorDto;
        } else {
            // 예외 처리: 의사 정보 수정 실패
            RuntimeException exception = new RuntimeException("의사 정보 수정에 실패했습니다. " + doctorDto.getDoctorid()); // [4] 예외 객체 생성
            System.out.println("[DoctorService] modifyDoctor - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }

    // 의사 정보 삭제 (ID로 삭제)
    public boolean removeDoctor(int doctorid) {
        System.out.println("[DoctorService] removeDoctor - doctorId: " + doctorid); // [1] 입력 파라미터 로깅
        System.out.println("[DoctorService] removeDoctor - Calling doctorMapper.deleteDoctorById with doctorId: " + doctorid); // [2] Mapper 호출 전 로깅
        int result = doctorMapper.deleteDoctorById(doctorid);
        System.out.println("[DoctorService] removeDoctor - doctorMapper.deleteDoctorById result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return true; // 삭제 성공 시 true 반환 (boolean 타입으로 변경)
        } else {
            // 예외 처리: 의사 정보 삭제 실패
            RuntimeException exception = new RuntimeException("의사 정보 삭제에 실패했습니다. " + doctorid); // [4] 예외 객체 생성
            System.out.println("[DoctorService] removeDoctor - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }
}