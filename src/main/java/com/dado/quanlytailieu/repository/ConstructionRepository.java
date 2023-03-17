package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.model.Construction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionRepository extends JpaRepository<Construction, Long> {
}
