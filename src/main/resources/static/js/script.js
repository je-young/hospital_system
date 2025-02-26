// script.js
console.log('Hello, JS!');

// HTML 문서 로딩 완료 후 실행되는 이벤트 리스너 등록 (DOMContentLoaded 이벤트)
document.addEventListener('DOMContentLoaded', () => { // ✅ DOMContentLoaded 이벤트 리스너 - 모든 UI 요소 및 이벤트 리스너 등록 코드를 이 스코프 안에 위치시킴

    console.log('DOMContentLoaded 이벤트 발생!');

    // UI 요소 가져오기 - 모든 요소 `DOMContentLoaded` 스코프 안에서 가져와야 함
    const addPatientBtn = document.getElementById('add-patient-btn'); // "환자 추가" 버튼 요소
    const patientListSection = document.getElementById('patient-list-section'); // 환자 목록 세션 요소
    const patientRegisterSection = document.getElementById('patient-register-section'); // 환자 등록 세션 요소
    const patientDetailSection = document.getElementById('patient-detail-section'); // 환자 상세 정보 세션 요소
    const patientForm = document.getElementById('patient-form'); // 환자 등록 폼 요소
    console.log('patientForm 요소 (DOMContentLoaded):', patientForm); // patientForm 요소 로그 (DOMContentLoaded 시점)
    const patientListTbody = document.getElementById('patient-list-tbody'); // 환자 목록 테이블 Body 요소
    const patientDetailDiv = document.getElementById('patient-detail'); // 환자 상세 정보 표시 영역 요소
    const backToListBtn = document.getElementById('back-to-list-btn'); // "목록으로 돌아가기" 버튼 요소
    const cancelRegisterBtn = document.getElementById('cancel-register-btn'); // "등록 취소" 버튼 요소


    // 함수 정의 : 환자 목록 조회 및 표시
    async function fetchPatientList() { // async 함수 (비동기 함수) 선언: 환자 목록 조회 API 호출 및 화면 업데이트
        try {
            // API 호출 - 백엔드 API (GET /api/patients) 를 호출하여 환자 목록 데이터를 가져옴 (axios 라이브러리 사용)
            const response = await axios.get('http://localhost:8080/api/patients'); // axios.get() 메소드로 GET 요청, await 로 비동기 작업 완료까지 기다림
            const patients = response.data; // API 응답 데이터 (환자 목록 - PatientDto 배열) 를 patients 변수에 저장

            // UI 업데이트 - 환자 목록 테이블 UI 를 업데이트 (새로운 환자 목록으로 갱신)
            patientListTbody.innerHTML = ''; // 환자 목록 테이블 Body (<tbody>) 내용 초기화 (기존 목록 비우기)

            patients.forEach(patient => { // 환자 목록 데이터를 배열에서 돌어서 환자 데이터를 테이블 행(<tr>)로 표시
                const row = patientListTbody.insertRow(); // 테이블 행(<tr>) 생성
                row.insertCell().textContent = patient.patientid; // 테이블 행(<tr>)에 환자-ID (<td>) 추가
                row.insertCell().textContent = patient.name; // 테이블 행(<tr>)에 환자-이름 (<td>) 추가
                row.insertCell().textContent = patient.birthdate ? new Date(patient.birthdate).toLocaleDateString() : ''; // 테이블 행(<tr>)에 환자-생년월일 (<td>) 추가
                row.insertCell().textContent = patient.phone; // 테이블 행(<tr>)에 환자-전화번호 (<td>) 추가
                row.insertCell().textContent = patient.address; // 테이블 행(<tr>)에 환자-주소 (<td>) 추가
                row.insertCell().textContent = new Date(patient.createdat).toLocaleDateString(); // 테이블 행(<tr>)에 환자-등록일 (<td>) 추가

                // 상세 버튼 추가 - 각 환자 행의 마지막 셀에 "상세" 버튼을 추가하여 환자 상세 정보 보기 기능 제공
                const detailCell = row.insertCell(); // 테이블 행(<tr>)에 환자 상세 정보 상세 버튼 (<td>) 추가
                const detailButton = document.createElement('button'); // 환자 상세 정보 상세 버튼 (<button>) 생성
                detailButton.textContent = '상세'; // 환자 상세 정보 상세 버튼 (<button>)에 텍스트 ("상세") 추가
                detailButton.className = 'btn btn-sm btn-primary'; // 환자 상세 정보 상세 버튼 (<button>)에 CSS 클래스 ("btn btn-sm btn-primary") 추가
                detailButton.addEventListener('click', () => showPatientDetail(patient.patientid)); // "상세" 버튼 클릭 시 showPatientDetail 함수 호출, 파라미터로 환자 ID 전달 (특정 환자 상세 정보 조회)
                detailCell.appendChild(detailButton); // "상세" 버튼을 셀(<td>)의 자식 요소로 추가 (셀에 버튼 배치)
            }); // forEach end
        } catch (error) {
            console.error('환자 목록 조회 실패 :', error); // 환자 목록 조회 실패 시 예외처리
            alert('환자 목록 조회 실패했습니다.'); // 환자 목록 조회 실패 시 알림
        } // try-catch end
    } // fetchPatientList end
    fetchPatientList(); // 페이지 로딩 완료 후 환자 목록 조회 및 표시


    // 환자 등록 폼 제출 이벤트 처리 - addPatientBtn 클릭 이벤트 리스너 내부로 이동 (변수 scope 문제 해결)
    addPatientBtn.addEventListener('click', () => { // ✅ "환자 등록" 버튼 클릭 시 이벤트 리스너 - 이제 addPatientBtn 은 DOMContentLoaded 스코프 안에서 유효함
        patientListSection.style.display = 'none';
        patientDetailSection.style.display = 'none';
        patientRegisterSection.style.display = 'block'; // 환자 등록 화면 표시

        const patientForm = document.getElementById('patient-form'); // ✅ patientForm 요소 가져오는 코드 - addPatientBtn 클릭 시점에 가져오도록 이동 (이전 오류 해결)
        console.log('patientForm 요소 (addPatientBtn 클릭 시):', patientForm); // patientForm 요소 로그 (확인용)


        patientForm.addEventListener('submit', async (event) => { // 환자 등록 폼 (id="patient-form") submit 이벤트 발생 시 실행될 이벤트 리스너 등록
            event.preventDefault(); // form 기본 submit 동작 중단 (페이지 새로고침 방지)

            // 폼 데이터 FormData 객체에서 추출
            const formData = new FormData(patientForm); // FormData 객체 생성, 폼 데이터 담기
            const patientData = { // FormData 에서 추출한 값을 이용하여 환자 정보 객체 (patientData) 생성
                name: formData.get('name'), // 폼 name 필드 값 가져오기
                birthdate: formData.get('birthdate'), // 폼 birthdate 필드 값 가져오기
                phone: formData.get('phone'), // 폼 phone 필드 값 가져오기
                address: formData.get('address') // 폼 address 필드 값 가져오기
            }; // patientData end

            try {
                // API 호출 - 백엔드 API (POST /api/patients) 를 호출하여 환자 등록 요청
                const response = await axios.post('http://localhost:8080/api/patients', patientData); // axios.post() 로 POST 요청, API 엔드포인트 URL 과 요청 body (환자 정보) 전달

                if (response.status === 201) { // HTTP 응답 상테 코드 201 (Created, 환자 등록 성공) 여부 확인
                    alert('환자가 성공적으로 등록되었습니다.');
                    patientForm.reset(); // 환자 등록 폼 리셋
                    patientRegisterSection.style.display = 'none'; // 환자 등록 화면 감추기
                    patientListSection.style.display = 'block'; // 환자 목록 화면 표시
                    fetchPatientList(); // 환자 목록 조회(갱신된 환자 목록 표시)
                } else { // HTTP 응답 상태 코드 201 이 아닌 경우 (예상치 못한 오류)
                    alert('환자 등록에 실패했습니다.');
                } // if-else end
            } catch (error) {
                console.log('환자 등록 실패:', error); // 콘솔에 오류 메시지 출력
            } // try-catch end
        }); // submit end - patientForm.addEventListener
    }); // addPatientBtn.addEventListener - DOMContentLoaded 스코프 안으로 이동
}); // DOMContentLoaded end// script.js
