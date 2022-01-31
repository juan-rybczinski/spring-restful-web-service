package com.example.restfulwebservice.user

import com.fasterxml.jackson.annotation.JsonFilter
import java.util.*

@JsonFilter("UserInfoV2")
data class UserV2(
    override var id: Int? = 0,
    override var name: String = "",
    override var joinDate: Date = Date(),
    override var password: String = "",
    override var ssn: String = "",
) : UserBase(id, name, joinDate, password, ssn) {
    lateinit var grade: String
}