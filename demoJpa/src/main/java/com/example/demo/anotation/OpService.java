package com.example.demo.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(java.lang.annotation.ElementType.METHOD)   /*method 일거라고 선언 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)   /*
	CLASS 일때도 동작안하고
	SOURCE 일때도 동작안하고
	RUNTIME 일때만 동작한다.
   와 이거 없으면   reflection에서 불러오지를 못하네

*/
public @interface OpService {
	/*https://medium.com/@ggikko/java-%EC%BB%A4%EC%8A%A4%ED%85%80-annotation-436253f395ad
	 * 참고 
	 * */

}
