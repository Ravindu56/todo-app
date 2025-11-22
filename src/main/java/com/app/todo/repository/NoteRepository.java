package com.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.todo.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
