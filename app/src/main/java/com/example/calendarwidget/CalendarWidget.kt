package com.example.calendarwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private data class CalendarCell(
    val date: LocalDate,
    val day: Int,
    val isCurrentMonth: Boolean,
)

@Composable
fun BasicCalendarWidget(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate = LocalDate.now(),
) {
    val today = LocalDate.now()
    val monthLabel = selectedDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val year = selectedDate.year

    val cells = remember(selectedDate.year, selectedDate.monthValue) {
        buildCalendarGrid(selectedDate)
    }

    val weekDays = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$monthLabel $year",
                color = Color(0xFFEAEAEA),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weekDays.forEach { dayName ->
                Text(
                    text = dayName,
                    color = Color(0xFFB0B0B0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        cells.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { cell ->
                    val isToday = cell.date == today
                    val textColor = when {
                        isToday -> Color.White
                        cell.isCurrentMonth -> Color(0xFFEAEAEA)
                        else -> Color(0xFF666666)
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (isToday) Color(0xFFE53935) else Color.Transparent
                            )
                    ) {
                        Text(
                            text = cell.day.toString(),
                            color = textColor,
                            fontSize = 16.sp,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun buildCalendarGrid(date: LocalDate): List<CalendarCell> {
    val firstDayOfMonth = LocalDate.of(date.year, date.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek
    val startOffset = firstDayOfWeek.value % 7
    val totalCells = 42
    val cells = mutableListOf<CalendarCell>()
    val firstVisibleDate = firstDayOfMonth.minusDays(startOffset.toLong())

    repeat(totalCells) { index ->
        val currentDate = firstVisibleDate.plusDays(index.toLong())
        cells += CalendarCell(
            date = currentDate,
            day = currentDate.dayOfMonth,
            isCurrentMonth = currentDate.month == date.month
        )
    }
    return cells
}
