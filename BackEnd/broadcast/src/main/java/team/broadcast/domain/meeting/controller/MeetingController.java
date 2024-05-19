package team.broadcast.domain.meeting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.meeting.dto.MeetingCreateRequest;
import team.broadcast.domain.meeting.dto.MeetingDTO;
import team.broadcast.domain.meeting.dto.MeetingUpdateRequest;
import team.broadcast.domain.meeting.exception.MeetingErrorCode;
import team.broadcast.domain.meeting.service.MeetingService;
import team.broadcast.domain.user.dto.InviteUser;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.jwt.exception.JwtErrorCode;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.global.login.user.CustomUserDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting/")
@Tag(name = "회의 API")
public class MeetingController {

    private final MeetingService meetingService;
    private final JwtService jwtService;

    // 현재 참가되어 있는 회의 모두 조회
    @GetMapping
    @Operation(
            summary = "참가 되어 있는 회의 모두조회",
            description = "모든 회의 조회 API")
    public List<MeetingDTO> getMeetings(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return meetingService.findAllMeetings(userDetails.getId());
    }

    // 특정 회의 조회
    @GetMapping("/{meetingId}")
    @Operation(
            summary = "회의 조회",
            description = "특정 회의 조회 API"
    )
    public MeetingDTO getMeeting(@PathVariable Long meetingId) {
        return meetingService.findMeetingById(meetingId);
    }

    // 회의 생성
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회의 생성", description = "회의 생성 API")
    public MeetingDTO createMeeting(@AuthenticationPrincipal CustomUserDetails customUser, @RequestBody MeetingCreateRequest request) {
        return meetingService.createMeeting(customUser.getUser(), request);
    }

    //회의 수정
    @PutMapping("/{meetingId}/update")
    @Operation(summary = "회의 수정", description = "회의 수정 API")
    public MeetingDTO updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingUpdateRequest request, @AuthenticationPrincipal CustomUserDetails customUser) {
        boolean checked = meetingService.checkHostInMeeting(customUser.getId(), meetingId);
        if (!checked) {
            throw new CustomException(MeetingErrorCode.ALLOW_HOST_ROLE);
        }

        // 호스트인 경우 업데이트 실행
        return meetingService.updateMeeting(meetingId, request);
    }

    // 회의 삭제
    @DeleteMapping("/{meetingId}/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회의 삭제", description = "회의 삭제 API")
    public void deleteMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal CustomUserDetails customUser) {
        boolean checked = meetingService.checkHostInMeeting(customUser.getId(), meetingId);

        // 호스트가 아닌 경우 권한 오류를 부여 한다.
        if (!checked) {
            throw new CustomException(MeetingErrorCode.ALLOW_HOST_ROLE);
        }

        // 호스트인 경우에만 삭제 실행
        meetingService.deleteMeeting(meetingId);
    }

    // 회의에서 사용자 탈퇴
    @DeleteMapping("/{meetingId}/exit")
    @ResponseStatus(HttpStatus.OK)
    public void exitMeeting(@PathVariable Long meetingId,
                            @AuthenticationPrincipal CustomUserDetails customUser) {
        meetingService.exitAttender(meetingId, customUser.getUser());
    }

    @GetMapping("/{meetingId}/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinMeeting(@PathVariable Long meetingId, @RequestParam String token) {
        String email = jwtService.extractEmail(token)
                .orElseThrow(() -> new CustomException(JwtErrorCode.NOT_FOUND_ACCESS));

        // TODO: 참석자 추가 코드 수정 필요.
//        meetingService.addAttender(meetingId, email);
    }

    @PostMapping("/{meetingId}/invite")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "참석자 초대 링크 발송",
            description = "이미 회원인 사용자에게 호스트가 회의 초대 링크 이메일을 보낸다.")
    public String sendEmail(@PathVariable Long meetingId,
                            @RequestBody InviteUser inviteUser,
                            @AuthenticationPrincipal CustomUserDetails customUser) {

        // 보내는 사람이 호스트인지 검사하는 코드
        boolean checked = meetingService.checkHostInMeeting(customUser.getId(), meetingId);

        if (!checked) {
            throw new CustomException(MeetingErrorCode.ALLOW_HOST_ROLE);
        }
        String token = jwtService.generateAccessToken(inviteUser.getEmail());
        // 빋는 시용자에게 메일을 보낸다.
        meetingService.sendEmail(meetingId, inviteUser.getEmail(), token);
        return "{ \"message\" : \"success\" }";
    }
}
