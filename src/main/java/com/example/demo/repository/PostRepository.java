package com.example.demo.repository;


import com.example.demo.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

//TODO: Sort by Date

    Iterable<Post> findByOrderByIdDesc();

}
