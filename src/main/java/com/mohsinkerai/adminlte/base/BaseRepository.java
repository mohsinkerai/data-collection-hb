package com.mohsinkerai.adminlte.base;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {

}
