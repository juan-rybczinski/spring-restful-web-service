package com.example.restfulwebservice.user

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

//@JsonFilter("UserInfo")
//@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
class User(
    @Id
    @GeneratedValue
    override var id: Int?,
//    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    override val name: String,
//    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
    override val joinDate: Date,
//    @ApiModelProperty(notes = "사용자 패스워드을 입력해 주세요.")
    override val password: String?,
//    @ApiModelProperty(notes = "사용자 주민등록번호 입력해 주세요.")
    override val ssn: String?,

    @OneToMany(mappedBy = "user")
    val posts: List<Post>?
) : UserBase(id, name, joinDate, password, ssn) {
    constructor(id: Int, name: String, joinDate: Date, password: String, ssn: String) : this(id, name, joinDate, password, ssn, null)
}