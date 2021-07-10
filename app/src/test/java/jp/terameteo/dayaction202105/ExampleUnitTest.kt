package jp.terameteo.dayaction202105

import com.google.common.truth.Truth
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun division_isCorrect(){
        val result:Int = 41 / 8
        Truth.assertThat(result).isEqualTo(5)
        val modResult = 41 % 8
        Truth.assertThat(modResult).isEqualTo(1)
    }
}

// Hamcrest AssertJ  Truth アサーションライブラリ
// AssertJ AndroidはDiscontinue
//　