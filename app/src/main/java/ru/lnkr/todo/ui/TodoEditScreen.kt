package ru.lnkr.todo.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoViewModel
import ru.lnkr.todo.ui.theme.AppTheme
import ru.lnkr.todo.util.Util
import java.util.Date

data class ImportanceViewData(
    val text: String,
    val color: Color,
    val labelColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    item: TodoItem?,
    onSave: (TodoItem) -> Unit,
    onClose: () -> Unit,
    onDelete: (String) -> Unit = {},
    onComplete: (String) -> Unit = {}
) {
    var itemText by remember { mutableStateOf(item?.text.orEmpty()) }
    var importance by remember { mutableStateOf(item?.importance ?: Importance.NONE) }

    var deadline by remember { mutableStateOf(item?.deadline ?: Date()) }
    var isDeadline by remember { mutableStateOf(item?.deadline != null) }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    scrolledContainerColor = AppTheme.colors.backPrimary,
                    containerColor = AppTheme.colors.backPrimary,
                    titleContentColor = AppTheme.colors.labelPrimary,
                    actionIconContentColor = AppTheme.colors.colorBlue
                ),
                title = {},
                actions = {
                    Text(
                        "СОХРАНИТЬ",
                        color = AppTheme.colors.colorBlue,
                        style = AppTheme.typography.button,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                onSave(
                                    when (item) {
                                        null -> TodoItem(
                                            text = itemText,
                                            importance = importance,
                                            deadline = if (isDeadline) deadline else null,
                                            modifiedAt = Date(),
                                        )

                                        else -> TodoItem(
                                            id = item.id,
                                            text = itemText,
                                            importance = importance,
                                            deadline = if (isDeadline) deadline else null,
                                            createdAt = item.createdAt,
                                            modifiedAt = Date(),
                                            isCompleted = item.isCompleted
                                        )
                                    }
                                )
                                onClose()
                            }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClose() }) {
                        Icon(Icons.Default.Close,
                            contentDescription = "Back",
                            tint = AppTheme.colors.labelPrimary)
                    }
                },
            )
        },
        containerColor = AppTheme.colors.backPrimary,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.colors.backSecondary,
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = itemText,
                    onValueChange = { itemText = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = AppTheme.typography.body,
                    minLines = 5,
                    placeholder = {
                        Text(
                            "Что надо сделать...",
                            style = AppTheme.typography.body,
                            color = AppTheme.colors.labelTertiary
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = AppTheme.colors.labelPrimary,
                        unfocusedTextColor = AppTheme.colors.labelPrimary,

                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ImportanceChooser(
                importance,
                expanded,
                { newImportance ->
                    importance = newImportance
                },
                { newExpanded ->
                    expanded = newExpanded
                })
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            DeadlineChooser(
                isDeadline,
                deadline,
                { newDeadline ->
                    deadline = newDeadline
                },
                { newIsDeadline ->
                    isDeadline = newIsDeadline
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    item?.let { onDelete(it.id) }
                    onClose()
                },
                contentPadding = PaddingValues(0.dp),
                enabled = item != null,
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppTheme.colors.colorRed,
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                )
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Удалить",
                    style = AppTheme.typography.body,
                )
            }
        }
    }
}

