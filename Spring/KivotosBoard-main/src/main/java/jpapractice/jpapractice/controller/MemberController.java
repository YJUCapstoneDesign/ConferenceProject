package jpapractice.jpapractice.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import jpapractice.jpapractice.dto.member.DefaultInfoDto;
import jpapractice.jpapractice.dto.member.PasswordDto;
import jpapractice.jpapractice.dto.member.StudentAndAccountDto;
import jpapractice.jpapractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member") // 메서드들에 매핑된 URL 앞에 /memeber path를 추가함
public class MemberController {

  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public MemberController(MemberService memberService,
      PasswordEncoder passwordEncoder) {
    this.memberService = memberService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/join")
  public String join(StudentAndAccountDto studentAndAccountDto) {
    return "member/joinPage";
  }

  @PostMapping("/join")
  public String join(@Valid StudentAndAccountDto studentAndAccountDto,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("studentAndAccountDTO", studentAndAccountDto);
      return "member/joinPage";
    }
    if (!studentAndAccountDto.getAccountPasswd().equals(
        studentAndAccountDto.getAccountPasswdCheck())) {
      bindingResult.rejectValue("accountPasswdCheck", "passwdIncorrect",
          "비밀번호가 일치하지 않습니다");
      return "member/joinPage";
    }
    String account = memberService.registMember(studentAndAccountDto);

    return "redirect:/login";
    // return "redirect:/mypage"; -> get 방식으로 파라미터가 넘어가게 된다
    // return "redirect:/member/mypage/{account}"; // -> path parameter 방법으로 데이터를 넘김

  }

  @GetMapping("/mypage/{account}") // path parameter을 받을 때의 방법
  public String myPage(@PathVariable("account") String account,
      Model model) {
    DefaultInfoDto result = memberService.findInfo(account);
    model.addAttribute("info", result);
    return "member/mypage";
  }
  // @PathVariable(parameter이름) 으로 값을 가져올수 있다.

  @GetMapping("mypage/{accountId}/modify")
  public String modifyInfo(
      @PathVariable("accountId") String accountId, Model model,
      Principal principal) {
    if (!accountId.equals(principal.getName())) {
      return "redirect:/";
    }
    StudentAndAccountDto studentAndAccountDto = memberService.getUserInfoForModify(
        accountId);
    model.addAttribute("studentAndAccountDto", studentAndAccountDto);
    return "member/modifyInfo";
  }

  @PostMapping("/mypage/{accountId}/modify")
  public String modifyInfo(@PathVariable("accountId") String accountId,
      @Valid StudentAndAccountDto studentAndAccountDto,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes, Principal principal) {
    if (bindingResult.hasErrors()) {
      return "member/modifyInfo";
    }
    if (!accountId.equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "수정 권한이 없습니다.");
    }
    memberService.modifyInfo(studentAndAccountDto, accountId);
    redirectAttributes.addAttribute("accountId", accountId);

    return "redirect:/member/mypage/{accountId}";
  }

  @GetMapping("/mypage/{accountId}/unregist")
  public String unregist(Model model) {
    System.out.println("테스트");
    model.addAttribute("passwordDto", new PasswordDto());
    return "member/unregist";
  }

  @PostMapping("/mypage/{accountId}/unregist")
  public String unregist(@Valid PasswordDto passwordDto,
      BindingResult bindingResult,
      @PathVariable("accountId") String accountId, Principal principal) {
    if (bindingResult.hasErrors()) {
//      return String.format("redirect:unregist",
//          accountId);
      System.out.println("haserror");
      return "member/unregist";
    }
    if (!passwordDto.getPassword()
                    .equals(passwordDto.getPasswordCheck())
        || !passwordEncoder.matches(passwordDto.getPassword(),
        memberService.getPassword(accountId))) {
      bindingResult.rejectValue("passwordCheck", "passwdIncorrect",
          "비밀번호가 일치하지 않습니다");
//      return String.format("redirect:unregist",
//          accountId); -> RedirectAttribute 사용하지 않고도 Redirect 가능
      System.out.println("incoreect password");
      return "member/unregist";
    }
    if (!accountId.equals(principal.getName())) {
      System.out.println("incorrect account");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "사용자가 일치하지 않습니다");
    }
    System.out.println("delete account");
    memberService.unregistMember(accountId);
    return "redirect:/logout";
  }

}
