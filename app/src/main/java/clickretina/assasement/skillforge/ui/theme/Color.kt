package clickretina.assasement.skillforge.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val SkillForgeTeal = Color(0xFF2DD4BF)
val SkillForgeDeepTeal = Color(0xFF0F9B8E)
val SkillForgeEmerald = Color(0xFF34D399)
val SkillForgeAmber = Color(0xFFFBBF24)
val SkillForgeBackground = Color(0xFFFAFAF9)
val SkillForgeSurface = Color(0xFFFFFFFF)
val SkillForgeCharcoal = Color(0xFF1C1C1E)
val SkillForgeMediumGrey = Color(0xFF6B7280)
val SkillForgeBorder = Color(0xFFE5E7EB)
val SkillForgeLightTeal = Color(0xFFCCFBF1)
val SkillForgeFreeText = Color(0xFF0F766E)
val SkillForgeLightAmber = Color(0xFFFEF3C7)
val SkillForgePriceText = Color(0xFF92400E)
val SkillForgeBeginnerBackground = Color(0xFFD1FAE5)
val SkillForgeBeginnerText = Color(0xFF065F46)
val SkillForgeIntermediateBackground = Color(0xFFDBEAFE)
val SkillForgeIntermediateText = Color(0xFF1E40AF)
val SkillForgePlayerBackground = Color(0xFF0F172A)

val SkillForgeDarkBackground = Color(0xFF0F1115)
val SkillForgeDarkSurface = Color(0xFF18181B)
val SkillForgeDarkSurfaceVariant = Color(0xFF27272A)
val SkillForgeDarkBorder = Color(0xFF52525B)
val SkillForgeDarkSecondaryContainer = Color(0xFF134E4A)
val SkillForgeDarkSecondaryText = Color(0xFFA7F3D0)
val SkillForgeDarkAmberContainer = Color(0xFF78350F)
val SkillForgeDarkAmberText = Color(0xFFFEF3C7)
val SkillForgeDarkText = Color(0xFFF3F4F6)
val SkillForgeDarkSubText = Color(0xFFD1D5DB)

@Immutable
data class SkillForgeColors(
    val freeBadgeContainer: Color,
    val freeBadgeContent: Color,
    val priceBadgeContainer: Color,
    val priceBadgeContent: Color,
    val beginnerChipContainer: Color,
    val beginnerChipContent: Color,
    val intermediateChipContainer: Color,
    val intermediateChipContent: Color,
    val playerBackground: Color,
)

val LightSkillForgeColors = SkillForgeColors(
    freeBadgeContainer = SkillForgeLightTeal,
    freeBadgeContent = SkillForgeFreeText,
    priceBadgeContainer = SkillForgeLightAmber,
    priceBadgeContent = SkillForgePriceText,
    beginnerChipContainer = SkillForgeBeginnerBackground,
    beginnerChipContent = SkillForgeBeginnerText,
    intermediateChipContainer = SkillForgeIntermediateBackground,
    intermediateChipContent = SkillForgeIntermediateText,
    playerBackground = SkillForgePlayerBackground,
)

val DarkSkillForgeColors = SkillForgeColors(
    freeBadgeContainer = SkillForgeDarkSecondaryContainer,
    freeBadgeContent = SkillForgeDarkSecondaryText,
    priceBadgeContainer = SkillForgeDarkAmberContainer,
    priceBadgeContent = SkillForgeDarkAmberText,
    beginnerChipContainer = SkillForgeDarkSecondaryContainer,
    beginnerChipContent = SkillForgeDarkSecondaryText,
    intermediateChipContainer = Color(0xFF1E3A8A),
    intermediateChipContent = Color(0xFFBFDBFE),
    playerBackground = SkillForgePlayerBackground,
)

val LocalSkillForgeColors = staticCompositionLocalOf { LightSkillForgeColors }

val MaterialTheme.skillForgeColors: SkillForgeColors
    @Composable
    get() = LocalSkillForgeColors.current
