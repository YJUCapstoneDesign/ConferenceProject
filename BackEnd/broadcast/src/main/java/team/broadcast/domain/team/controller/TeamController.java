package team.broadcast.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.team.dto.TeamRequest;
import team.broadcast.domain.team.dto.TeamUpdateRequest;
import team.broadcast.domain.team.service.TeamService;
import team.broadcast.global.login.user.CustomUserDetails;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    // 팀 생성
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createTeam(@RequestBody TeamRequest request,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return teamService.createTeam(request, userDetails.getUser());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long joinTeam(@RequestBody TeamRequest joinRequest) {
        return teamService.searchTeam(joinRequest);
    }

    // 비밀번호 업데이트
    @PutMapping("/update/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable Long teamId,
                               @RequestBody TeamUpdateRequest updateRequest) {
        teamService.updatePassword(teamId, updateRequest.getOldPwd(), updateRequest.getNewPwd());
    }

    // 팀 아이디로 삭제
    @DeleteMapping("/delete/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable Long teamId, @RequestBody String password) {
        teamService.deleteTeam(teamId, password);
    }
}
