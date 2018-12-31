package com.mohsinkerai.adminlte.base;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BaseService<E extends BaseEntity> {

  protected final BaseRepository<E> baseRepository;

  protected BaseService(BaseRepository<E> baseRepository) {
    this.baseRepository = baseRepository;
  }

  public List<E> findAll() {
    return Lists.newArrayList(baseRepository.findAll());
  }

  public Page<E> findAll(Pageable pageable) {
    return baseRepository.findAll(pageable);
  }

  public Optional<E> findOne(Long id) {
    return Optional.ofNullable(baseRepository.findOne(id));
  }

  public E save(E e) {
    return baseRepository.save(e);
  }

  public void delete(E e) {
    baseRepository.delete(e);
  }

  public void delete(Long id) {
    baseRepository.delete(id);
  }
}
