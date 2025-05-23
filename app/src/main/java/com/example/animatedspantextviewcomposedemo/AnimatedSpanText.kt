package com.example.animatedspantextviewcomposedemo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.example.animatedspantextviewcomposedemo.AnimatedSpanText.GradientWidth
import com.example.animatedspantextviewcomposedemo.AnimatedSpanText.GradientWidth.Raw
import com.example.animatedspantextviewcomposedemo.AnimatedSpanText.GradientWidth.TextSizeMultiple

object AnimatedSpanText {
    val DEFAULT_COLORS = listOf(Color.Red, Color.Green, Color.Blue)
    const val DEFAULT_DURATION_MS = 10_000

    sealed interface GradientWidth {
        /** @param multiple The width of the gradient will be calculated: textWidth * multiple. */
        data class TextSizeMultiple(val multiple: Float = 1f) : GradientWidth

        /** @param width The width of the gradient in pixels. */
        data class Raw(val width: Float) : GradientWidth
    }
}

@Composable
fun AnimatedSpanText(
    text: String,
    gradientColors: List<Color> = AnimatedSpanText.DEFAULT_COLORS,
    gradientWidth: GradientWidth = TextSizeMultiple(1f),
    animationDuration: Int = AnimatedSpanText.DEFAULT_DURATION_MS,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    // Create an infinite transition for animation
    val infiniteTransition = rememberInfiniteTransition(label = "gradientAnimation")

    // Animate an offset value from 0f to 1f repeatedly
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration, // Duration of one cycle
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "offsetAnimation"
    )

    // This is in pixels! Offset works in pixels.
    var textWidth by remember {
        mutableFloatStateOf(if (gradientWidth is Raw) gradientWidth.width else 0f)
    }
    val multiple = if (gradientWidth is TextSizeMultiple) gradientWidth.multiple else 0f

    // Calculate the gradient's start and end offsets for horizontal movement
    val brush = Brush.linearGradient(
        colors = gradientColors,
        // Move start point horizontally
        start = Offset(offset * textWidth * multiple, 0f),
        // Move end point horizontally. 0.5f instead of 1f for mirror repeat
        end = Offset((offset + 0.5f) * textWidth * multiple, 0f),
        tileMode = TileMode.Mirror
    )

    BasicText(
        text = text,
        modifier = modifier,
        style = style.merge(
            TextStyle(
                brush = brush,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign ?: TextAlign.Unspecified,
                lineHeight = lineHeight
            )
        ),
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = { result ->
            // Internal logic: Update textWidth
            textWidth = result.size.width.toFloat()
            // Call the custom onTextLayout if provided
            onTextLayout?.invoke(result)
        }
    )
}
