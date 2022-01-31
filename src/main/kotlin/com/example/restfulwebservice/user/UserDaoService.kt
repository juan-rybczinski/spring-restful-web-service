package com.example.restfulwebservice.user

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDaoService {
    init {
        users.add(User(1, "Rybczinski", Date(), "password", "123456-1234567"))
        users.add(User(2, "Juan", Date(), "password", "123456-1234567"))
        users.add(User(3, "Suyoung", Date(), "password", "123456-1234567"))

        usersCount = users.size
    }

    fun findAll(): List<User> {
        return users
    }

    fun save(user: User): User {
        user.id = user.id ?: ++usersCount
        users.add(user)

        return user
    }

    fun findOne(id: Int): User? {
        return users.find {
            it.id == id
        }
    }

    fun deleteById(id: Int): User? {
        val iterator = users.iterator()
        while (iterator.hasNext()) {
            val user = iterator.next()

            if (user.id == id) {
                iterator.remove()
                return user
            }
        }

        return null
    }

    companion object {
        val users = mutableListOf<User>()
        var usersCount = users.size
    }
}