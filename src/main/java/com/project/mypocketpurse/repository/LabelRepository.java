package com.project.mypocketpurse.repository;

import com.project.mypocketpurse.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository
        extends JpaRepository<Label, Long> {

    List<Label> findLabelByUserId(Long id);


}
