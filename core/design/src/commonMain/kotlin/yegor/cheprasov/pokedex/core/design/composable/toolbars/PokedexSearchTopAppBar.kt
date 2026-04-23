package yegor.cheprasov.pokedex.core.design.composable.toolbars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_close
import yegor.cheprasov.pokedex.core.design.composable.buttons.BackButton
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextField
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextFieldColors
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextFieldDefaults
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun PokedexSearchTopAppBar(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = rememberTextFieldState(),
    hint: String = "",
    textStyle: TextStyle = PokedexTheme.typography.bodyMedium,
    hintTextStyle: TextStyle = textStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    scrollState: ScrollState = rememberScrollState(),
    onBack: () -> Unit = {}
) {
    val spacing = PokedexTheme.spacing
    val colors = PokedexTheme.colors
    val typography = PokedexTheme.typography

    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }

    val largeHeight = statusBarHeight + 64.dp
    val smallHeight = statusBarHeight + 36.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(largeHeight)
            .background(PokedexTheme.colors.primary)
            .padding(horizontal = spacing.small)
            .padding(top = statusBarHeight)
    ) {
        BackButton(onClick = onBack)
        Spacer(modifier = Modifier.width(12.dp))
        TextField(
            textFieldState = textFieldState,
            hint = hint,
            textStyle = textStyle,
            hintTextStyle = hintTextStyle,
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            scrollState = scrollState
        )
    }
}

@Composable
private fun TextFieldInner(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = rememberTextFieldState(),
    hint: String = "",
    textStyle: TextStyle = PokedexTheme.typography.bodyMedium,
    hintTextStyle: TextStyle = textStyle,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    scrollState: ScrollState = rememberScrollState(),
) {
    val isFocused by interactionSource.collectIsFocusedAsState()

    val textColor by animateColorAsState(
        targetValue = if (isFocused) colors.focusedTextColor else colors.unfocusedTextColor,
    )

    BasicTextField(
        state = textFieldState,
        modifier = modifier.heightIn(min = 52.dp),
        textStyle = textStyle.merge(TextStyle(color = textColor)),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        interactionSource = interactionSource,
        scrollState = scrollState,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp)
                    .heightIn(min = 52.dp)
                    .clip(CircleShape)
                    .background(PokedexTheme.colors.background)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { textFieldState.clearText() },
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = colors.clearIconColor,
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun PokedexSearchTopAppBarPreview() {
    PokedexTheme {
        Scaffold(topBar = {
            PokedexSearchTopAppBar(hint = "Search")
        }) {
            Column(modifier = Modifier.fillMaxSize().padding(it)) { }
        }
    }
}