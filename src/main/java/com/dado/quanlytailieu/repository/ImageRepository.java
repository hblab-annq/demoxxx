package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image getImageByFileName(String name);

    @Query(value = "SELECT i FROM Image i where i.fileEntity.id=?1 and i.fileName=?2")
    Image getImageByFileId(Long id, String fineName);
}