@Composable
fun ImportanceChooser(
    importance: Importance,
    expanded: Boolean,
    changeImportance: (Importance) -> Unit,
    changeExpanded: (Boolean) -> Unit,
) {
    val importanceDictionary = mapOf(
        Importance.NONE to ImportanceViewData(
            "Нет",
            AppTheme.colors.labelPrimary,
            AppTheme.colors.labelTertiary
        ),
        Importance.LOW to ImportanceViewData(
            "Низкий",
            AppTheme.colors.labelPrimary,
            AppTheme.colors.labelTertiary
        ),
        Importance.HIGH to ImportanceViewData(
            "!! Высокий",
            AppTheme.colors.colorRed,
            AppTheme.colors.colorRed
        )
    )

    Column {
        Text(
            "Важность",
            style = AppTheme.typography.body,
            color = AppTheme.colors.labelPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            importanceDictionary[importance]!!.text,
            color = importanceDictionary[importance]!!.labelColor,
            style = AppTheme.typography.button,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { changeExpanded(!expanded) }
        )
        Spacer(modifier = Modifier.height(4.dp))

        DropdownMenu(
            modifier = Modifier.background(AppTheme.colors.backElevated),
            expanded = expanded,
            onDismissRequest = { changeExpanded(false) },
        ) {
            Importance.entries.forEach { importanceOption ->
                DropdownMenuItem(
                    text = {
                        Text(importanceDictionary[importanceOption]!!.text)
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = importanceDictionary[importanceOption]!!.color,
                    ),
                    onClick = {
                        changeImportance(importanceOption)
                        changeExpanded(false)
                    },
                )
            }
        }
    }
}

@Composable
fun DeadlineChooser(
    isDeadline: Boolean,
    deadline: Date,
    deadlineOnChange: (Date) -> Unit,
    isDeadlineChanged: (Boolean) -> Unit,
) {
    var isShownDatePicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "Сделать до",
                style = AppTheme.typography.body,
                color = AppTheme.colors.labelPrimary
            )
            if (isDeadline) {
                Text(
                    Util.convertDateToString(deadline),
                    color = AppTheme.colors.colorBlue,
                    style = AppTheme.typography.button,
                    modifier = Modifier.clickable {
                        isShownDatePicker = true
                    }
                )
            }
        }

        Switch(
            checked = isDeadline,
            onCheckedChange = { isDeadlineChanged(!isDeadline) },
            colors = SwitchDefaults.colors(
                checkedBorderColor = Color.Transparent,
                checkedThumbColor = AppTheme.colors.colorBlue,
                checkedTrackColor = AppTheme.colors.colorBlue.copy(alpha = 0.3f),

                uncheckedThumbColor = AppTheme.colors.backElevated,
                uncheckedBorderColor = Color.Transparent,
                uncheckedTrackColor = AppTheme.colors.supportOverlay
            )
        )

        if (isShownDatePicker) {
            DatePickerModal(deadline.time, { selectedDateMillis ->
                deadlineOnChange(Date(selectedDateMillis))
            }, {
                isShownDatePicker = false
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    dateMillis: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (datePickerState.selectedDateMillis == null) {
                    return@TextButton
                }

                onDateSelected(datePickerState.selectedDateMillis!!)
                onDismiss()
            }) {
                Text(
                    "ОК",
                    color = AppTheme.colors.colorBlue,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Отмена",
                    color = AppTheme.colors.colorBlue,
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = AppTheme.colors.backSecondary,
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = AppTheme.colors.backSecondary,
                titleContentColor = AppTheme.colors.colorBlue,
                subheadContentColor = AppTheme.colors.colorBlue,
                headlineContentColor = AppTheme.colors.colorBlue,
                navigationContentColor = AppTheme.colors.colorBlue,
                dayContentColor = AppTheme.colors.labelPrimary,
                weekdayContentColor = AppTheme.colors.labelTertiary,

                todayContentColor = AppTheme.colors.colorBlue,
                todayDateBorderColor = AppTheme.colors.colorBlue,
                currentYearContentColor = AppTheme.colors.colorBlue,

                selectedDayContentColor = AppTheme.colors.colorWhite,
                selectedDayContainerColor = AppTheme.colors.colorBlue,

                selectedYearContentColor = AppTheme.colors.colorWhite,
                selectedYearContainerColor = AppTheme.colors.colorBlue,
            )
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TodoEditScreenPreview() {
    val fakeViewModel = TodoViewModel()
    val items = fakeViewModel.items.collectAsState()
    AppTheme {
        TodoEditScreen(items.value[3], {}, {})
    }
}
