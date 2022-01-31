package com.example.restfulwebservice.user

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.beans.BeanUtils
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminUserController(private val service: UserDaoService) {
    @GetMapping(path = ["/users"])
    fun retrieveAllUsers(): MappingJacksonValue {
        val users = service.findAll()
        val mapping = MappingJacksonValue(users)
        val filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "password", "ssn")
        val filters = SimpleFilterProvider().addFilter("UserInfo", filter)
        mapping.filters = filters
        return mapping
    }

//    @GetMapping(path = ["/v1/users/{id}"])
//    @GetMapping(path = ["/users/{id}/"], params = ["version=1"])
//    @GetMapping(path = ["/users/{id}"], headers = ["X-API-VERSION=1"])
    @GetMapping(path = ["/users/{id}"], produces = ["application/vnd.company.appv1+json"])
    fun retrieveUserV1(@PathVariable id: Int): MappingJacksonValue {
        val user = service.findOne(id) ?: throw UserNotFoundException("ID[$id] not found")
        val mapping = MappingJacksonValue(user)
        val filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "password", "ssn")
        val filters = SimpleFilterProvider().addFilter("UserInfo", filter)
        mapping.filters = filters
        return mapping
    }

//    @GetMapping(path = ["/v2/users/{id}"])
//    @GetMapping(path = ["/users/{id}/"], params = ["version=2"])
//    @GetMapping(path = ["/users/{id}"], headers = ["X-API-VERSION=2"])
    @GetMapping(path = ["/users/{id}"], produces = ["application/vnd.company.appv2+json"])
    fun retrieveUserV2(@PathVariable id: Int): MappingJacksonValue {
        val user = service.findOne(id) ?: throw UserNotFoundException("ID[$id] not found")
        val userV2 = UserV2()
        BeanUtils.copyProperties(user, userV2)
        userV2.grade = "VIP"

        val mapping = MappingJacksonValue(userV2)
        val filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "grade")
        val filters = SimpleFilterProvider().addFilter("UserInfoV2", filter)
        mapping.filters = filters
        return mapping
    }
}