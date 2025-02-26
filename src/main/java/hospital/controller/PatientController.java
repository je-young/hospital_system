package hospital.controller;

import hospital.dto.PatientDto;
import hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // (1) 환자 등록 API 엔드포인트 (POST /api/patients)
    @PostMapping("")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patientDto) {
        try {
            PatientDto registeredPatient = patientService.registerPatient(patientDto);
            return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED); // 201 Created 상태 코드와 함께 응답
        } catch (RuntimeException e) {
            // 예외 처리: 등록 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }

    // (2) 전체 환자 목록 조회 API 엔드포인트 (GET /api/patients)
    @GetMapping("")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
    }

    // (3) 특정 환자 상세 조회 API 엔드포인트 (GET /api/patients/{patientId})
    @GetMapping("/{patientid}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable int patientid) {
        PatientDto patient = patientService.getPatientById(patientid);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found 상태 코드 응답 (환자 정보 없음)
        }
    }

    // (4) 환자 정보 수정 API 엔드포인트 (PUT /api/patients/{patientId})
    @PutMapping("/{patientid}")
    public ResponseEntity<PatientDto> modifyPatient(@PathVariable int patientid, @RequestBody PatientDto patientDto) {
        try {
            patientDto.setPatientid(patientid);
            PatientDto modifiedPatient = patientService.modifyPatient(patientDto);
            return new ResponseEntity<>(modifiedPatient, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
        } catch (RuntimeException e) {
            // 예외 처리: 수정 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }

    // (5) 환자 정보 삭제 API 엔드포인트 (DELETE /api/patients/{patientId})
    @DeleteMapping("/{patientid}")
    public ResponseEntity<Void> removePatient(@PathVariable int patientid) {
        try {
            boolean removed = patientService.removePatient(patientid);
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