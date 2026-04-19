package yegor.cheprasov.pokedex.core.design.composable.text_fields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.ui.theme.LocalPokedexColors
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_close
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Immutable
data class TextFieldColors(
    val focusedBorderColor: Color,
    val unfocusedBorderColor: Color,
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val hintColor: Color,
    val clearIconColor: Color,
)

object TextFieldDefaults {
    @Composable
    fun colors(
        focusedBorderColor: Color = LocalPokedexColors.current.cardBorder,
        unfocusedBorderColor: Color = Color.Black.copy(alpha = 0.12f),
        focusedTextColor: Color = Color.Black,
        unfocusedTextColor: Color = Color.Black,
        hintColor: Color = Color.Black.copy(alpha = 0.38f),
        clearIconColor: Color = Color.Black.copy(alpha = 0.38f),
    ) = TextFieldColors(
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        hintColor = hintColor,
        clearIconColor = clearIconColor,
    )
}

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = rememberTextFieldState(),
    hint: String = "",
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    hintTextStyle: TextStyle = textStyle,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState()
) {
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) colors.focusedBorderColor else colors.unfocusedBorderColor,
    )
    val textColor by animateColorAsState(
        targetValue = if (isFocused) colors.focusedTextColor else colors.unfocusedTextColor,
    )

    BasicTextField(
        state = textFieldState,
        modifier = modifier.height(56.dp),
        textStyle = textStyle.merge(TextStyle(color = textColor)),
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        interactionSource = interactionSource,
        scrollState = scrollState,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                    .then(
                        if (!enabled && onClick != null) Modifier.clickable(onClick = onClick)
                        else Modifier
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingIcon?.let {
                    it()
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Crossfade(
                    targetState = textFieldState.text.isEmpty(),
                    modifier = Modifier.weight(1f),
                ) { isEmpty ->
                    if (isEmpty) {
                        Text(
                            text = hint,
                            style = hintTextStyle.merge(TextStyle(color = colors.hintColor)),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    } else {
                        innerTextField()
                    }
                }

                AnimatedVisibility(
                    visible = textFieldState.text.isNotEmpty(),
                    enter = fadeIn() + scaleIn(initialScale = 0.7f),
                    exit = fadeOut() + scaleOut(targetScale = 0.7f),
                ) {
                    IconButton(
                        onClick = { textFieldState.clearText() },
                        modifier = Modifier.size(20.dp),
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = colors.clearIconColor,
                        )
                    }
                }

                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    it()
                }
            }
        }
    )
}

@Preview
@Composable
private fun TextFieldPreview() {
    PokedexTheme {
        Column {
            TextField(
                textFieldState = rememberTextFieldState(),
                modifier = Modifier.padding(16.dp),
                hint = "Search"
            )
        }
    }
}