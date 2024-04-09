package team.broadcast.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.attender.mysql.repository.AttenderRepository;
import team.broadcast.domain.meeting.dto.MeetingCreateRequest;
import team.broadcast.domain.meeting.dto.MeetingDTO;
import team.broadcast.domain.meeting.dto.MeetingUpdateRequest;
import team.broadcast.domain.meeting.entity.Meeting;
import team.broadcast.domain.meeting.mysql.repository.MeetingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final AttenderRepository attenderRepository;

    // 회의 추가
    @Transactional
    public MeetingDTO createMeeting(MeetingCreateRequest meetingCreateRequest) {
        Meeting meeting = meetingCreateRequest.from();

        Meeting saved = meetingRepository.save(meeting);

        return MeetingDTO.builder()
                .name(saved.getName())
                .createTime(saved.getCreateTime())
                .endTime(saved.getEndTime())
                .build();
    }

    // 회의 수정
    @Transactional
    public MeetingDTO updateMeeting(Long id, MeetingUpdateRequest meetingUpdateRequest) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found meeting"));

        meeting.updateName(meetingUpdateRequest.getName());
        meeting.updateStartTime(meetingUpdateRequest.getStartTime());
        meeting.updateEndTime(meetingUpdateRequest.getEndTime());

        meetingRepository.save(meeting);

        return MeetingDTO.builder()
                .name(meeting.getName())
                .createTime(meeting.getCreateTime())
                .endTime(meeting.getEndTime())
                .build();

    }

    public Optional<Meeting> getMeetingById(Long id) {
        return meetingRepository.findById(id);
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    // 회의 삭제
    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Not found meeting"));

        meetingRepository.delete(meeting);
    }

    // 회의에 있는 참여자 가져오기
    @Transactional
    public List<AttenderDTO> findAttenders(Long meetingId) {
        List<Attender> attenders = attenderRepository.findByMeetingId(meetingId);

        return attenders.stream()
                .map(AttenderDTO::from)
                .collect(Collectors.toList());
    }

}
