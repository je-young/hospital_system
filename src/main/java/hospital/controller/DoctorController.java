package hospital.controller;

import hospital.dto.DoctorDto;
import hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // (1) 의사 등록 API 엔드포인트 (POST /api/doctors)
    @PostMapping("")
    public ResponseEntity<DoctorDto> registerDoctor(@RequestBody DoctorDto doctorDto) {
        try {
            DoctorDto registeredDoctor = doctorService.registerDoctor(doctorDto);
            return new ResponseEntity<>(registeredDoctor, HttpStatus.CREATED); // 201 Created 상태 코드와 함께 응답
        } catch (RuntimeException e) {
            // 예외 처리: 등록 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }

    // (2) 전체 의사 목록 조회 API 엔드포인트 (GET /api/doctors)
    @GetMapping("")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
    }

    // (3) 특정 의사 상세 조회 API 엔드포인트 (GET /api/doctors/{doctorid})
    @GetMapping("/{doctorid}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable int doctorid) {
        DoctorDto doctor = doctorService.getDoctorById(doctorid);
        if (doctor != null) {
            return new ResponseEntity<>(doctor, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found 상태 코드 응답 (의사 정보 없음)
        }
    }

    // (4) 의사 정보 수정 API 엔드포인트 (PUT /api/doctors/{doctorid})
    @PutMapping("/{doctorid}")
    public ResponseEntity<DoctorDto> modifyDoctor(@RequestBody DoctorDto doctorDto) {
        try {
            DoctorDto modifiedDoctor = doctorService.modifyDoctor(doctorDto);
            return new ResponseEntity<>(modifiedDoctor, HttpStatus.OK); // 200 OK 상태 코드와 함께 응답
        } catch (RuntimeException e) {
            // 예외 처리: 수정 실패
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드 응답
        }
    }

    // (5) 의사 정보 삭제 API 엔드포인트 (DELETE /api/doctors/{doctorId})
    @DeleteMapping("/{doctorid}")
    public ResponseEntity<Void> removeDoctor(@PathVariable int doctorid) {
        try {
            boolean removed = doctorService.removeDoctor(doctorid);
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