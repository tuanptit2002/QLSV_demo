package Tuhoc.example.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Tuhoc.example.demo.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Integer>{
	@Query("SELECT c FROM Course c where c.name = :n")
	Page<Course>searchByName(@Param("n") String n , Pageable pageable);
}
