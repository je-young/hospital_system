package hospital.mapper; // <-- 패키지 선언을 hospital.mapper 로 지정

import hospital.dto.AppointmentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.time.LocalDate; // LocalDate import 추가

@Mapper // 마이바티스 Mapper 인터페이스임을 선언하는 어노테이션
public interface AppointmentMapper {
    // 진료 예약 등록
    int insertAppointment(AppointmentDto appointmentDto);

    // 전체 진료 예약 목록 조회 (특정 날짜 X)
    List<AppointmentDto> selectAllAppointments();

    // 특정 진료 예약 상세 조회 (ID로 조회)
    AppointmentDto selectAppointmentById(int appointmentid);

    // 특정 날짜의 진료 예약 목록 조회
    List<AppointmentDto> selectAppointmentsByDate(LocalDate appointmentDate); // 파라미터 타입을 LocalDate 로 변경

    // 환자별 진료 예약 목록 조회
    List<AppointmentDto> selectAppointmentsByPatientId(int patientid);

    // 의사별 진료 예약 목록 조회
    List<AppointmentDto> selectAppointmentsByDoctorId(int doctorid);

    // 진료 예약 수정
    int updateAppointment(AppointmentDto appointmentDto);

    // 진료 예약 취소 (ID로 삭제)
    int deleteAppointmentById(int appointmentid);
}