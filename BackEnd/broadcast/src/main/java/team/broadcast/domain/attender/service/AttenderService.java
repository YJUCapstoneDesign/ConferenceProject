package team.broadcast.domain.attender.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.attender.exception.AttenderErrorCode;
import team.broadcast.domain.attender.mysql.repository.AttenderRepository;
import team.broadcast.domain.enumstore.enums.MeetingRole;
import team.broadcast.domain.meeting.entity.Meeting;
import team.broadcast.domain.meeting.exception.MeetingErrorCode;
import team.broadcast.domain.meeting.mysql.repository.MeetingRepository;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.mysql.repository.UserRepository;
import team.broadcast.global.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttenderService {
    private final AttenderRepository attenderRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;


    // 참석자 추가하기
    @Transactional
    public Long addAttender(AttenderDTO dto) {
        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Attender attender = Attender.builder()
                .user(user)
                .meeting(meeting)
                .role(MeetingRole.PARTICIPANT)
                .build();

        return attenderRepository.save(attender).getId();
    }

    // 참석자 권한 수정
    @Transactional
    public Long updateAttenderRole(Long id, MeetingRole role) {
        Attender attender = attenderRepository.findById(id)
                .orElseThrow(() -> new CustomException(AttenderErrorCode.ATTENDER_NOT_FOUND));

        attender.updateRole(role);

        return attenderRepository.save(attender).getId();
    }

    // 사용자 삭제
    @Transactional
    public void removeAttender(Long attenderId) {
        Attender attender = attenderRepository.findById(attenderId)
                .orElseThrow(() -> new CustomException(AttenderErrorCode.ATTENDER_NOT_FOUND));

        attenderRepository.delete(attender);
    }

    public AttenderDTO getAttender(Long attenderId) {
        Attender attender = attenderRepository.findById(attenderId)
                .orElseThrow(() -> new CustomException(AttenderErrorCode.ATTENDER_NOT_FOUND));

        return AttenderDTO.toDTO(attender);
    }

    public Optional<Attender> findAttenderByUserIdAndMeetingId(Long userId, Long meetingId) {
        return attenderRepository.findByUserIdAndMeetingId(userId, meetingId);

    }

    public List<AttenderDTO> toDTOList(List<Attender> attenders) {
        return attenders.stream()
                .map(AttenderDTO::toDTO).toList();
    }

}
