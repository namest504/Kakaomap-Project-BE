package com.list.kakaoMap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column
    private String name;
    @Column
    private String detail;
    @Column
    private double posX;
    @Column
    private double posY;

}
