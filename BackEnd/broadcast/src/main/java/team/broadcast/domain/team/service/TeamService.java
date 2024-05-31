package team.broadcast.domain.team.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enumstore.enums.TeamRole;
import team.broadcast.domain.team.dto.TeamRequest;
import team.broadcast.domain.team.entity.Team;
import team.broadcast.domain.team.exception.TeamErrorCode;
import team.broadcast.domain.team.repository.TeamRepository;
import team.broadcast.domain.user.entity.User;
import team.broadcast.global.exception.CustomException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
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

        return team.getId();
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new CustomException(TeamErrorCode.TEAM_NOT_FOUND));
    }

    // 팀 입장시 비밀번호가 맞는지 확인한다.
    public boolean confirmTeamPwd(Team team, String password) {
        // 데이터베이스에는 비밀번호가 암호화 되어 있음.
        return team.getPassword().equals(passwordEncoder.encode(password));
    }

    public Long searchTeam(TeamRequest request) {
        Team team = getTeam(request.getTeamId());

        boolean checkPwd = confirmTeamPwd(team, request.getPassword());

        if (!checkPwd) {
            throw new CustomException(TeamErrorCode.INVALID_PASSWORD);
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
        password = passwordEncoder.encode(password);

        Team team = getTeam(teamId);

        boolean checkPwd = confirmTeamPwd(team, password);

        if (!checkPwd) {
            throw new CustomException(TeamErrorCode.INVALID_PASSWORD);
        }

        // 팀 테이블 삭제하기 전에 CrazyEight 삭제를 해야 한다.
        // 추후 추가 예정..
        teamRepository.deleteById(teamId);
    }

    // 현재 참석된 참석자 리스트 조회하기
    public List<User> getUsers(Long teamId) {
        Team team = getTeam(teamId);
        return team.getUserList();
    }
}
