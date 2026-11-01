package com.bellapple.memo_server.memo

import com.bellapple.memo_server.memo.dto.request.ReqCreateMemo
import com.bellapple.memo_server.memo.dto.request.ReqDeleteMemo
import com.bellapple.memo_server.memo.dto.request.ReqGetMemo
import com.bellapple.memo_server.memo.dto.request.ReqListMemo
import com.bellapple.memo_server.memo.dto.request.ReqUpdateMemo
import com.bellapple.memo_server.memo.dto.response.ResCreateMemo
import com.bellapple.memo_server.memo.dto.response.ResDeleteMemo
import com.bellapple.memo_server.memo.dto.response.ResGetMemo
import com.bellapple.memo_server.memo.dto.response.ResListMemo
import com.bellapple.memo_server.memo.dto.response.ResUpdateMemo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// HTTP 요청을 받아서 JSON으로 응답
// 요청 DTO에서 값을 꺼내 Service에 넘기고, 결과를 응답 DTO로 바꿈
@RestController
@RequestMapping("/api/memos")
class MemoController(
    private val memoService: MemoService
) {
    @PostMapping("/create")
    fun createMemo(@RequestBody request: ReqCreateMemo): ResCreateMemo {
        return ResCreateMemo.from(memoService.createMemo(request.userId, request.content))
    }

    // 조회는 GET. 쿼리 파라미터(?id=1 등)를 @ModelAttribute로 DTO에 담음
    @GetMapping("/list") // GET /api/memos/list?userId=1
    fun getAllMemo(@ModelAttribute request: ReqListMemo): List<ResListMemo> {
        return memoService.getAllMemos(request.userId).map { ResListMemo.from(it) }
    }

    @GetMapping("/detail") // GET /api/memos/detail?id=1
    fun getOneMemo(@ModelAttribute request: ReqGetMemo): ResGetMemo {
        return ResGetMemo.from(memoService.getMemo(request.id))
    }

    @PostMapping("/update")
    fun updateMemo(@RequestBody request: ReqUpdateMemo): ResUpdateMemo {
        return ResUpdateMemo.from(memoService.updateMemo(request.id, request.content))
    }

    @PostMapping("/delete")
    fun deleteMemo(@RequestBody request: ReqDeleteMemo): ResDeleteMemo {
        memoService.deleteMemo(request.id)
        return ResDeleteMemo(id = request.id)
    }
}
