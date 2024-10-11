# SPRING PLUS


## [Level 1-1] 1. 코드 개선 퀴즈 - @Transactional의 이해
기존에 할 일 저장 기능을 구현한 API `/todos`를 호출하였을때, 오류가 발생하였습니다.
그 이유는 저장되는 데이터의 변화가 일어나는 `TodoService` 클랫스에 `Transactional(readOnly = true)`가 적용되어 있어서 데이터 읽기만 가능했기 때문입니다.
그리하여 수정이 발생하는 `saveTodo()`메서드에 `Transactional`을 적용하여 수정 작업이 가능하도록 해주었습니다.
![image](https://github.com/user-attachments/assets/afb285ac-d187-4e7b-859d-adebab3b1864)

</br></br>

## [Level 1-2] 2. 코드 추가 퀴즈 - JWT의 이해
User 정보에 `nickname` 컬럼을 추가해주면서 전체적인 코드를 기획자의 요구에 맞춰 수정하였습니다.
추가된 `nickname`을 포함하여 JWT 토큰을 인증/인가하여 서비스가 동작할 수 있도록 해주었습니다.

- JwtFilter

  ![image](https://github.com/user-attachments/assets/ce9ed046-dfc5-4bfe-b431-c50a3de5fc7f)
- AuthUserArgumentResolver

  ![image](https://github.com/user-attachments/assets/e644ac77-49e2-4217-95f6-b4ea7e7bff55)


</br></br>
## [Level 1-3] 3. 코드 개선 퀴즈 - AOP의 이해
AOP가 적용된 포인트 컷이 잘못되어있고, 적용되는 시점의 수정이 필요한 수정이었습니다.
AOP가 기획자의 의도에 맞게 `UserAdminController` 클래스의 `changeUserRole()` 메서드가 실행되기 전에 동작할 수있도록 다음과 같이 수정해 주었습니다.
![image](https://github.com/user-attachments/assets/ba6e1a31-1ebf-496a-8e83-1f62e95f00ba)


</br></br>
## [Level 1-4] 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해
컨트롤러 테스트를 하는 과정에서 `todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다()` 테스트가 실패하였습니다.
이유를 살펴보니, 해당 테스트 메서드는 todo가 존재하지 않아서 `InvalidRequestException`의 예외가 잘 발생하는지를 체크해야하였습니다. 하지만 해당 테스트 응답이 잘 발생하는지 (200 코드가 잘 발생하는지)를 확인하고 있어서 테스트 검증 코드를 다음과 같이 수정하였습니다.

![image](https://github.com/user-attachments/assets/4b147f10-8de5-430d-9993-8c95a024ec94)


</br></br>
## [Level 1-5] 5. 코드 개선 퀴즈 -  JPA의 이해
기획자가 할 일을 검색 시 `weather`, 수정일 기준으로 기간 검색 기능을 추가해 달라는 수정을 요구하였습니다. 
`weather`와, 기간 검색을 위해 `searchStartDate`, `searchEndDate` 파람을 추가로 받아 서비스 로직 및 DB 검색 쿼리문을 다음과 같이 수정하였습니다.
- TodoController
  
  ![image](https://github.com/user-attachments/assets/fc040c0a-3841-4bab-9441-100201060182)
- TodoService
  
  ![image](https://github.com/user-attachments/assets/47c93d8e-d2a6-480f-9214-dd9e82a021f0)
- TodoRepository
  
  ![image](https://github.com/user-attachments/assets/e861d197-8efb-427d-b567-843fa77c28f0)



</br></br>
## [Level 2-6] 6. JPA Cascade


</br></br>
## [Level 2-7] 7. N+1


</br></br>
## [Level 2-8] 8. QueryDSL


</br></br>
## [Level 2-9] 9. Spring  Security
