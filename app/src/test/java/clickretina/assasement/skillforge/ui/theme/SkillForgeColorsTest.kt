package clickretina.assasement.skillforge.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Test

class SkillForgeColorsTest {

    @Test
    fun lightThemeExposesBadgeAndLevelChipRoles() {
        assertEquals(SkillForgeLightTeal, LightSkillForgeColors.freeBadgeContainer)
        assertEquals(SkillForgeFreeText, LightSkillForgeColors.freeBadgeContent)
        assertEquals(SkillForgeLightAmber, LightSkillForgeColors.priceBadgeContainer)
        assertEquals(SkillForgePriceText, LightSkillForgeColors.priceBadgeContent)
        assertEquals(SkillForgeBeginnerBackground, LightSkillForgeColors.beginnerChipContainer)
        assertEquals(SkillForgeBeginnerText, LightSkillForgeColors.beginnerChipContent)
        assertEquals(SkillForgeIntermediateBackground, LightSkillForgeColors.intermediateChipContainer)
        assertEquals(SkillForgeIntermediateText, LightSkillForgeColors.intermediateChipContent)
    }
}
