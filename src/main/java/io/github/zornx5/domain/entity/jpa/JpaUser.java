package io.github.zornx5.domain.entity.jpa;

import io.github.zornx5.domain.entity.AbstractUser;
import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

/**
 * 用户实体
 *
 * @author zornx5
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "t_users")
@ToString
public class JpaUser extends AbstractUser<JpaUser, Long> {

    private static final long serialVersionUID = 14130110092L;

    private String firstName;

    private String lastName;

    private String avatar;

    private int age;

    @Column(nullable = false)
    private UserGender gender;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    private String address;

    @Column(nullable = false)
    private String password;

    private int loginFailedAttempts;

    @Column(nullable = false)
    private UserStatus status;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = JpaRole.class)
    @JoinTable(
            name = "t_users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @ToString.Exclude
    private Collection<Role<JpaUser, Long>> roles;

    public JpaUser(Long id) {
        this.setId(id);
    }

    public static JpaUser of(User<JpaUser, Long> user) {
        if (user instanceof JpaUser) {
            return (JpaUser) (user);
        }

        var target = new JpaUser();
        BeanUtils.copyProperties(user, target);
        return target;
    }

    @Override
    public void addRole(Role<JpaUser, Long> role) {
        getRoles().add(role);
    }

    @Override
    public void removeRole(Role<JpaUser, Long> role) {
        getRoles().remove(role);
    }
}
