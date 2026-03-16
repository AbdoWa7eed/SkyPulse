package com.iti.skypulse.ui.components.transformations

import android.graphics.Bitmap
import android.graphics.Color
import coil.size.Size
import coil.transform.Transformation

class WhiteToColorTransformation(private val targetColor: Int) : Transformation {

    override val cacheKey = "WhiteToColorTransformation($targetColor)"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val output = input.copy(Bitmap.Config.ARGB_8888, true)
        val pixels = IntArray(output.width * output.height)
        output.getPixels(pixels, 0, output.width, 0, 0, output.width, output.height)

        val threshold = 200

        for (i in pixels.indices) {
            val pixel = pixels[i]
            val a = Color.alpha(pixel)
            val r = Color.red(pixel)
            val g = Color.green(pixel)
            val b = Color.blue(pixel)

            if (a > 0 && r > threshold && g > threshold && b > threshold) {
                pixels[i] = Color.argb(
                    a,
                    Color.red(targetColor),
                    Color.green(targetColor),
                    Color.blue(targetColor)
                )
            }
        }

        output.setPixels(pixels, 0, output.width, 0, 0, output.width, output.height)
        return output
    }
}