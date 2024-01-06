package com.gsdc.server.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IService<T>{

    List<T> findAll();

    Page<T> findAllPageable(int size, int pageNumber);

    T findById(Long id);

    T findByUUID(String uuid);

    T update(T obj, Long id);
    T update(T obj, String uuid);

    T create(T obj);

    void delete(Long id);
    void delete(String uuid);

}
