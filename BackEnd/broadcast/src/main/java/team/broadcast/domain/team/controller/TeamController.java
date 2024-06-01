package team.broadcast.domain.team.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.team.dto.TeamRequest;
import team.broadcast.domain.team.dto.TeamUpdateRequest;
import team.broadcast.domain.team.service.TeamService;
import team.broadcast.global.login.user.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
@Tag(name = "팀 API")
public class TeamController {
    private final TeamService teamService;

    // 팀 생성
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createTeam(@RequestBody TeamRequest request,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return teamService.createTeam(request, userDetails.getUser());
    }

    // 방 입장시 비밀 번호 확인 코드 작성
    // 여기에 팀에 없는 경우 팀에 추가한다.
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long joinTeam(@RequestBody TeamRequest joinRequest,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return teamService.joinTeam(joinRequest, userDetails.getUser());
    }

    // 비밀번호 업데이트
    @PutMapping("/update/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable Long teamId,
                               @RequestBody TeamUpdateRequest updateRequest) {
        teamService.updatePassword(teamId, updateRequest.getOldPwd(), updateRequest.getNewPwd());
    }

    // 버튼 입력시 팀 인원 리스트 불러오기
    // 이 때 팀 닉네임 리스트를 불러온다.
    @GetMapping("/user-list/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getUserList(@PathVariable Long teamId) {
        return teamService.getUsersNickname(teamId);
    }

    // 팀 아이디로 삭제
    @DeleteMapping("/delete/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable Long teamId, @RequestBody String password) {
        teamService.deleteTeam(teamId, password);
    }
}
