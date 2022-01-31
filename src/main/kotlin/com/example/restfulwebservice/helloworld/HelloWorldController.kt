package com.example.restfulwebservice.helloworld

import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class HelloWorldController(
    private val messageSource: MessageSource
) {

    //    @RequestMapping(method = [RequestMethod.GET], path = ["/hello-world"])
    @GetMapping(path = ["/hello-world"])
    fun helloWorld(): String {
        return "Hello World"
    }

    @GetMapping(path = ["/hello-world-bean"])
    fun helloWorldBean(): HelloWorldBean {
        return HelloWorldBean("Hello World")
    }

    @GetMapping(path = ["/hello-world-bean/path-variable/{name}"])
    fun helloWorldBean(@PathVariable(value = "name") name: String): HelloWorldBean {
        return HelloWorldBean("Hello World, $name")
    }

    @GetMapping(path = ["/hello-world-internationalized"])
    fun helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) locale: Locale?): String {
        return messageSource.getMessage("greeting.message", null, locale ?: Locale.KOREA)
    }
}