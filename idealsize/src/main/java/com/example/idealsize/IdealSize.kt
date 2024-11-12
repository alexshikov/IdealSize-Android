package com.example.idealsize

/**
 * Represents the size of the body or clothing.
 */
enum class SizeType(val size: String) {
    S("S"),
    M("M"),
    L("L"),
    XL("XL")
}

/**
 * Indicates that the BMI value is outside of the reasonable range (1-100).
 */
class InvalidBodyMassIndexException(message:String): Exception(message)

/**
 * IdealSize provides the recommended size based on the BMI (Body Mass Index).
 */
object IdealSize {
    /**
     * Provides the recommended size based on the Body Mass Index (BMI).
     *
     * @param bmi Body Mass Index.
     * @throws InvalidBodyMassIndexException if the BMI value is outside of the reasonable range (1-100).
     * @return Recommended size of type [SizeType].
     */
    @Throws(InvalidBodyMassIndexException::class)
    fun sizeByBMI(bmi: Float): SizeType {
        if (bmi < 1 || bmi >= 100) {
            throw InvalidBodyMassIndexException("BMI value is outside of the reasonable range")
        }

        return when {
            bmi < 18.5 -> SizeType.S
            bmi < 25 -> SizeType.M
            bmi < 30 -> SizeType.L
            else -> SizeType.XL
        }
    }
}