package com.example.idealsize

import org.junit.Test
import org.junit.Assert.*

data class SizeWithBMI(val bmi: Float, val size: SizeType)

class IdealSizeUnitTests {

    @Test
    fun negativeBMI_throwsError() {
        assertThrows(InvalidBodyMassIndexException::class.java) {
            IdealSize.sizeByBMI(-1f)
        }
    }

    @Test
    fun zeroBMI_throwsError() {
        assertThrows(InvalidBodyMassIndexException::class.java) {
            IdealSize.sizeByBMI(0f)
        }
    }

    @Test
    fun hugeBMI_throwsError() {
        assertThrows(InvalidBodyMassIndexException::class.java) {
            IdealSize.sizeByBMI(10000f)
        }
    }

    @Test
    fun returnsProperSize() {
        val testCases = listOf(
            SizeWithBMI(bmi = 15f, size = SizeType.S),
            SizeWithBMI(bmi = 18.499f, size = SizeType.S),
            SizeWithBMI(bmi = 18.5f, size = SizeType.M),
            SizeWithBMI(bmi = 20f, size = SizeType.M),
            SizeWithBMI(bmi = 24.99f, size = SizeType.M),
            SizeWithBMI(bmi = 25f, size = SizeType.L),
            SizeWithBMI(bmi = 29.99f, size = SizeType.L),
            SizeWithBMI(bmi = 30f, size = SizeType.XL),
            SizeWithBMI(bmi = 50f, size = SizeType.XL),
            SizeWithBMI(bmi = 90f, size = SizeType.XL)
        )

        for (case in testCases) {
            val size = IdealSize.sizeByBMI(case.bmi)
            assertEquals("Expected size ${case.size} for BMI ${case.bmi}", case.size, size)
        }
    }
}
