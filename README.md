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
할 일을 새로 저장할 시, 할 일을 생성한 유저를 담당자로 자동 등록되도록 코드를 수정해야하였습니다. JPA의 `cascade` 기능을 이용하여 `Todo` 엔티티에서 자동으로 담당자가 등록될 수 있도록 다음과 같이 코드를 수정하였습니다.

![image](https://github.com/user-attachments/assets/cd319d76-6008-4007-9b37-a4c3db213e4e)



</br></br>
## [Level 2-7] 7. N+1
`CommentController` 클래스의 `getComments()` API를 호출할 때 N+1 문제가 발생하고 있습니다. N+1 문제란, 데이터베이스 쿼리 성능 저하를 일으키는 대표적인 문제 중 하나로, 특히 연관된 엔티티를 조회할 때 발생합니다.
해당 문제가 발생하지 않도록 다음과 같이 코드를 수정하였습니다.

![image](https://github.com/user-attachments/assets/7e532cf5-a1c9-4c5b-92ea-5e6a6c92127b)



</br></br>
## [Level 2-8] 8. QueryDSL
JPQL로 작성된 `findByIdWithUser` 를 QueryDSL로 리팩토링 해달라는 요구가 들어왔습니다. 해당 검색쿼리를 QueryDSL로 작성하면서 N+1 문제가 발생하지 않도록 `FEATCH JOIN`을 사용하여 다음과 같이 리팩토링 하였습니다.

![image](https://github.com/user-attachments/assets/fed66bee-2846-429c-9c9f-c8f4b7402765)


</br></br>
## [Level 2-9] 9. Spring  Security
Spirng Security 방식을 도입하기로 하여 코드를 리팩토링 하여야 했습니다.
기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 Spring Security 방식을 사용한 `JwtSecurityFilter`와 `JwtAuthenticationToken`으로 수정해 주었습니다.
접근 권한 및 유저 권한 기능은 그대로 유지하면서, 권한은 Spring Security의 기능을 사용하는 방식으로 코드를 리팩토링 하였습니다. (토큰 기반 인증 방식은 유지하여 JWT는 그대로 사용하였습니다.)
Spring Security 방식으로 리팩토링하여 기존의 사용자 정보를 받아오던 커스텀 어노테이션 `@Auth` 가 아닌 `@AuthenticationPrincipal` 어노테이션을 통해 인증된 사용자 정보를 서비스에서 이용할 수 있도록 리팩토링 되었습니다.

![image](https://github.com/user-attachments/assets/93dc44da-f605-4d8f-8211-67700c1a31c5)





