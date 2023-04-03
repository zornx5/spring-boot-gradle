package io.github.zornx5.domain.entity.jpa;

import io.github.zornx5.domain.entity.AbstractResource;
import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

/**
 * 资源实体
 *
 * @author zornx5
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_resources")
@ToString
public class JpaResource extends AbstractResource<JpaUser, Long> {
    private static final long serialVersionUID = 14130110092L;

    private ResourceType type;

    private String permission;

    private String icon;

    private String url;

    @ManyToMany(mappedBy = "resources", targetEntity = JpaRole.class)
    @ToString.Exclude
    private Collection<Role<JpaUser, Long>> roles;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = JpaResource.class)
    @JoinTable(name = "t_resources_resources",
            joinColumns = {@JoinColumn(name = "resource_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_children_id")}
    )
    @ToString.Exclude
    private Resource<JpaUser, Long> parent;

    @OneToMany(mappedBy = "parent", targetEntity = JpaResource.class)
    @ToString.Exclude
    private Collection<Resource<JpaUser, Long>> children;


    public JpaResource(Long id) {
        this.setId(id);
    }

    public static JpaResource of(Resource<JpaUser, Long> resource) {
        if (resource instanceof JpaResource) {
            return (JpaResource) (resource);
        }

        var target = new JpaResource();
        BeanUtils.copyProperties(resource, target);
        return target;
    }

    @Override
    public void addChild(Resource<JpaUser, Long> resource) {
        getChildren().add(resource);
    }

    @Override
    public void removeChild(Resource<JpaUser, Long> resource) {
        getChildren().remove(resource);
    }
}
