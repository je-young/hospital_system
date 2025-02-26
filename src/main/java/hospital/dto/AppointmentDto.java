package hospital.dto; // <-- 패키지 선언을 hospital.dto 로 지정

import lombok.*;

import java.util.Date;
import java.sql.Time;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class AppointmentDto {
    private int appointmentid;   // 예약 ID (자동 생성)
    private int patientid;       // 환자 ID (Patient 테이블 pk 참조)
    private int doctorid;        // 의사 ID (Doctor 테이블 pk 참조)
    private Date appointmentdate;  // 예약 날짜
    private Time appointmenttime;  // 예약 시간
    private int status;          // 예약 상태 (예: 1: 예약 완료, 0: 예약 취소)
    private Date createdat;      // 생성일시 (자동 생성)
}