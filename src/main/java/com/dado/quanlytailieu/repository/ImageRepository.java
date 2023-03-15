package com.dado.quanlytailieu.repository;

import java.time.LocalDateTime;

import com.dado.quanlytailieu.model.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image getImageByFileName(String name);

    @Query(value = "SELECT i FROM Image i where i.fileEntity.id=?1 and i.fileName=?2")
    Image getImageByFileId(Long id, String fineName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Image i " +
            "SET i.createdTime = ?1, i.createdUser = ?2, i.fileName = ?3, i.type = ?4, i.fileEntity.id = ?5 " +
            "WHERE i.id = ?6")
    void updateImageById(LocalDateTime createdTime, String createdUser, String fileName, String type, int fileId, int imageId);
}
