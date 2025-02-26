package hospital.dto; // <-- 패키지 선언을 hospital.dto 로 지정

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class PatientDto {
    private int patientid;       // 환자 ID (자동 생성)
    private String name;         // 환자 이름
    private Date birthdate;      // 환자 생년월일
    private String phone;        // 환자 전화번호
    private String address;      // 환자 주소
    private Date createdat;      // 생성일시 (자동 생성)
}