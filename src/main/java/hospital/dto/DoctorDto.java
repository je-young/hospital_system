package hospital.dto; // <-- 패키지 선언을 hospital.dto 로 지정

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class DoctorDto {
    private int doctorid;        // 의사 ID (자동 생성)
    private String name;         // 의사 이름
    private String specialty;    // 의사 전문 분야
    private String phone;        // 의사 전화번호
    private Date createdat;      // 생성일시 (자동 생성)
}