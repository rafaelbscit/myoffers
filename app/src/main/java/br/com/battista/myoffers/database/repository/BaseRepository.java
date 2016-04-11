package br.com.battista.myoffers.database.repository;


import java.util.List;

import br.com.battista.myoffers.model.BaseEntity;

public interface BaseRepository<Entity extends BaseEntity> {

    void save(Entity entity);

    Entity findById(Long id);

    void update(Entity entity);

    void deleteById(Long id);

    List<Entity> findAll();
}
