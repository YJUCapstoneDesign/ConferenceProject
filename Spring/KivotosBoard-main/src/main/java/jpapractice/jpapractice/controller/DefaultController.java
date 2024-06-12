package jpapractice.jpapractice.controller;

import jpapractice.jpapractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

  private final MemberService memberService;

  @Autowired
  public DefaultController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/login")
  public String login() {
    return "member/loginPage";
  }
  // Post Mapping은 Spring Security가 대신 처리해준다
  // @PostMapping("/login")
  // public String login(@Valid LoginDto loginDto, BindingResult bindingResult,
  // RedirectAttributes redirectAttributes,
  // HttpServletRequest request, Model model) {

  // if (bindingResult.hasErrors()) {
  // model.addAttribute("loginDto", loginDto);
  // return "member/loginPage";
  // }
  // request.getSession().invalidate();
  // HttpSession session = request.getSession(true);
  // session.setAttribute("account", loginDto.getId());
  // redirectAttributes.addAttribute("account", loginDto.getId());

  // return "redirect:/member/mypage/{account}";
  // }

  // @GetMapping("logout")
  // public String logout()

}
