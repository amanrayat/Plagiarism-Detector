package com.team208.domain;







import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_course")
@IdClass(StudentCourseId.class)
public class StudentCourseEntity implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private CourseEntity course;
	
	private StudentEntity student;

	@Id
    @ManyToOne
    @JoinColumn(name = "courseId")
	public CourseEntity getCourse() {
		return course;
	}

	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	@Id
    @ManyToOne
    @JoinColumn(name = "studentDBid")
	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}
	
	

}
