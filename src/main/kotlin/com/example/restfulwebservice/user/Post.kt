package com.example.restfulwebservice.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Post(
    @Id
    @GeneratedValue
    val id: Int?,
    val description: String,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User?
) {

}