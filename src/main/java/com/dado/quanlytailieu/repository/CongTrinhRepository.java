package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.model.CongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongTrinhRepository extends JpaRepository<CongTrinh, Long> {
}
