package com.yemeksepeti.YemekPayCase.Repo;

import com.yemeksepeti.YemekPayCase.Model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ToDoRepo extends JpaRepository<ToDo,Integer> {



    @Modifying
    @Query("UPDATE ToDo t SET t.completed = :c WHERE t.id = :id")
    int updateById(@Param("id") int id,@Param("c") boolean completed);



}
