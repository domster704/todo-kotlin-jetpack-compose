package ru.lnkr.todo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.R
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoItemsRepository
import ru.lnkr.todo.ui.theme.AppTheme
import ru.lnkr.todo.util.Util

@Composable
fun TodoItemCard(item: TodoItem, onClick: () -> Unit) {
    Box(modifier = Modifier
        .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
        ) {
            /**
             * enabled - high importance
             * disabled - low and none importance
             *
             * check - completed
             * uncheck - not completed
             */
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { },
                enabled = item.importance == Importance.HIGH,
                colors = CheckboxColors(
                    checkedBoxColor = AppTheme.colors.colorGreen,
                    checkedBorderColor = AppTheme.colors.colorGreen,

                    uncheckedBoxColor = AppTheme.colors.colorRedTransparent,
                    uncheckedBorderColor = AppTheme.colors.colorRed,

                    disabledCheckedBoxColor = AppTheme.colors.colorGreen,
                    disabledBorderColor = AppTheme.colors.supportSeparator,

                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledUncheckedBorderColor = AppTheme.colors.supportSeparator,

                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedCheckmarkColor = AppTheme.colors.colorWhite,
                    disabledIndeterminateBoxColor = Color.Unspecified,
                    disabledIndeterminateBorderColor = Color.Unspecified
                ),
                modifier = Modifier
                    .padding(6.dp)
                    .size(12.dp)
            )

            when (item.importance) {
                Importance.HIGH -> {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "!!",
                        color = AppTheme.colors.colorRed,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }

                Importance.LOW -> {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painterResource(R.drawable.arrow_down),
                        contentDescription = "low importance",
                        modifier = Modifier
                            .width(16.dp)
                            .fillMaxHeight(),
                        tint = AppTheme.colors.colorGray
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }

                else -> {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier,
                    color = if (item.isCompleted) AppTheme.colors.labelTertiary else AppTheme.colors.labelPrimary,
                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null
                )

                if (item.deadline != null) {
                    Text(
                        text = Util.convertDateToString(item.deadline),
                        style = AppTheme.typography.subhead,
                        color = AppTheme.colors.labelTertiary,
                    )
                }
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "info",
                    tint = AppTheme.colors.labelTertiary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemCardPreview() {
    AppTheme {
        TodoItemCard(TodoItemsRepository.getTodoItems()[2]) {}
    }
}