package team.broadcast.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 테스트용 컨트롤러
 */
@RestController
public class TestController {

  @GetMapping("test")
  public String test() {
    return "ok";
  }
}
