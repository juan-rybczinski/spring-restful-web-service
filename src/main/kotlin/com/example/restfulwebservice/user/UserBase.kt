package com.example.restfulwebservice.user

import java.util.*
import javax.validation.constraints.Past
import javax.validation.constraints.Size

//@JsonIgnoreProperties(value = ["password", "ssn"])
//abstract class UserBase(
//    open var id: Int?,
//    @field:Size(min = 2, message = "Name은 2글자 이상 입력해주세요")
//    open val name: String,
//    @field:Past
//    open val joinDate: Date,
////    private val password: String,
////    private val ssn: String
////    @JsonIgnore
//    open val password: String,
////    @JsonIgnore
//    open val ssn: String
//)
abstract class UserBase(
    open var id: Int?,
    @field:Size(min = 2, message = "Name은 2글자 이상 입력해주세요")
    open val name: String,
    @field:Past
    open val joinDate: Date,
//    private val password: String,
//    private val ssn: String
//    @JsonIgnore
    open val password: String?,
//    @JsonIgnore
    open val ssn: String?
) {
    constructor(): this(0, "", Date(), "", "")
}