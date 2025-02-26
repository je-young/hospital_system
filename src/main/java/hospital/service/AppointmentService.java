package hospital.service;

import hospital.dto.AppointmentDto;
import hospital.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    // 진료 예약 등록
    public AppointmentDto registerAppointment(AppointmentDto appointmentDto) {
        System.out.println("[AppointmentService] registerAppointment - AppointmentDto: " + appointmentDto); // [1] 입력 DTO 로깅
        System.out.println("[AppointmentService] registerAppointment - Calling appointmentMapper.insertAppointment with appointmentDto: " + appointmentDto); // [2] Mapper 호출 전 로깅
        int result = appointmentMapper.insertAppointment(appointmentDto);
        System.out.println("[AppointmentService] registerAppointment - appointmentMapper.insertAppointment result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return appointmentDto;
        } else {
            // 예외 처리: 진료 예약 등록 실패
            RuntimeException exception = new RuntimeException("진료 예약 등록에 실패했습니다."); // [4] 예외 객체 생성
            System.out.println("[AppointmentService] registerAppointment - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }

    // 전체 진료 예약 목록 조회
    public List<AppointmentDto> getAllAppointments() {
        System.out.println("[AppointmentService] getAllAppointments - Calling appointmentMapper.selectAllAppointments"); // [2] Mapper 호출 전 로깅
        List<AppointmentDto> appointments = appointmentMapper.selectAllAppointments();
        System.out.println("[AppointmentService] getAllAppointments - appointmentMapper.selectAllAppointments result: " + appointments.size() + " appointments"); // [3] Mapper 결과 로깅
        return appointments;
    }

    // 특정 진료 예약 상세 조회 (ID로 조회)
    public AppointmentDto getAppointmentById(int appointmentid) {
        System.out.println("[AppointmentService] getAppointmentById - appointmentId: " + appointmentid); // [1] 입력 파라미터 로깅
        System.out.println("[AppointmentService] getAppointmentById - Calling appointmentMapper.selectAppointmentById with appointmentId: " + appointmentid); // [2] Mapper 호출 전 로깅
        AppointmentDto appointment = appointmentMapper.selectAppointmentById(appointmentid);
        System.out.println("[AppointmentService] getAppointmentById - appointmentMapper.selectAppointmentById result: " + appointment); // [3] Mapper 결과 로깅
        return appointment;
    }

    // 특정 날짜의 진료 예약 목록 조회
    public List<AppointmentDto> getAppointmentsByDate(LocalDate appointmentDate) { // LocalDate 타입으로 변경
        System.out.println("[AppointmentService] getAppointmentsByDate - appointmentDate: " + appointmentDate); // [1] 입력 파라미터 로깅
        System.out.println("[AppointmentService] getAppointmentsByDate - Calling appointmentMapper.selectAppointmentsByDate with appointmentDate: " + appointmentDate); // [2] Mapper 호출 전 로깅
        List<AppointmentDto> appointments = appointmentMapper.selectAppointmentsByDate(appointmentDate); // Mapper 도 LocalDate 타입으로 변경 필요
        System.out.println("[AppointmentService] getAppointmentsByDate - appointmentMapper.selectAppointmentsByDate result: " + appointments.size() + " appointments"); // [3] Mapper 결과 로깅
        return appointments;
    }

    // 환자별 진료 예약 목록 조회
    public List<AppointmentDto> getAppointmentsByPatientId(int patientid) {
        System.out.println("[AppointmentService] getAppointmentsByPatientId - patientId: " + patientid); // [1] 입력 파라미터 로깅
        System.out.println("[AppointmentService] getAppointmentsByPatientId - Calling appointmentMapper.selectAppointmentsByPatientId with patientId: " + patientid); // [2] Mapper 호출 전 로깅
        List<AppointmentDto> appointments = appointmentMapper.selectAppointmentsByPatientId(patientid);
        System.out.println("[AppointmentService] getAppointmentsByPatientId - appointmentMapper.selectAppointmentsByPatientId result: " + appointments.size() + " appointments"); // [3] Mapper 결과 로깅
        return appointments;
    }

    // 의사별 진료 예약 목록 조회
    public List<AppointmentDto> getAppointmentsByDoctorId(int doctorid) {
        System.out.println("[AppointmentService] getAppointmentsByDoctorId - doctorId: " + doctorid); // [1] 입력 파라미터 로깅
        System.out.println("[AppointmentService] getAppointmentsByDoctorId - Calling appointmentMapper.selectAppointmentsByDoctorId with doctorId: " + doctorid); // [2] Mapper 호출 전 로깅
        List<AppointmentDto> appointments = appointmentMapper.selectAppointmentsByDoctorId(doctorid);
        System.out.println("[AppointmentService] getAppointmentsByDoctorId - appointmentMapper.selectAppointmentsByDoctorId result: " + appointments.size() + " appointments"); // [3] Mapper 결과 로깅
        return appointments;
    }

    // 진료 예약 수정
    public AppointmentDto modifyAppointment(AppointmentDto appointmentDto) {
        System.out.println("[AppointmentService] modifyAppointment - AppointmentDto: " + appointmentDto); // [1] 입력 DTO 로깅
        System.out.println("[AppointmentService] modifyAppointment - Calling appointmentMapper.updateAppointment with appointmentDto: " + appointmentDto); // [2] Mapper 호출 전 로깅
        int result = appointmentMapper.updateAppointment(appointmentDto);
        System.out.println("[AppointmentService] modifyAppointment - appointmentMapper.updateAppointment result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return appointmentDto;
        } else {
            // 예외 처리: 진료 예약 수정 실패
            RuntimeException exception = new RuntimeException("진료 예약 수정에 실패했습니다. appointmentId: " + appointmentDto.getAppointmentid()); // [4] 예외 객체 생성
            System.out.println("[AppointmentService] modifyAppointment - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }

    // 진료 예약 취소 (ID로 삭제)
    public boolean removeAppointment(int appointmentid) {
        System.out.println("[AppointmentService] removeAppointment - appointmentId: " + appointmentid); // [1] 입력 파라미터 로깅
        System.out.println("[AppointmentService] removeAppointment - Calling appointmentMapper.deleteAppointmentById with appointmentId: " + appointmentid); // [2] Mapper 호출 전 로깅
        int result = appointmentMapper.deleteAppointmentById(appointmentid);
        System.out.println("[AppointmentService] removeAppointment - appointmentMapper.deleteAppointmentById result: " + result); // [3] Mapper 결과 로깅
        if (result == 1) {
            return true; // 삭제 성공 시 true 반환 (boolean 타입으로 변경)
        } else {
            // 예외 처리: 진료 예약 취소 실패
            RuntimeException exception = new RuntimeException("진료 예약 취소에 실패했습니다. appointmentId: " + appointmentid); // [4] 예외 객체 생성
            System.out.println("[AppointmentService] removeAppointment - Exception occurred: " + exception.getMessage()); // [4] 예외 로깅
            throw exception; // [4] 예외 던지기
        }
    }
}