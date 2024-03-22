package com.exampleSmartStudent.demo8.Dao;

import com.exampleSmartStudent.demo8.entity.Contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
   //pagination
   @Query("select c from Contact c where c.user.id = :userId")
   //public List<Contact> findContactsByUser(@Param("userId") int userId);
   public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
   @Transactional
   void deleteByCid(int cid);



}
