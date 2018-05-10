package com.erfangc.sesamelab.web.dashboard

import com.erfangc.sesamelab.web.user.UserService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@CrossOrigin
@RestController
@RequestMapping("api/v1/dashboard")
class DashboardController(private val searchService: SearchService, private val userService: UserService) {
    @GetMapping
    fun dashboard(principal: Principal?): Dashboard {
        return searchService.dashboard(userService.getUserFromAuthenticatedPrincipal(principal))
    }
}