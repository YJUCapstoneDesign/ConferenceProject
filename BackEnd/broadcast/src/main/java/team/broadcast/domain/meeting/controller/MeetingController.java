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
import team.broadcast.domain.meeting.service.MeetingService;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.domain.video_room.service.InvitationService;
import team.broadcast.global.login.user.CustomUserDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting/")
@Tag(name = "회의 API")
public class MeetingController {

    private final MeetingService meetingService;
    private final InvitationService invitationService;
    private final UserService userService;

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
    public MeetingDTO createMeeting(@RequestBody MeetingCreateRequest request) {
        return meetingService.createMeeting(request);
    }

    //회의 수정
    @PutMapping("/{meetingId}/update")
    @Operation(summary = "회의 수정", description = "회의 수정 API")
    public MeetingDTO updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingUpdateRequest request) {
        return meetingService.updateMeeting(meetingId, request);
    }

    // 회의 삭제
    @DeleteMapping("/{meetingId}/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회의 삭제", description = "회의 삭제 API")
    public void deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
    }
    
    // 회의 참석자 추가
//    @PostMapping("/{meetingId}/add-attender")
//    @ResponseStatus(HttpStatus.OK)
//    public void sendMailFromAttender(@PathVariable Long meetingId, Long userId) {
//        User user = userService.findUser(userId);
//        Meeting meeting = meetingService.findMeetingById(meetingId);
//        invitationService.sendInviteMail(user, );
//    }
}
