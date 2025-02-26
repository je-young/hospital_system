package hospital.controller;

import hospital.dto.AppointmentDto;
import hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // (1) 진료 예약 등록 API 엔드포인트 (POST /api/appointments)
    @PostMapping("")
    public ResponseEntity<AppointmentDto> registerAppointment(@RequestBody AppointmentDto appointmentDto) {
        try {
            AppointmentDto registeredAppointment = appointmentService.registerAppointment(appointmentDto);
            return new ResponseEntity<>(registeredAppointment, HttpStatus.CREATED); // 201 Created 상태 코드와 함께 응답
        } catch (RuntimeException e) {
            // 예외 처리: 등록 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }

    // (2) 전체 진료 예약 목록 조회 API 엔드포인트 (GET /api/appointments)
    @GetMapping("")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
    }

    // (3) 특정 진료 예약 상세 조회 API 엔드포인트 (GET /api/appointments/{appointmentid})
    @GetMapping("/{appointmentid}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable int appointmentid) {
        AppointmentDto appointment = appointmentService.getAppointmentById(appointmentid);
        if (appointment != null) {
            return new ResponseEntity<>(appointment, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found 상태 코드 응답 (예약 정보 없음)
        }
    }

    // (4) 특정 날짜 진료 예약 목록 조회 API 엔드포인트 (GET /api/appointments/date)
    @GetMapping("/date")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDate(
            @RequestParam("appointmentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate) { // LocalDate 타입으로 요청 파라미터 받기
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByDate(appointmentDate);
        return new ResponseEntity<>(appointments, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
    }

    // (5) 환자별 진료 예약 목록 조회 API 엔드포인트 (GET /api/appointments/patient)
    @GetMapping("/patient")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByPatientId(@RequestParam int patientid) {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByPatientId(patientid);
        return new ResponseEntity<>(appointments, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
    }

    // (6) 의사별 진료 예약 목록 조회 API 엔드포인트 (GET /api/appointments/doctor)
    @GetMapping("/doctor")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDoctorId(@RequestParam int doctorid) {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByDoctorId(doctorid);
        return new ResponseEntity<>(appointments, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
    }

    // (7) 진료 예약 수정 API 엔드포인트 (PUT /api/appointments/{appointmentid})
    @PutMapping("/{appointmentid}")
    public ResponseEntity<AppointmentDto> modifyAppointment(@PathVariable int appointmentid, @RequestBody AppointmentDto appointmentDto) {
        try {
            appointmentDto.setAppointmentid(appointmentid);
            AppointmentDto modifiedAppointment = appointmentService.modifyAppointment(appointmentDto);
            return new ResponseEntity<>(modifiedAppointment, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
        } catch (RuntimeException e) {
            // 예외 처리: 수정 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }

    // (8) 진료 예약 취소 API 엔드포인트 (DELETE /api/appointments/{appointmentid})
    @DeleteMapping("/{appointmentid}")
    public ResponseEntity<Void> removeAppointment(@PathVariable int appointmentid) {
        try {
            boolean removed = appointmentService.removeAppointment(appointmentid);
            if (removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content 상태 코드 응답 (삭제 성공)
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found 상태 코드 응답 (삭제 대상 없음)
            }
        } catch (RuntimeException e) {
            // 예외 처리: 삭제 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }
}