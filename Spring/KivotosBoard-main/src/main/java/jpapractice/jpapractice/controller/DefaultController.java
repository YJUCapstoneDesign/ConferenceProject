package jpapractice.jpapractice.controller;

import jpapractice.jpapractice.domain.Club;
import jpapractice.jpapractice.service.ClubService;
import jpapractice.jpapractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DefaultController {

  private final MemberService memberService;
  private final ClubService clubService;

  @Autowired
  public DefaultController(MemberService memberService, ClubService clubService) {
    this.memberService = memberService;
    this.clubService = clubService;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<Club> clubs = clubService.getAllClubs();
    model.addAttribute("clubs", clubs);
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "member/loginPage";
  }
}
