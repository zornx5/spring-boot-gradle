package io.github.zornx5.infrastructure.repository.jpa;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.jpa.JpaResource;
import io.github.zornx5.domain.entity.jpa.JpaUser;
import io.github.zornx5.infrastructure.repository.ResourceQuery;
import io.github.zornx5.infrastructure.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Jpa 资源存储实现
 *
 * @author zornx5
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class JpaResourceRepositoryImpl implements ResourceRepository<JpaUser, Long> {

    private final JpaResourceRepositoryDelegate delegate;

    @Override
    public Resource<JpaUser, Long> create() {
        JpaResource resource = new JpaResource();
        resource.init();
        return resource;
    }

    @Override
    public Resource<JpaUser, Long> create(Long id) {
        JpaResource resource = new JpaResource(id);
        resource.init();
        return resource;
    }

    @Override
    public Resource<JpaUser, Long> save(Resource<JpaUser, Long> resource) {
        return this.delegate.save(JpaResource.of(resource));
    }

    @Override
    public void delete(Resource<JpaUser, Long> resource) {
        this.delegate.delete(JpaResource.of(resource));
    }

    @Override
    public Optional<Resource<JpaUser, Long>> findById(Long id) {
        return CastUtils.cast(this.delegate.findById(id));
    }

    @Override
    public Optional<Resource<JpaUser, Long>> findByQuery(ResourceQuery query) {
        if (Objects.nonNull(query.getName())) {
            return CastUtils.cast(this.delegate.findByName(query.getName()));
        }
        return Optional.empty();
    }

    @Override
    public List<Resource<JpaUser, Long>> findAllById(Collection<Long> ids) {
        return CastUtils.cast(delegate.findAllById(ids));
    }

    @Override
    public Page<Resource<JpaUser, Long>> findAll(ResourceQuery query, Pageable pageable) {
        return CastUtils.cast(delegate.findAll(pageable));
    }
}
