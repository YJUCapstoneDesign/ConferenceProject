package org.example.mindmap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "node")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Long nd_id;

    //@Column(length = 200, nullable = false)
    //private String node;

}
