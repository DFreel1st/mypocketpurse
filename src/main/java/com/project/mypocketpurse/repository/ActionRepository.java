package com.project.mypocketpurse.repository;

import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.Action;
import com.project.mypocketpurse.model.Category;
import com.project.mypocketpurse.model.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository
        extends JpaRepository<Action, Long> {
    Page<Action> findByCreatedBy(String login, Pageable pageable);

    List<Action> findByCreatedBy(String login);

    @Query(nativeQuery = true,
            value = """
                    select *
                    from actions a
                    left join accounts a2 on a2.id = a.account_id
                    where cast(a2.user_id as varchar) like COALESCE(:user_id, '%')
                    and cast(a.account_id as varchar) like COALESCE(:account_id, '%')
                    and cast(a.category_id as varchar) like COALESCE(:category_id, '%')
                    and COALESCE(cast(a.label_id as varchar), '%') like cast(:label_id as varchar)
                    """)
    Page<Action> searchAction(@Param(value = "account_id") String accountId,
                              @Param(value = "category_id") String categoryId,
                              @Param(value = "label_id") String labelId,
                              @Param(value = "user_id") String userId,
                              Pageable pageable);

    List<Action> findAllByAccountId(Long accountId);
}
