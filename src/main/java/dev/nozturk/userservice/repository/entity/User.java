package dev.nozturk.userservice.repository.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 76, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private UserGroup group;

    public List<GrantedAuthority> getRoles() {
        List<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(this.group);
        return roleList;
    }

}
