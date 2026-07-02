package clickretina.assasement.skillforge.view.lessonPlayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import clickretina.assasement.skillforge.ui.theme.skillForgeColors
import clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel.LessonPlayerData
import clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel.LessonPlayerLessonUiModel
import clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel.LessonPlayerUiState
import clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel.LessonPlayerViewModel

@Composable
fun LessonPlayerScreen(
    viewModel: LessonPlayerViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LessonPlayerScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onTabSelected = viewModel::onTabSelected,
        onLessonClick = viewModel::onLessonClick,
        onPlayPauseClick = viewModel::onPlayPauseClick,
        onRetry = viewModel::retry,
    )
}

@Composable
fun LessonPlayerScreen(
    uiState: LessonPlayerUiState,
    onBackClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onLessonClick: (Int) -> Unit,
    onPlayPauseClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        when (uiState) {
            LessonPlayerUiState.Loading -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center),
            )

            is LessonPlayerUiState.Error -> ErrorContent(
                message = uiState.message,
                onRetry = onRetry,
                modifier = Modifier.align(Alignment.Center),
            )

            is LessonPlayerUiState.Success -> LessonPlayerContent(
                data = uiState.data,
                onBackClick = onBackClick,
                onTabSelected = onTabSelected,
                onLessonClick = onLessonClick,
                onPlayPauseClick = onPlayPauseClick,
            )
        }
    }
}

@Composable
private fun LessonPlayerContent(
    data: LessonPlayerData,
    onBackClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onLessonClick: (Int) -> Unit,
    onPlayPauseClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        PlayerHeader(
            data = data,
            onBackClick = onBackClick,
            onPlayPauseClick = onPlayPauseClick,
        )
        LessonIdentity(data = data)
        LessonTabs(
            selectedTabIndex = data.activeTabIndex,
            onTabSelected = onTabSelected,
        )
        when (data.activeTabIndex) {
            0 -> LessonsTab(data = data, onLessonClick = onLessonClick)
            1 -> NotesTab()
            else -> ResourcesTab()
        }
    }
}

@Composable
private fun PlayerHeader(
    data: LessonPlayerData,
    onBackClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(MaterialTheme.skillForgeColors.playerBackground),
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.16f))
                .clickable(onClick = onPlayPauseClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (data.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (data.isPlaying) "Pause" else "Play",
                tint = Color.White,
                modifier = Modifier.size(36.dp),
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            LinearProgressIndicator(
                progress = { data.progressFraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(999.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White.copy(alpha = 0.24f),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = data.currentTimestampText,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = data.totalDurationText,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
private fun LessonIdentity(data: LessonPlayerData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "LESSON ${data.currentLessonNumber} : ${data.courseTitle.uppercase()}",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = data.currentLesson.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = data.currentLesson.content,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun LessonTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val tabs = listOf("Lessons", "Notes", "Resources")
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun LessonsTab(
    data: LessonPlayerData,
    onLessonClick: (Int) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        data.lessons.forEachIndexed { index, lesson ->
            LessonRow(lesson = lesson, onClick = { onLessonClick(lesson.index) })
            if (index != data.lessons.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}

@Composable
private fun LessonRow(
    lesson: LessonPlayerLessonUiModel,
    onClick: () -> Unit,
) {
    val backgroundColor = if (lesson.isCurrent) {
        MaterialTheme.skillForgeColors.freeBadgeContainer.copy(alpha = 0.42f)
    } else {
        Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${lesson.number}. ${lesson.title}",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = if (lesson.isCurrent) "Now playing � ${lesson.durationMinutes} min" else "${lesson.durationMinutes} min",
                color = if (lesson.isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        AccessBadge(isFree = lesson.isFree, isCurrent = lesson.isCurrent)
    }
}

@Composable
private fun AccessBadge(
    isFree: Boolean,
    isCurrent: Boolean,
) {
    if (isCurrent) return

    val container = if (isFree) {
        MaterialTheme.skillForgeColors.freeBadgeContainer
    } else {
        MaterialTheme.skillForgeColors.priceBadgeContainer
    }
    val content = if (isFree) {
        MaterialTheme.skillForgeColors.freeBadgeContent
    } else {
        MaterialTheme.skillForgeColors.priceBadgeContent
    }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(container)
            .padding(horizontal = 9.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (!isFree) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                tint = content,
                modifier = Modifier.size(12.dp),
            )
        }
        Text(
            text = if (isFree) "FREE" else "PRICE",
            color = content,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun NotesTab() {
    var notes by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(24.dp)) {
        TextField(
            value = notes,
            onValueChange = { notes = it },
            placeholder = { Text(text = "Your notes for this lesson...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            ),
        )
    }
}

@Composable
private fun ResourcesTab() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No resources for this lesson.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(text = "Retry", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
