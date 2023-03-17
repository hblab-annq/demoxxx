package com.dado.quanlytailieu.repository;

import java.util.List;

import com.dado.quanlytailieu.model.ConstructionDocument;
import com.dado.quanlytailieu.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContructionDocumentRepository extends JpaRepository<ConstructionDocument, Long> {
    @Query(value = "SELECT i FROM ConstructionDocument i where i.construction.id=?1")
    List<ConstructionDocument> getConstructionDocsByConstructionId(String id);
}
