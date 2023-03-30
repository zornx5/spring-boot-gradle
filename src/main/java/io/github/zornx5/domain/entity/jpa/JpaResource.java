package io.github.zornx5.domain.entity.jpa;

import io.github.zornx5.domain.entity.AbstractResource;
import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = JpaResource.class)
    @JoinTable(name = "t_resources_resources",
            joinColumns = @JoinColumn(name = "resource_id "),
            inverseJoinColumns = @JoinColumn(name = "children_resource_id"))
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
}
