document.addEventListener('DOMContentLoaded', () => {
  console.log('DOMContentLoaded 이벤트 발생!');
  
  // UI 요소 가져오기
  const addPatientBtn = document.getElementById('add-patient-btn');
  const patientListSection = document.getElementById('patient-list-section');
  const patientRegisterSection = document.getElementById('patient-register-section');
  const patientDetailSection = document.getElementById('patient-detail-section');
  const patientForm = document.getElementById('patient-form');
  const patientListTbody = document.getElementById('patient-list-tbody');
  const patientDetailDiv = document.getElementById('patient-detail');
  const backToListBtn = document.getElementById('back-to-list-btn');
  const cancelRegisterBtn = document.getElementById('cancel-register-btn');
  const searchForm = document.getElementById('searchForm');
  const resetSearchBtn = document.getElementById('resetSearchBtn');
  const editPatientBtn = document.getElementById('edit-patient-btn');
  
  // UI 섹션 표시/숨김 함수 (지역변수 사용 → 스코프 문제 해결)
  const showPatientListSection = () => {
    patientListSection.style.display = 'block';
    patientRegisterSection.style.display = 'none';
    patientDetailSection.style.display = 'none';
  };
  
  const showPatientRegisterSection = () => {
    patientListSection.style.display = 'none';
    patientRegisterSection.style.display = 'block';
    patientDetailSection.style.display = 'none';
  };
  
  const showPatientDetailSection = () => {
    patientListSection.style.display = 'none';
    patientRegisterSection.style.display = 'none';
    patientDetailSection.style.display = 'block';
  };
  
  // 폼 초기화 함수
  const resetRegisterForm = () => {
    patientForm.reset();
  };
  
  // 날짜 포맷팅 함수들
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR');
  };
  
  const formatDateTime = (dateTimeString) => {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleString('ko-KR');
  };
  
  // API 호출 및 데이터 처리 함수
  
  // 전체 환자 목록 조회 및 테이블 업데이트
  const fetchPatientList = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/patients');
      const patients = response.data;
      patientListTbody.innerHTML = ''; // 테이블 초기화
      patients.forEach(patient => displayPatient(patient));
    } catch (error) {
      console.error('환자 목록 조회 실패 :', error);
      alert('환자 목록 조회 실패했습니다.');
    }
  };
  
  // 환자 목록 테이블에 행 추가
  const displayPatient = (patient) => {
    const row = patientListTbody.insertRow();
    row.insertCell().textContent = patient.patientid;
    row.insertCell().textContent = patient.name;
    row.insertCell().textContent = formatDate(patient.birthdate);
    row.insertCell().textContent = patient.phone;
    row.insertCell().textContent = patient.address;
    row.insertCell().textContent = formatDate(patient.createdat);
    
    const detailCell = row.insertCell();
    const detailButton = document.createElement('button');
    detailButton.type = 'button';
    detailButton.textContent = '상세 정보';
    detailButton.className = 'btn btn-sm btn-primary detail-button';
    detailButton.dataset.patientId = patient.patientid;
    detailCell.appendChild(detailButton);
  };
  
  // 환자 등록 함수
  const registerPatient = async () => {
    const patientData = {
      name: document.getElementById('name').value,
      birthdate: document.getElementById('birthdate').value,
      phone: document.getElementById('phone').value,
      address: document.getElementById('address').value
    };
    
    try {
      const response = await axios.post('http://localhost:8080/api/patients', patientData);
      if (response.status === 201) {
        alert('환자가 성공적으로 등록되었습니다.');
        resetRegisterForm();
        showPatientListSection();
        fetchPatientList();
      } else {
        alert('환자 등록에 실패했습니다.');
      }
    } catch (error) {
      console.error('환자 등록 실패:', error);
      alert('환자 등록 중 오류가 발생했습니다.');
    }
  };
  
  // 특정 환자 상세 정보 조회 및 표시
  const showPatientDetail = async (patientId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/patients/${patientId}`);
      const patient = response.data;
      patientDetailDiv.innerHTML = `
            <ul class="list-group">
                <li class="list-group-item">ID: ${patient.patientid}</li>
                <li class="list-group-item">이름: ${patient.name}</li>
                <li class="list-group-item">생년월일: ${formatDate(patient.birthdate)}</li>
                <li class="list-group-item">전화번호: ${patient.phone}</li>
                <li class="list-group-item">주소: ${patient.address}</li>
                <li class="list-group-item">등록일: ${formatDateTime(patient.createdat)}</li>
            </ul>
        `;
      showPatientDetailSection();
    } catch (error) {
      console.error('환자 상세 정보 조회 실패:', error);
      alert('환자 상세 정보를 불러오는데 실패했습니다.');
    }
  };
  
  // 환자 검색 함수
  const searchPatients = async (name, phone) => {
    const apiUrl = `http://localhost:8080/api/patients/search?name=${name}&phone=${phone}`;
    try {
      const response = await axios.get(apiUrl);
      const patients = response.data;
      patientListTbody.innerHTML = '';
      if (patients.length === 0) {
        patientListTbody.innerHTML = '<tr><td colspan="7" class="text-center">검색 결과가 없습니다.</td></tr>';
      } else {
        patients.forEach(patient => displayPatient(patient));
      }
    } catch (error) {
      console.error('환자 검색 실패:', error);
      alert('환자 검색에 실패했습니다.');
    }
  };
  
  // --- 이벤트 리스너 설정 ---
  
  // 페이지 로딩 후 전체 환자 목록 조회
  fetchPatientList();
  
  // "환자 등록" 버튼 클릭 시 등록 섹션 표시
  addPatientBtn.addEventListener('click', () => {
    showPatientRegisterSection();
    resetRegisterForm();
  });
  
  // "취소" 버튼 클릭 시 목록 섹션 표시
  cancelRegisterBtn.addEventListener('click', () => {
    showPatientListSection();
  });
  
  // 등록 폼 제출 이벤트
  patientForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    registerPatient();
  });
  
  // 환자 목록 테이블에서 "상세 정보" 버튼 클릭 (이벤트 위임)
  patientListTbody.addEventListener('click', (event) => {
    if (event.target.classList.contains('detail-button')) {
      const patientId = event.target.closest('tr').firstChild.textContent;
      showPatientDetail(patientId);
    }
  });
  
  // "목록으로 돌아가기" 버튼 클릭 시 목록 섹션 표시
  backToListBtn.addEventListener('click', () => {
    showPatientListSection();
  });
  
  // "수정" 버튼 클릭 시 (미구현)
  editPatientBtn.addEventListener('click', () => {
    alert('환자 정보 수정 기능은 아직 구현 중입니다.');
  });
  
  // 검색 폼 제출 이벤트
  searchForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const searchName = document.getElementById('searchName').value;
    const searchPhone = document.getElementById('searchPhone').value;
    searchPatients(searchName, searchPhone);
  });
  
  // "초기화" 버튼 클릭 시 검색 필드 초기화 및 전체 목록 조회
  resetSearchBtn.addEventListener('click', () => {
    document.getElementById('searchName').value = '';
    document.getElementById('searchPhone').value = '';
    fetchPatientList();
  });
});
