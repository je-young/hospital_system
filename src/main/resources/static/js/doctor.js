document.addEventListener('DOMContentLoaded', () => {
  console.log('DOMContentLoaded 이벤트 발생!');
  
  // UI 요소 가져오기
  const addDoctorBtn = document.getElementById('add-doctor-btn');
  const doctorListSection = document.getElementById('doctor-list-section');
  const doctorRegisterSection = document.getElementById('doctor-register-section');
  const doctorDetailSection = document.getElementById('doctor-detail-section');
  const doctorForm = document.getElementById('doctor-form');
  const doctorListTbody = document.getElementById('doctor-list-tbody');
  const doctorDetailDiv = document.getElementById('doctor-detail');
  const backToListBtn = document.getElementById('back-to-list-btn');
  const cancelRegisterBtn = document.getElementById('cancel-register-btn');
  const searchForm = document.getElementById('searchForm');
  const resetSearchBtn = document.getElementById('resetSearchBtn');
  const editDoctorBtn = document.getElementById('edit-doctor-btn');
  
  // 페이지 로딩 완료 후 의사 목록 조회 및 표시
  fetchDoctorList();
  
  // "의사 등록" 버튼 클릭 시 의사 등록 섹션 표시
  addDoctorBtn.addEventListener('click', () => {
    showDoctorRegisterSection();
    resetRegisterForm();
  });
  
  // "취소" 버튼 클릭 시 목록 섹션 표시
  cancelRegisterBtn.addEventListener('click', () => {
    showDoctorListSection();
  });
  
  // 의사 등록 폼 제출 이벤트 처리
  doctorForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    registerDoctor();
  });
  
  // 의사 목록 테이블 (이벤트 위임): "상세 정보" 버튼 클릭 시 의사 상세 정보 조회
  doctorListTbody.addEventListener('click', function(event) {
    if (event.target.classList.contains('detail-button')) {
      const doctorId = event.target.closest('tr').firstChild.textContent;
      showDoctorDetail(doctorId);
    }
  });
  
  // "목록으로 돌아가기" 버튼 클릭 시 의사 목록 섹션 표시
  backToListBtn.addEventListener('click', () => {
    showDoctorListSection();
  });
  
  // "수정" 버튼 클릭 시 의사 정보 수정 페이지로 이동 (아직 미구현)
  editDoctorBtn.addEventListener('click', () => {
    alert('의사 정보 수정 기능은 아직 구현 중입니다.');
  });
  
  // 검색 폼 제출 이벤트 처리 (이름, 전문 분야 검색)
  searchForm.addEventListener('submit', function(event) {
    event.preventDefault();
    const searchName = document.getElementById('searchName').value;
    const searchSpecialty = document.getElementById('searchSpecialty').value;
    searchDoctors(searchName, searchSpecialty);
  });
  
  // "초기화" 버튼 클릭 이벤트 처리 (검색 폼 초기화 및 전체 목록 조회)
  resetSearchBtn.addEventListener('click', function() {
    document.getElementById('searchName').value = '';
    document.getElementById('searchSpecialty').value = '';
    fetchDoctorList(); // getAllDoctors() → fetchDoctorList()로 수정
  });
  
  // --- UI 섹션 표시/숨김 함수 ---
  const showDoctorListSection = () => {
    doctorListSection.style.display = 'block';
    doctorRegisterSection.style.display = 'none';
    doctorDetailSection.style.display = 'none';
  };
  
  const showDoctorRegisterSection = () => {
    doctorListSection.style.display = 'none';
    doctorRegisterSection.style.display = 'block';
    doctorDetailSection.style.display = 'none';
  };
  
  const showDoctorDetailSection = () => {
    doctorListSection.style.display = 'none';
    doctorRegisterSection.style.display = 'none';
    doctorDetailSection.style.display = 'block';
  };
  
  // --- 폼 초기화 함수 ---
  const resetRegisterForm = () => {
    doctorForm.reset();
  };
  
  // --- API 호출 및 데이터 처리 함수 ---
  
  // 전체 의사 목록 조회 API 호출 및 테이블 업데이트
  async function fetchDoctorList() {
    try {
      const doctorListTbody = document.getElementById('doctor-list-tbody');
      const response = await axios.get('http://localhost:8080/api/doctors');
      const doctors = response.data;
      doctorListTbody.innerHTML = '';
      doctors.forEach(doctor => displayDoctor(doctor));
    } catch (error) {
      console.error('의사 목록 조회 실패 :', error);
      alert('의사 목록을 불러오는데 실패했습니다.');
    }
  }
  
  // 의사 목록 테이블에 의사 행 추가
  const displayDoctor = (doctor) => {
    const row = doctorListTbody.insertRow();
    row.insertCell().textContent = doctor.doctorid;
    row.insertCell().textContent = doctor.name;
    row.insertCell().textContent = doctor.specialty;
    row.insertCell().textContent = doctor.phone;
    row.insertCell().textContent = formatDate(doctor.createdat);
    
    const detailCell = row.insertCell();
    const detailButton = document.createElement('button');
    detailButton.type = 'button';
    detailButton.textContent = '상세 정보';
    detailButton.className = 'btn btn-sm btn-primary detail-button';
    detailButton.dataset.doctorId = doctor.doctorid;
    detailCell.appendChild(detailButton);
  };
  
  // 의사 등록 API 호출 함수
  async function registerDoctor() {
    const doctorData = {
      name: document.getElementById('name').value,
      specialty: document.getElementById('specialty').value,
      phone: document.getElementById('phone').value
    };
    
    try {
      const response = await axios.post('http://localhost:8080/api/doctors', doctorData);
      if (response.status === 201) {
        alert('의사가 성공적으로 등록되었습니다.');
        resetRegisterForm();
        showDoctorListSection();
        fetchDoctorList();
      } else {
        alert('의사 등록에 실패했습니다.');
      }
    } catch (error) {
      console.error('의사 등록 실패:', error);
      alert('의사 등록 중 오류가 발생했습니다.');
    }
  }
  
  // 특정 의사 상세 정보 조회 API 호출 및 상세 정보 표시
  async function showDoctorDetail(doctorId) {
    try {
      const doctorDetailDiv = document.getElementById('doctor-detail');
      const response = await axios.get(`http://localhost:8080/api/doctors/${doctorId}`);
      const doctor = response.data;
      doctorDetailDiv.innerHTML = `
            <ul class="list-group">
                <li class="list-group-item">ID: ${doctor.doctorid}</li>
                <li class="list-group-item">이름: ${doctor.name}</li>
                <li class="list-group-item">진료과: ${doctor.specialty}</li>
                <li class="list-group-item">전화번호: ${doctor.phone}</li>
                <li class="list-group-item">등록일: ${formatDateTime(doctor.createdat)}</li>
            </ul>
        `;
      showDoctorDetailSection();
    } catch (error) {
      console.error('의사 상세 정보 조회 실패:', error);
      alert('의사 상세 정보를 불러오는데 실패했습니다.');
    }
  }
  
  // 의사 검색 API 호출 및 테이블 업데이트
  async function searchDoctors(name, specialty) {
    const apiUrl = `http://localhost:8080/api/doctors/search?name=${name}&specialty=${specialty}`;
    try {
      const response = await axios.get(apiUrl);
      const doctors = response.data;
      doctorListTbody.innerHTML = '';
      if (doctors.length === 0) {
        doctorListTbody.innerHTML = '<tr><td colspan="6" class="text-center">검색 결과가 없습니다.</td></tr>';
      } else {
        doctors.forEach(doctor => displayDoctor(doctor));
      }
    } catch (error) {
      console.error('의사 검색 실패:', error);
      alert('의사 검색에 실패했습니다.');
    }
  }
  
  // --- Helper 함수 ---
  
  // 날짜 포맷팅 (YYYY-MM-DD)
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR');
  };
  
  // 날짜/시간 포맷팅 (YYYY-MM-DD HH:mm:ss)
  const formatDateTime = (dateTimeString) => {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleString('ko-KR');
  };
  
}); // End of DOMContentLoaded
