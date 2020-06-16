package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.Case
import com.github.anddd7.cookiemock.domain.Cookie
import com.github.anddd7.cookiemock.domain.CookieRepository
import com.github.anddd7.cookiemock.util.RequestPredicateUtil
import com.ninjasquad.springmockk.MockkBean
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RequestPredicate
import java.util.UUID

@SpringBootTest
@AutoConfigureWebTestClient
internal class MockHandlerTest {

  @MockkBean
  private lateinit var cookieRepository: CookieRepository

  @Autowired
  private lateinit var webTestClient: WebTestClient

  @Nested
  inner class GetProductTest {
    private val boxId = UUID.randomUUID()
    private val cookie = Cookie(
      uuid = UUID.randomUUID(),
      boxId = boxId,
      method = HttpMethod.GET,
      urlPattern = "/product/{id}",
      cases = listOf(
        Case(
          name = "return products which id is less than 10",
          condition = "id < 10",
          body = "{\"message\":\"id < 10\"}"
        ),
        Case(
          name = "return products which id is less than 100",
          condition = "id < 100",
          body = "{\"message\":\"id < 100\"}"
        ),
        Case(
          name = "return products which id is less than 1000",
          condition = "id < 1000",
          body = "{\"message\":\"id < 1000\"}"
        )
      )
    )

    @BeforeEach
    internal fun setUp() {
      coEvery { cookieRepository.getByBoxId(boxId) } returns flowOf(cookie)
      mockkObject(RequestPredicateUtil)
      every { RequestPredicateUtil.build("id < 10") } returns RequestPredicate {
        it.pathVariable("id").toLong() < 10
      }
      every { RequestPredicateUtil.build("id < 100") } returns RequestPredicate {
        it.pathVariable("id").toLong() < 100
      }
      every { RequestPredicateUtil.build("id < 1000") } returns RequestPredicate {
        it.pathVariable("id").toLong() < 1000
      }
    }

    @AfterEach
    internal fun tearDown() {
      clearAllMocks()
    }

    @Test
    fun `should return case when 'id less than 10'`() {
      webTestClient
        .get().uri("/mock/${boxId}/product/7")
        .exchange().expectBody()
        .jsonPath("$.message").isEqualTo("id < 10")
    }

    @Test
    fun `should return case when 'id less than 100'`() {
      webTestClient
        .get().uri("/mock/${boxId}/product/68")
        .exchange().expectBody()
        .jsonPath("$.message").isEqualTo("id < 100")
    }

    @Test
    fun `should return case when 'id less than 1000'`() {
      webTestClient
        .get().uri("/mock/${boxId}/product/298")
        .exchange().expectBody()
        .jsonPath("$.message").isEqualTo("id < 1000")
    }
  }
}

