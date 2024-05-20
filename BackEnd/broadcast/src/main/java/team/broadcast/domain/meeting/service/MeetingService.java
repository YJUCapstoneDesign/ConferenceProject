package team.broadcast.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.attender.exception.AttenderErrorCode;
import team.broadcast.domain.attender.mysql.repository.AttenderRepository;
import team.broadcast.domain.enumstore.enums.MeetingRole;
import team.broadcast.domain.meeting.dto.MeetingCreateRequest;
import team.broadcast.domain.meeting.dto.MeetingDTO;
import team.broadcast.domain.meeting.dto.MeetingUpdateRequest;
import team.broadcast.domain.meeting.entity.Meeting;
import team.broadcast.domain.meeting.exception.MeetingErrorCode;
import team.broadcast.domain.meeting.mysql.repository.MeetingRepository;
import team.broadcast.domain.mindmap.service.InvitationService;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.global.exception.CustomException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final AttenderRepository attenderRepository;
    private final InvitationService invitationService; // 초대 코드 발송 서비스
    private final UserService userService;

    public boolean checkHostInMeeting(Long userId, Long meetingId) {
        Attender attender = attenderRepository.findByUserIdAndMeetingId(userId, meetingId)
                .orElseThrow(() -> new CustomException(AttenderErrorCode.ATTENDER_NOT_FOUND));

        return attender.isHost();
    }

    // 회의 추가
    @Transactional
    public MeetingDTO createMeeting(User user, MeetingCreateRequest meetingCreateRequest) {
        Meeting meeting = meetingCreateRequest.from();

        Meeting saved = meetingRepository.save(meeting);

        // 회읠를 생성한 사람은 자동으로 참석자의 호스트가 된다.
        Attender attender = Attender.builder()
                .user(user)
                .meeting(saved)
                .role(MeetingRole.HOST)
                .build();

        attenderRepository.save(attender);

        return MeetingDTO.from(saved);
    }

    // 회의 수정
    @Transactional
    public MeetingDTO updateMeeting(Long meetingId, MeetingUpdateRequest meetingUpdateRequest) {

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND));

        meeting.updateName(meetingUpdateRequest.getName());
        meeting.updateStartTime(meetingUpdateRequest.getStartTime());
        meeting.updateEndTime(meetingUpdateRequest.getEndTime());

        meetingRepository.save(meeting);

        return MeetingDTO.from(meeting);

    }

    public MeetingDTO findMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND));

        return MeetingDTO.from(meeting);
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    // 회의 삭제
    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND));

        meetingRepository.delete(meeting);
    }

    // 회의 참석자 추가
    @Transactional
    public Attender addAttender(Long meetingId, User user) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND));

        Attender existAttender = attenderRepository.findByUserIdAndMeetingId(meeting.getId(), user.getId())
                .orElse(null);

        if (existAttender != null) {
            throw new CustomException(AttenderErrorCode.DUPLICATED_ATTENDER);
        }

        Attender attender = Attender.builder()
                .user(user)
                .meeting(meeting)
                .role(MeetingRole.PARTICIPANT)
                .build();

        return attenderRepository.save(attender);
    }

    //회의 참석자 나감
    @Transactional
    public void exitAttender(Long meetingId, User user) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND));

        Attender attender = attenderRepository.findByUserIdAndMeetingId(meeting.getId(), user.getId())
                .orElseThrow(() -> new CustomException(AttenderErrorCode.ATTENDER_NOT_FOUND));

        attenderRepository.delete(attender);
    }

    // 회의에 있는 참여자 가져오기
    @Transactional
    public List<UserResponse> findAttenders(Long meetingId) {
        List<Attender> attenders = attenderRepository.findByMeetingId(meetingId);

        return attenders.stream()
                .map(attender -> UserResponse.from(attender.getUser()))
                .toList();
    }

    // 호스트 유저가 회원 유저에게 이메일 발송
    public void sendEmail(Long meetingId, String userEmail, String token) {

        // 여기서 사용자가 실제로 존재하는지 검사를 해야 한다.
        User findUser = userService.findUserByEmail(userEmail);

        // 이미 참석자인지 확인을 한다.
        Attender isAttender = attenderRepository.findByUserIdAndMeetingId(findUser.getId(), meetingId)
                .orElse(null);

        log.info("isAttender: {}", isAttender);

        if (isAttender != null) {
            throw new CustomException(AttenderErrorCode.DUPLICATED_ATTENDER);
        }

        String content = "http://localhost:8080/api/meeting/" + meetingId + "/join?token=" + token;

        invitationService.sendInviteMail(findUser, content);
    }

    // 현재 참석되어 있는 모든 회의 불러오기
    @Transactional
    public List<MeetingDTO> findAllMeetings(Long userId) {
        List<Attender> attenders = attenderRepository.findByUserId(userId);

        List<Meeting> meetings = attenders.stream()
                .map(attender -> meetingRepository.findById(attender.getMeeting().getId())
                        .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND)))
                .toList();
        return fromMeetings(meetings);
    }

    private List<MeetingDTO> fromMeetings(List<Meeting> meetingList) {
        return meetingList.stream()
                .map(meeting -> MeetingDTO.builder()
                        .name(meeting.getName())
                        .createTime(meeting.getCreateTime())
                        .endTime(meeting.getEndTime())
                        .build())
                .toList();
    }
}

