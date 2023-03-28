package io.github.zornx5.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * 角色实体
 *
 * @author zornx5
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "t_roles")
@ToString
public class JpaRole extends AbstractRole<JpaUser, String> {
    private static final long serialVersionUID = 14130110092L;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, targetEntity = JpaRole.class)
    @JoinTable(name = "t_roles_resources",
            joinColumns = @JoinColumn(name = "role_id "),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    @ToString.Exclude
    private Collection<Resource<JpaUser, String>> resources;

    public JpaRole(String id) {
        this.setId(id);
    }

    public static JpaRole of(Role<JpaUser, String> role) {
        if (role instanceof JpaRole) {
            return (JpaRole) (role);
        }

        var target = new JpaRole();
        BeanUtils.copyProperties(role, target);
        return target;
    }
}
