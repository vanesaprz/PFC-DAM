package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.RedSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedSocialRepository extends JpaRepository<RedSocial, Long> {
}
