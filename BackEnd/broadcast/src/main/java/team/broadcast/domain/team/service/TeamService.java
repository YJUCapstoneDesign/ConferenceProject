package team.broadcast.domain.team.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enumstore.enums.TeamRole;
import team.broadcast.domain.team.dto.TeamRequest;
import team.broadcast.domain.team.entity.Team;
import team.broadcast.domain.team.exception.TeamErrorCode;
import team.broadcast.domain.team.repository.TeamRepository;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.global.exception.CustomException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 팀 생성 팀을 생성한 사람은 호스트가 된다.
    @Transactional
    public Long createTeam(TeamRequest request, User user) {

        Team team = Team.builder()
                .id(request.getTeamId())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .userList(new ArrayList<>(Collections.singletonList(user)))
                .role(TeamRole.HOST)
                .build();

        ArrayList<Team> teams = new ArrayList<>(Collections.singletonList(team));
        user.updateTeams(teams);
        teamRepository.save(team);
        userRepository.save(user);

        return team.getId();
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new CustomException(TeamErrorCode.TEAM_NOT_FOUND));
    }

    // 팀 입장시 비밀번호가 맞는지 확인한다.
    public boolean confirmTeamPwd(Team team, String password) {
        return passwordEncoder.matches(password, team.getPassword());
    }

    // 입장 처리 로직
    public Long joinTeam(TeamRequest request, User user) {
        Team team = getTeam(request.getTeamId());

        boolean checkPwd = confirmTeamPwd(team, request.getPassword());

        if (!checkPwd) {
            throw new CustomException(TeamErrorCode.INVALID_PASSWORD);
        }

        boolean joined = isJoined(team, user);

        // 팀에 없는 경우 팀에 추가시킨다.
        if (!joined) {
            team.addUser(user);
            teamRepository.save(team);
            user.updateTeams(Collections.singletonList(team));
            userRepository.save(user);
        }

        return team.getId();
    }

    // 팀 비밀번호 수정 코드
    @Transactional
    public void updatePassword(Long teamId, String password, String newPassword) {

        Team team = getTeam(teamId);
        boolean checkPwd = confirmTeamPwd(team, passwordEncoder.encode(password));

        if (!checkPwd) {
            throw new CustomException(TeamErrorCode.INVALID_PASSWORD);
        }

        team.updatePassword(newPassword);
        teamRepository.save(team);
    }

    @Transactional
    public void deleteTeam(Long teamId, String password) {

        Team team = getTeam(teamId);

        boolean checkPwd = confirmTeamPwd(team, passwordEncoder.encode(password));

        if (!checkPwd) {
            throw new CustomException(TeamErrorCode.INVALID_PASSWORD);
        }

        // 팀 테이블 삭제하기 전에 CrazyEight 삭제를 해야 한다.
        // 추후 추가 예정..
        teamRepository.deleteById(teamId);
    }

    // 현재 팀에 소속이 되어있는지 검사하는 코드
    public boolean isJoined(Team team, User user) {
        return team.getUserList().contains(user);
    }

    // 현재 참석된 참석자 리스트 조회하기 (참석자 리스트 보내기)
    public List<String> getUsersName(Long teamId) {
        Team team = getTeam(teamId);

        return team.getUserList()
                .stream()
                .map(User::getName)
                .toList();
    }
}
