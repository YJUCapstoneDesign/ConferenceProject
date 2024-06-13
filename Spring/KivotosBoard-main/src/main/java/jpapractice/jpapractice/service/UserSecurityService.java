package jpapractice.jpapractice.service;

import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.misc.UserRole;
import jpapractice.jpapractice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserSecurityService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // System.out.println("UserSecurityService: " + id);
        Optional<Student> optionalUser = this.memberRepository.findByAccountId(id);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        Student user = optionalUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(id)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(user.getAccountId(), user.getPasswd(), authorities);

    }

}
