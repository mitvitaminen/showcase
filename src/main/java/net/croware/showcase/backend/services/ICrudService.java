package net.croware.showcase.backend.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICrudService<T> {

    default long count() {
        return getRepository().count();
    }

    T createNew();

    default void delete(long id) {
        delete(load(id));
    }

    default void delete(T entity) {
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        getRepository().delete(entity);
    }

    JpaRepository<T, Long> getRepository();

    default T load(long id) {
        final T entity = getRepository().findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    default T save(T entity) {
        return getRepository().saveAndFlush(entity);
    }

}
