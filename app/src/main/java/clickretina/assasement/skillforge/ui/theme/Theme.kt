package clickretina.assasement.skillforge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SkillForgeTeal,
    onPrimary = SkillForgeSurface,
    primaryContainer = SkillForgeLightTeal,
    onPrimaryContainer = SkillForgeFreeText,
    secondary = SkillForgeEmerald,
    onSecondary = SkillForgeSurface,
    secondaryContainer = SkillForgeBeginnerBackground,
    onSecondaryContainer = SkillForgeBeginnerText,
    tertiary = SkillForgeAmber,
    onTertiary = SkillForgeCharcoal,
    tertiaryContainer = SkillForgeLightAmber,
    onTertiaryContainer = SkillForgePriceText,
    background = SkillForgeBackground,
    onBackground = SkillForgeCharcoal,
    surface = SkillForgeSurface,
    onSurface = SkillForgeCharcoal,
    surfaceVariant = SkillForgeBackground,
    onSurfaceVariant = SkillForgeMediumGrey,
    outline = SkillForgeBorder,
    outlineVariant = SkillForgeBorder,
    error = SkillForgePriceText,
    onError = SkillForgeSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = SkillForgeTeal,
    onPrimary = SkillForgeCharcoal,
    primaryContainer = SkillForgeDeepTeal,
    onPrimaryContainer = SkillForgeLightTeal,
    secondary = SkillForgeEmerald,
    onSecondary = SkillForgeCharcoal,
    secondaryContainer = SkillForgeDarkSecondaryContainer,
    onSecondaryContainer = SkillForgeDarkSecondaryText,
    tertiary = SkillForgeAmber,
    onTertiary = SkillForgeCharcoal,
    tertiaryContainer = SkillForgeDarkAmberContainer,
    onTertiaryContainer = SkillForgeDarkAmberText,
    background = SkillForgeDarkBackground,
    onBackground = SkillForgeDarkText,
    surface = SkillForgeDarkSurface,
    onSurface = SkillForgeDarkText,
    surfaceVariant = SkillForgeDarkSurfaceVariant,
    onSurfaceVariant = SkillForgeDarkSubText,
    outline = SkillForgeDarkBorder,
    outlineVariant = SkillForgeDarkBorder,
    error = Color(0xFFEF4444),
    onError = Color.White,
)

@Composable
fun SkillForgeTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme
    val skillForgeColors = if (isDark) DarkSkillForgeColors else LightSkillForgeColors

    CompositionLocalProvider(LocalSkillForgeColors provides skillForgeColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
