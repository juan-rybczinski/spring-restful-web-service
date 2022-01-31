package com.example.restfulwebservice.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/jpa")
class UserJpaController(
    private val userRepository: UserRepository
) {
    @Autowired lateinit var postRepository: PostRepository

    @GetMapping(path = ["/users"])
    fun retrieveAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping(path = ["/users/{id}"])
    fun retrieveUser(@PathVariable id: Int): EntityModel<User> {
        val user = userRepository.findById(id)
        if (!user.isPresent) {
            throw UserNotFoundException("ID[$id] not found")
        }

        val model = EntityModel.of(user.get())
        val linkTo = linkTo<UserJpaController> { retrieveAllUsers() }
        model.add(linkTo.withRel("all-users"))

        return model
    }

    @DeleteMapping(path = ["/users/{id}"])
    fun deleteUser(@PathVariable id: Int) {
        userRepository.deleteById(id)
    }

    @PostMapping(path = ["/users"])
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        val savedUser = userRepository.save(user)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @GetMapping(path = ["/users/{id}/posts"])
    fun retrieveAllPostsByUser(@PathVariable id: Int): List<Post>? {
        val user = userRepository.findById(id)
        if (!user.isPresent) {
            throw UserNotFoundException("ID[$id] not found")
        }

        return user.get().posts
    }

    @PostMapping(path = ["/users/{id}/posts"])
    fun createPost(@PathVariable id: Int, @RequestBody post: Post): ResponseEntity<Post> {
        val user = userRepository.findById(id)
        if (!user.isPresent) {
            throw UserNotFoundException("ID[$id] not found")
        }
        post.user = user.get()

        val savedPost = postRepository.save(post)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPost.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}