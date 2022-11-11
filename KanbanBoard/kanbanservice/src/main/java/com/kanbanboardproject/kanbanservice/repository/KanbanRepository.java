package com.kanbanboardproject.kanbanservice.repository;

import com.kanbanboardproject.kanbanservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KanbanRepository extends MongoRepository<User,String> {
}
