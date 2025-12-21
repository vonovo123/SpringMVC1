# Spring Web MVC
## Web Server, Web Application Server
### WEB
#### HTTP 메시지를 통해 모든 것을 전송

- HTML, TEXT
- IMAGE, VIDEO, FILE, MUSIC
- JSON, XML

### Web Server
- HTTP 기반으로 동작
- 정적 리소스 제공 + 기타 부가기능
- STATIC HTML, CSS, JS, IMAGE, VIDEO 등
- EX) NGINX, APACHE

### WEB APPLICATION SERVER
- HTTP 기반으로 동작
- WEB SERVER 기능 포함
- 프로그램 코드를 실행해 앱로직 수행
  - 동적 HTML, HTTP API
  - SERVLET, JSP, SPRING MVC
- EX) TOMCAT, UNDERTOW

### Web Server 와 WAS의 차이
- 웹 서비는 정적 리소스 제공, WAS는 에플리케이션 로직 제공
- 하지만 상호 동일한 기능을 제공할 수 있음
- JAVA는 서블릿 컨테이너 기능을 제공하면 WAS
- WAS는 애플리케이션 코드를 실행하는데 더 특화됨

### 웹 시스템 구성 - WEB, WAS, DB

WAS와 DB 만으로 시스템 구성이 가능하다. WAS는 정적 리스소와 애플리케이션 로직이 모두 제공 가능하기 때문이다.
하지만 WAS가 너무 많은 역할을 담당하는 경우, 과부하 우려가 있다.
애플리케이션 로직이 정적 리소스로 인해 수행이 어려울 수 있다.
이로인해, 정적 리소스는 WEB SERVER가 처리하고 동적인 처리는 WAS에 위임하는 구조로 설계한다.
이를 통해 효율적인 리소스 관리가 가능하다.
정적 리소스가 많아지면 WEB SERVER를 증설하고, APP Logic이 많아지면 WAS를 증설하면 된다.
WEB SERVER는 잘 죽지 않지만 WAS는 잘 죽는다. 이때 WEB 서버가 클라이언트로 오류 화면을 제공 가능하다.

## SERVLET

클라이언트는 POST 방식으로 서버에 HTML FORM 데이터를 전송할 수 있다.
```
POST /save HTTP/1.1
Host: localhost:8080
Content-Type : application/x-www-form-urlencoded
username=kim&age=20
```

서버는 HTML FORM 데이터를 데이터베이스에 저장 요청하는 비즈니스 로직을 실행한다.
이후 클라이언트로 전송할 HTTP응답 메시지를 생성한다.

```
HTTP/1.1 200 OK
CONTENT-TYPE:text/html;charset=UTF-8
Content-length:3423
<html>
    <body>...</body>
</html>
```

비즈니스 로직을 실행하기 위해 서버는 Servlet 기술을 활용할 수 있다.

```java
@WebServlet(name="helloServlet",urlPatterns="/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response){
        // Application Logic
    }
}
```

- urlPatterns의 URL이 호출되면 서블릿 코드가 실행된다.

### HTTP 요청, 응답 흐름

- HTTP 요청시
  - WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체를 호출한다.
  - HttpServletRequest 객체를 통해 요청정보를 사용하고 HttpServletResponse 객체를 통해 응답 정보를 제공한다.
  - WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성한다.

### 서블릿 컨테이너

TOMCAT처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 한다.

- 서블릿 컨테이너는 서블릿 객체를 생성,초기화, 호출, 종료하는 생명주기를 관리한다.
- 서블릿 객체는 싱글톤으로 관리된다.
  - 클라이언트 요청이 올 때 마다 서블릿 객체를 생성하는 것은 비효율적이다.
  - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용한다.
  - 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근한다.
  - 이로 인해 공유 변수를 사용해서는 안된다.
  - 서블릿 컨테이너 종료시 서블릿 객체의 생명주기도 함께 종료된다.
- JSP 역시 서블릿으로 변환 되어 상요된다.
- 동시 요청을 위한 멀티 쓰레드 처리 또한 지원된다.


## 동시 요청 - 멀티 쓰레드

### 쓰레드

WAS의 Servlet 컨테이너에서 서블릿 객체를 호출하는 역할은 쓰레드가 담당한다.

- 쓰레드는 애플리케이션 코드를 순차적으로 실행한다.
- 자바 메인 메서드를 처음 실행하면 main 쓰레드가 실행된다.
- 쓰레드가 없다면 자바 애플리케이션 실행이 불가능하다.
- 쓰레드는 한번에 하나의 코드 라인만 수행한다.
- 동시 처리가 필요하다면 쓰레드를 추가로 생성한다.

WAS는 Servlet 객체 호출에 필요한 쓰레드를 생성해 연결하는 역할을 한다.
WAS는 클라이언트 요청 마다 새로운 쓰레드를 생성하는데 장단점은 다음과 같다.

