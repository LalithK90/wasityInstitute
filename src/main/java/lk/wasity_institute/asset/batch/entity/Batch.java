package lk.wasity_institute.asset.batch.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wasity_institute.asset.batch.entity.enums.ClassDay;
import lk.wasity_institute.asset.batch.entity.enums.Grade;
//import lk.wasity_institute.asset.batch.entity.enums.Medium;
import lk.wasity_institute.asset.batch.entity.enums.Medium;
import lk.wasity_institute.asset.batch_exam.entity.BatchExam;
import lk.wasity_institute.asset.batch_student.entity.BatchStudent;
import lk.wasity_institute.asset.common_asset.model.enums.LiveDead;
import lk.wasity_institute.asset.teacher.entity.Teacher;
import lk.wasity_institute.asset.time_table.entity.TimeTable;
import lk.wasity_institute.util.audit.AuditEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonFilter( "Batch" )
public class Batch extends AuditEntity {

  @Column( unique = true )
  private String code;

  @Column( unique = true )
  private String name;

  private String year;

  @Enumerated( EnumType.STRING )
  private Grade grade;

//  @Enumerated( EnumType.STRING )
//  private Medium medium;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @Enumerated( EnumType.STRING )
  private ClassDay classDay;

  @DateTimeFormat( pattern = "HH:mm" )
  private LocalTime startAt;

  @DateTimeFormat( pattern = "HH:mm" )
  private LocalTime endAt;

  @ManyToOne( fetch = FetchType.EAGER )
  private Teacher teacher;

  @OneToMany( mappedBy = "batch" )
  private List< BatchStudent > batchStudents;

  @OneToMany( mappedBy = "batch" )
  private List< TimeTable > timeTables;

  @OneToMany( mappedBy = "batch" )
  private List< BatchExam > batchExams;

  @Transient
  private int count;

  @Transient
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate date;
}
