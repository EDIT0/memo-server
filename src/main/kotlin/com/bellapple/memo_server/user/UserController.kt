package com.bellapple.memo_server.user

import com.bellapple.memo_server.user.dto.request.ReqCreateUser
import com.bellapple.memo_server.user.dto.request.ReqDeleteUser
import com.bellapple.memo_server.user.dto.request.ReqGetUser
import com.bellapple.memo_server.user.dto.request.ReqListUser
import com.bellapple.memo_server.user.dto.request.ReqUpdateUser
import com.bellapple.memo_server.user.dto.response.ResCreateUser
import com.bellapple.memo_server.user.dto.response.ResDeleteUser
import com.bellapple.memo_server.user.dto.response.ResGetUser
import com.bellapple.memo_server.user.dto.response.ResListUser
import com.bellapple.memo_server.user.dto.response.ResUpdateUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// HTTP 요청을 받아서 JSON으로 응답
// 요청 DTO에서 값을 꺼내 Service에 넘기고, 결과를 응답 DTO로 바꿈
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/create")
    fun createUser(@RequestBody request: ReqCreateUser): ResCreateUser {
        return ResCreateUser.from(userService.createUser(request.username, request.email, request.password))
    }

    // 조회는 GET. 쿼리 파라미터(?id=1 등)를 @ModelAttribute로 DTO에 담음
    @GetMapping("/list") // GET /api/users/list
    fun getAllUser(@ModelAttribute request: ReqListUser): List<ResListUser> {
        return userService.getAllUsers().map { ResListUser.from(it) }
    }

    @GetMapping("/detail") // GET /api/users/detail?id=1
    fun getOneUser(@ModelAttribute request: ReqGetUser): ResGetUser {
        return ResGetUser.from(userService.getUser(request.id))
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody request: ReqUpdateUser): ResUpdateUser {
        return ResUpdateUser.from(
            userService.updateUser(request.id, request.username, request.email, request.password)
        )
    }

    @PostMapping("/delete")
    fun deleteUser(@RequestBody request: ReqDeleteUser): ResDeleteUser {
        userService.deleteUser(request.id)
        return ResDeleteUser(id = request.id)
    }
}
