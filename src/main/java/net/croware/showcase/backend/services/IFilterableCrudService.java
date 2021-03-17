package net.croware.showcase.backend.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilterableCrudService<T> extends ICrudService<T> {

    Long countAnyMatching(Optional<String> filter);

    Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

}
