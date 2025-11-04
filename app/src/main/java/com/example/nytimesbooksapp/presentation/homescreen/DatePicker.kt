package com.example.nytimesbooksapp.presentation.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nytimesbooksapp.presentation.viewmodel.Bookviewmodel
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFilterSection(
    viewModel: Bookviewmodel
) {
    val selectedDate by viewModel.selecteddate.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val displayFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    val displayDate = remember(selectedDate) {
        if (selectedDate.isNotEmpty()) {
            try {
                LocalDate.parse(selectedDate, isoFormatter).format(displayFormatter)
            } catch (e: Exception) {
                selectedDate
            }
        } else ""
    }
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp)) {
        TextButton(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (displayDate.isNotEmpty())
                    "Selected: $displayDate"
                else
                    "Select Published Date",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                viewModel.dateselected(date.format(isoFormatter))
                                viewModel.searchbooks("")
                            }
                            showDialog = false
                        }
                    ) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
