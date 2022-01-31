package com.example.restfulwebservice.user

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
class UserController(private val service: UserDaoService) {
    @GetMapping(path = ["/users"])
    fun retrieveAllUsers(): List<User> {
        return service.findAll()
    }

    @GetMapping(path = ["/users/{id}"])
    fun retrieveUser(@PathVariable id: Int): EntityModel<User> {
        val user = service.findOne(id) ?: throw UserNotFoundException("ID[$id] not found")

        // HATEOAS
        val model = EntityModel.of(user)
        val linkTo = linkTo<UserController> { retrieveAllUsers() }
        model.add(linkTo.withRel("all-users"))

        return model
    }

    @PostMapping(path = ["/users"])
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        val savedUser = service.save(user)
        val location =
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.id).toUri()

        return ResponseEntity.created(location).build()
    }

    @DeleteMapping(path = ["/users/{id}"])
    fun deleteUser(@PathVariable id: Int) {
        service.deleteById(id) ?: throw UserNotFoundException("ID[$id] not found")
    }
}