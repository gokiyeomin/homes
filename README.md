# 🏠 고기여민 - 경북대 홈즈 🏠


## 🔗 링크
 🔗 [프론트엔드 레포지토리 링크](https://github.com/kdongsu5509/KNU_Computer_Hack2024_Flutter)
 
 </br>


## 📝 서비스 요약
<div align="center">
</br>

### 경북대 홈즈 - 자취방 이어살기 매칭 플랫폼

<img src="https://github.com/user-attachments/assets/dee08a3e-4ec5-404f-ad24-d28244b996a8" width="350px" height="350px" />
</div>


## 🧩 주제 구분
-   E타입 경북대에 다니는 다양한 배경의 학우들을 위한 서비스

</br>


## 🧑‍💻 팀원 소개
<!-- 팀명과 팀원 소개 -->
<div align="center">

### 🧑‍🤝‍🧑 고기여민 (Go Ki Yeo Min) 🧑‍🤝‍🧑


| 프론트엔드 | 백엔드 | 디자이너 | 백엔드 |
| :-----: | :-----: | :-----: | :-----: |
|[<img src="https://github.com/kdongsu5509.png" width="100px">](https://github.com/kdongsu5509) | [<img src="https://github.com/kiryanchi.png" width="100px">](https://github.com/kiryanchi) |[<img src="https://github.com/doodoo10.png" width="100px">](https://github.com/doodoo10) |[<img src="https://github.com/rnjs5540.png" width="100px">](https://github.com/rnjs5540) |
| 고동수 | 박기현 | 정여진 | 권용민 |

</br>

</div>


## 🔗 시연 영상
(필수) Youtube 링크
(선택) Github Repository 페이지에서 바로 볼 수 있도록 넣어주셔도 좋습니다.

## ⚙️ 서비스 소개
### 📚 서비스 개요
경북대 학생들을 위한 자취방 이어살기 매칭 플랫폼이다. 사용자는 쉽게 기존 거주자의 방을 이어받아 자취할 때 겪는 주거지 구하기의 어려움을 해결하고, 경제적 부담을 줄일 수 있다. 또한 플랫폼을 통해 매물에 대한 상세 정보를 확인하고, 간편하게 입주 조건을 맞출 수 있다.

### 🔧 타서비스와의 차별점
1. 현시점 거의 유일한 이어살기 거래 플랫폼
2. 구체적인 검색 조건 필터링 기능 제공
3. 즉각적인 채팅 기능

### 🛠️ 구현 내용 및 결과물
1. 이어살기 매물 등록
  - 매물의 위치, 가격, 입주 가능 날짜 등 주요 정보를 태그화하여, 글 작성시에도 입주 희망자가 필요로 하는 정보들을 잊지 않고 기입할 수 있다.
2. 매물 필터링 및 조회
  - 상세한 조건 필터를 통해 원하는 조건의 매물을 간편하게 조회할 수 있다.
3. 판매자 - 구매자 간 실시간 채팅 기능
  - 플랫폼 내에서 간편하게 실시간으로 매물별 채팅을 할 수 있다.

### 👁️ 전체적인 View
<img width="200" alt="경북대 홈즈 메인페이지" src="https://github.com/user-attachments/assets/75cb9f86-aab4-4a6d-849f-3193b98db1d0">
<img width="200" alt="경북대 홈즈 필터 이미지" src="https://github.com/user-attachments/assets/33bcc74d-d5fe-4652-bd2b-e529bc6ce32e">
<br>
<img width="200" alt="경북대 홈즈 채팅목록 이미지" src="https://github.com/user-attachments/assets/4b2b2d7e-2cea-4b2f-a676-0e5eb0f6b5e1">
<img width="200" alt="경북대 홈즈 채팅방 이미지" src="https://github.com/user-attachments/assets/216ba021-f165-4bd1-b0ba-2b248475fd50">


### ✨ ERD
<img width="1397" alt="경북대 홈즈 erd" src="https://github.com/user-attachments/assets/cde96ec8-6e93-49d0-8702-a47b6d51b51c">

</br>
</br>


### 구현 방식

#### 📌 frontend
- **사용 프레임워크**: Dart, Flutter
<img src="https://shields.io/badge/Dart-0175C2?style=flat-square&logo=Dart&logoColor=white" />
<img src="https://shields.io/badge/Flutter-02569B?style=flat-square&logo=Flutter&logoColor=white" />

- **디자인 도구**: Figma <img src="https://shields.io/badge/Figma-F24E1E?style=flat-square&logo=Figma&logoColor=white" />


#### 📌 backend 
- **사용 프레임워크**: Java, Spring Boot <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white">

- **데이터베이스**: MySQL <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
   - 매물, 사용자 정보, 채팅 로그 등을 저장 및 관리한다.

- **매물 검색**: Spring Data JPA Specification <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
  - 동적 쿼리를 작성해 데이터베이스에서 원하는 데이터를 효율적으로 조회한다.

- **이미지 파일 저장**: AWS S3
<img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=AmazonS3&logoColor=white"/>
   - 업로드된 이미지들은 S3 버킷을 통해 저장 및 호스팅된다.

   </br>


#### ⛓️ 보안
- **사용 기술**: JWT, Spring Security <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=flat-square&logo=jsonwebtokens&logoColor=white"/>
<img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>
  - JWT 토큰을 이용해 유저 인증 및 인가 과정을 안전하게 구현하고, 특정 리소스에 대한 접근 권한을 관리한다.

  </br>



#### 협업
- **사용 기술**: WebSocket, STOMP, SockJS
  - WebSocket을 사용하여 실시간 채팅 기능을 구현한다.
  - 이 때 STOMP 프로토콜을 사용하여 클라이언트 간 메시지 송수신 처리를 한다.
- **API 문서화**: Swagger
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=Swagger&logoColor=white"/>
  - API 문서를 자동으로 생성한다. GithubActions과의 연동을 통해, API 문서가 항상 최신 상태로 유지된다.

#### 🖥️ 배포
- **서버**: AWS EC2
<img src="https://img.shields.io/badge/AmazonS2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/>
- **CI/CD**: GitHub Actions
<img src="https://img.shields.io/badge/GithubActions-2088FF?style=flat-square&logo=githubactions&logoColor=white">
  - 코드 푸시나 PR 머지 등의 이벤트에 따라 **GitHub Actions**가 트리거되어 **자동으로 빌드 및 배포**가 이루어진다. 이로 인해 개발 워크플로우가 자동화되어, 코드 변경이 있을 때마다 빠르게 배포할 수 있다.
  - 지속적 통합/배포 자동화를 구현한다.

</br>



## 🎈 향후 개선 혹은 발전 방안

</br>


1. **거주 환경 평가 시스템 도입**<br>
사용자가 거주했던 주거지에 대한 평가 시스템을 도입하여, 매물의 상태를 더 정확하게 파악할 수 있도록 돕는다. 청결도, 소음 등 구체적인 정보를 평가하여 보다 신뢰성 높은 매물 정보를 제공하고 사용자 만족도를 높일 수 있다.

2. **지도 API 통합**<br>
지도 API를 도입해 사용자들이 매물의 위치를 직관적으로 파악할 수 있도록 개선한다. 이로써 학교와의 거리, 대중교통 접근성, 주변 편의시설 등을 쉽게 확인할 수 있다.

3. **외국인 학생 지원 서비스 확장**<br>
경북대에 재학중인 외국인 학생들을 위해 다국어 지원을 추가하고, 주거 관련 절차에 대한 가이드를 제공하여 더 다양한 배경의 사용자를 유치할 수 있다.

4. **기업 연계**<br>
이사 서비스 및 가구 대여 서비스와의 전략적 제휴를 통해 자취생들이 필요한 모든 주거 관련 서비스를 한 곳에서 제공하는 통합 플랫폼으로 발전할 수 있다. 또한, 자취 생필품 구독 서비스를 통해 추가적인 수익 모델을 창출할 수 있습니다.

5.	**지역 확장 및 커뮤니티 기반 플랫폼**<br>
현재는 경북대 학생들을 주 타겟으로 하지만, 향후 전국의 자취생들을 대상으로 서비스를 확장할 수 있다. 더 나아가 자취생들이 주거 팁이나 생활 정보를 공유할 수 있는 자취 커뮤니티 플랫폼으로 발전할 수 있습니다.

이러한 방안들을 통해 경북대 홈즈는 단순한 이어살기 매칭 서비스를 넘어, 자취에 필요한 다양한 요소를 종합적으로 제공하는 토털 자취 플랫폼으로 발전할 수 있다.
