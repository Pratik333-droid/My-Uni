package myuni.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class University implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "UniversityName", nullable = false)
    @NotBlank(message = "The name field can't be blank")
    private String name;

    @Column(name = "Location", nullable = false)
    private String location;


    @Column(name = "GlobalRank", nullable = false)
    @Min(value = 1, message = "University rank can't be less than 1")
    @Max(value = 24000, message = "University Rank can't be greater than 24000")
    private int globalRank;

    private ZonedDateTime createdAt;
    @PrePersist
    public void prePersist(){
        System.out.println("inside the prepersist method");
        createdAt = ZonedDateTime.now(ZoneId.of("Asia/Kathmandu"));
    }

}
