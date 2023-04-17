package io.github.zornx5.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.zornx5.domain.entity.AbstractRole;
import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;

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
public class JpaRole extends AbstractRole<JpaUser, Long> {

    @Serial
    private static final long serialVersionUID = 14130110092L;

    @ManyToMany(mappedBy = "roles", targetEntity = JpaUser.class)
    @JsonBackReference
    @ToString.Exclude
    private Collection<User<JpaUser, Long>> users;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = JpaResource.class)
    @JoinTable(name = "t_roles_resources",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id")}
    )
    @JsonManagedReference
    @ToString.Exclude
    private Collection<Resource<JpaUser, Long>> resources;

    public JpaRole(Long id) {
        this.setId(id);
    }

    public static JpaRole of(Role<JpaUser, Long> role) {
        if (role instanceof JpaRole) {
            return (JpaRole) (role);
        }

        var target = new JpaRole();
        BeanUtils.copyProperties(role, target);
        return target;
    }

    @Override
    public Collection<String> getPermissions() {
        HashSet<String> permissions = new HashSet<>();
        if (CollectionUtils.isNotEmpty(this.getResources())) {
            for (Resource<JpaUser, Long> resource : this.getResources()) {
                permissions.addAll(resource.getPermissions());
            }
        }
        return permissions;
    }

    @Override
    public void addResource(Resource<JpaUser, Long> resource) {
        getResources().add(resource);
    }

    @Override
    public void removeResource(Resource<JpaUser, Long> resource) {
        getResources().remove(resource);
    }
}
