package dev.nozturk.userservice.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
public class UserGroup implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "name='" + name + '\'' +
                '}';
    }
}