- 장점
  - 동시 요청을 처리할 수 있다.
  - 시스템 리소스가 허용하는한 요청 처리가 가능한다.
  - 하나의 쓰레드의 처리가 지연되어도 나머지 쓰레드는 정상동작한다.
- 단점
  - 쓰레드 생성은 고비용이다.
  - 쓰레드는 컨텍스트 스위칭 비용이 발생한다.
  - 생성에 제한이 없기때문에 요청이 많이 발생하여 시스템 리소스 임계점을 넘으면 서버가 죽는다.

쓰레드의 단점을 보완하기 위해 쓰레드 풀을 활용한다.
쓰레드 풀을 활용해 요청마다 쓰레드를 생성해야하는 단점을 보완한다.

- 특징
  - 필요할 것으로 예상되는 쓰레드를 미리 생성해 풀에 보관하고 관리한다.
  - 풀에 생성가능한 최대치를 관리한다. 톰캣의 경우 최대 200개까지 기본설정이 가능하다.
- 사용
  - 쓰레드가 필요하면, 이미 생성된 쓰레드를 풀에서 꺼내서 사용한다.
  - 사용을 종료하면 풀에 다시 반납한다.
  - 최대 쓰레드가 모두 사용중이아서 풀에 쓰레드가 없다면 대기하거나 요청을 거절하도록 설정할 수 있다.
- 장점
  - 쓰레드를 생성하고 종료하는 비용이 절약되고, 응답시간이 빠르다.
  - 생성 가능한 쓰레드의 최대치가 있으므로 요청이 몰려도 기존 요청은 안전하게 처리할 수 있다.

### 실무 TIP

WAS의 주요 튜닝 포인트는 최대 쓰레드 수 설정이다.

최대 쓰레드 설정이 너무 낮으면 서버 리소스는 여유롭지만, 클라이언트의 응답이 지연된다.
반대로 너무 높으면 클라이언트 요청은 최대로 처리할 수있지만 시스템 리소스 소진으로 서버 다운의 위험이 커진다.

### WAS의 멀티 쓰레드 지원

- 멀티 쓰레드의 대부분은 WAS가 처리한다.
- 개발자는 마치 싱글 쓰레드로 프로그래밍 하듯 코딩하면된다.
- 단, 멀티 쓰레드 환경이므로 싱글톤 객체는 주의해서 사용해야한다.


## HTML, HTML API, CSR, SSR

### 정적 리소스

- STATIC HTML FILE, CSS, JS, IMAGE, MOVIE ETC
- 주로 웹 브라우저에서 사용

### HTML PAGE

- 동적으로 필요한 HTML 파일을 생성해서 전달.
- 웹 브라우저는 이를 해석해 HTML PAGE에 반영한다.

### HTTP API
- WAS HTML이 아니라 데이터만 전달한다.
- 주로 JSON 형식을 띈다.
- 웹 브라우저 뿐아니라 다양한 시스템이서 호출해 처리할 수 있다.
- 데이터만 주고 받기 때문에 UI 화면이 필요하면, 클라이언트가 별도로 처리한다.
- EX) APP, WEB, SERVET TO SERVET

### 서버사이드 렌더링, 클라이언트 사이드 렌더링

- SSR : 서버 사이드 렌더링
  - HTML 최종 결과를 서버에서 만들어 웹 브라우저에 전달한다.
  - 주로 정적인 화면에 사용한다.
  - EX) JSP, THYMLEAF
- CSR : 클라이언트 사이드 렌더링
  - HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성한다.
  - 주로 동적인 화면에 사용, 웹 환경을 마치 앱처럼 필요한 부분부분 변경할 수 있다.
- REDUX, VUEX 처럼 CSR과 SSR을 동시에 지원하는 웹 프레임워크도 있다.

## 자바 웹 기술 역사

### 현재 사용 기술

- 애노테이션 기반의 스프링 MVC
  - @Controller`
- 스프링 부트의 등장
  - 스프링 부트는 서버를 내장하고 있음.
  - 과거에는 서버에 WAS를 직접 설치하고, 소스는 WAR파일을 만들어 WAS에 배포
  - 스프링 부트는 자바에플리에키션의 빌드 결과(Jar)에 WAS 서버를 포함

## 자바 뷰 템플릿의 역사

- JSP
- 프리마커
- 타임리프
  - 내추럴 템플릿 : HTML 의 형태를 유지하며 뷰 템플릿 적용 가능
  - 스프링 MVC와 강력한 기능 통합
 
## HTTP 요청 개요 

- GET : query parameter
  - /url?username
- POST : HTML FORM
- HTTP Message body
  - HTTP API 에서 주로 사용
  - 데이터 형식은 주로 JSON

