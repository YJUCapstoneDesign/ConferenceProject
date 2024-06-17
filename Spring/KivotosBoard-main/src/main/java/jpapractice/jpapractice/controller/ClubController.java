package jpapractice.jpapractice.controller;

import jpapractice.jpapractice.customException.DataNotFoundException;
import jpapractice.jpapractice.domain.Club;
import jpapractice.jpapractice.dto.board.ClubDto;
import jpapractice.jpapractice.service.ClubService;
import jpapractice.jpapractice.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;


    @GetMapping
    public String getClubs(Model model) {
        List<Club> clubs = clubService.getAllClubs();
        model.addAttribute("clubs", clubs);
        return "club/list";
    }

    @PostMapping("/add")
    public String addClub(@ModelAttribute ClubDto clubDto,
                          Principal principal,
                          @RequestParam("image") MultipartFile image) throws IOException {
        clubService.saveClub(clubDto, image, principal.getName());
        return "redirect:/";
    }

    @PostMapping("/join/{clubId}")
    public String joinClub(@PathVariable Long clubId, Principal principal) {
        try {
            clubService.joinClub(principal.getName(), clubId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{clubId}")
    public String deleteClub(@PathVariable Long clubId, Principal principal) {
        try {
            String accountId = principal.getName();
            clubService.deleteClub(accountId, clubId);
        } catch (DataNotFoundException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/update/{clubId}")
    public String updateClubForm(@PathVariable Long clubId, Model model) {
        Club club = clubService.getClubById(clubId);
        model.addAttribute("club", club);
        return "club/update";
    }

    @PostMapping("/update/{clubId}")
    public String updateClub(@PathVariable Long clubId,
                             @ModelAttribute ClubDto clubDto,
                             @RequestParam(value = "image", required = false)
                                 MultipartFile image) throws IOException {
        clubService.updateClub(clubId, clubDto, image);
        return "redirect:/";
    }
}
