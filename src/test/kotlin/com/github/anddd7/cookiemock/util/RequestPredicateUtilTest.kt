package com.github.anddd7.cookiemock.util

import com.github.anddd7.cookiemock.domain.Condition
import com.github.anddd7.cookiemock.domain.Target
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RequestPredicateUtilTest {

  @Test
  internal fun `should build path variable predicate with given condition`() {
    val condition = Condition(Target.PathVariable, "{id} < 100 && {times}>1024")
    val predicate = RequestPredicateUtil.build(condition)

    Assertions.assertThat(predicate).isNotNull
  }
}
