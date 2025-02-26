// script.js
console.log('Hello, JS!');

// HTML 문서 로딩 완료 후 실행되는 이벤트 리스너 등록 (DOMContentLoaded 이벤트)
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOMContentLoaded 이벤트 발생!');
    // UI 요소 가져오기
    const addPatientBtn = document.getElementById('add-patient-btn'); // "환자 추가" 버튼 요소
    const patientListSection = document.getElementById('patient-list-section'); // 환자 목록 세션 요소
    const patientRegisterSection = document.getElementById('patient-register-section'); // 환자 등록 세션 요소
    const patientDetailSection = document.getElementById('patient-detail-section'); // 환자 상세 정보 세션 요소
    const patientForm = document.getElementById('patient-form'); // 환자 등록 폼 요소
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
                row.insertCell().textContent = patient.id; // 테이블 행(<tr>)에 환자-ID (<td>) 추가
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
                detailCell.appendChild(detailButton); // 환자 상세 정보 상세 버튼 (<button>)을 테이블 행(<tr>)의 마지막 셀에 추가
            }); // forEach end
        } catch (error) {
            console.error('환자 목록 조회 실패 :', error); // 환자 목록 조회 실패 시 예외처리
            alert('환자 목록 조회 실패했습니다.'); // 환자 목록 조회 실패 시 알림
        } // try-catch end
    } // fetchPatientList end
    fetchPatientList(); // 환자 목록 조회 및 표시
}); // DOMContentLoaded end

