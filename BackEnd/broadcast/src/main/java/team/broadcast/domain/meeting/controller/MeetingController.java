package team.broadcast.domain.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.meeting.dto.MeetingCreateRequest;
import team.broadcast.domain.meeting.dto.MeetingDTO;
import team.broadcast.domain.meeting.dto.MeetingUpdateRequest;
import team.broadcast.domain.meeting.service.MeetingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting/")
public class MeetingController {

    private final MeetingService meetingService;

    // 현재 참가되어 있는 회의 모두 조회
    @GetMapping("{userId}")
    public List<MeetingDTO> getMeetings(@PathVariable Long userId) {
        return meetingService.findAllMeetings(userId);
    }

    // 특정 회의 조회
    @GetMapping("/{meetingId}")
    public MeetingDTO getMeeting(@PathVariable Long meetingId) {
        return meetingService.findMeetingById(meetingId);
    }

    // 회의 생성
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingDTO createMeeting(@RequestBody MeetingCreateRequest request) {
        return meetingService.createMeeting(request);
    }

    //회의 수정
    @PostMapping("/{meetingId}/update")
    public MeetingDTO updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingUpdateRequest request) {
        return meetingService.updateMeeting(meetingId, request);
    }

    // 회의 삭제
    @DeleteMapping("/{meetingId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
    }
}
