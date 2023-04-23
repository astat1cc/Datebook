package com.github.astat1cc.datebook.datelist.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.astat1cc.datebook.R
import com.github.astat1cc.datebook.core.ui.colors.dateCreatingSheetBackground
import com.github.astat1cc.datebook.core.ui.colors.green
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.core.ui.colors.greenLight

@Composable
fun DateCreatingSheet(
    dateTitle: String?,
    dateDescription: String?,
    pickedTime: String,
    currentlyChosenDate: String,
    onTitleTextFieldValueChanged: (String) -> Unit,
    onDescriptionTextFieldValueChanged: (String) -> Unit,
    doneButtonClickListener: () -> Unit,
    onPickTimeClicked: () -> Unit
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = greenLight,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = greenDark,
        textColor = greenDark
    )
    val textFieldModifier = Modifier
        .padding(top = 12.dp)
        .fillMaxWidth()
//        .border(width = 1.dp, color = greenDark, shape = RoundedCornerShape(12.dp))
        .clip(RoundedCornerShape(12.dp))

    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(dateCreatingSheetBackground)
            .padding(horizontal = 20.dp)
    ) {
        // Create Date text
        item {
            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.create_date),
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = greenDark,
                textAlign = TextAlign.Center
            )
        }
        // Date
        item {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                text = currentlyChosenDate,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = greenDark,
                textAlign = TextAlign.Center
            )
        }
        // Date title text
        item {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.date_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = greenDark
            )
        }
        // Date title field
        item {
            TextField(
                modifier = textFieldModifier,
                value = dateTitle ?: "",
                onValueChange = { newValue ->
                    onTitleTextFieldValueChanged(newValue)
                },
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )
        }
        // Date description text
        item {
            Text(
                modifier = Modifier.padding(top = 28.dp),
                text = stringResource(R.string.date_description),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = greenDark
            )
        }
        // Date description field
        item {
            TextField(
                modifier = textFieldModifier,
                value = dateDescription ?: "",
                onValueChange = { newValue ->
                    onDescriptionTextFieldValueChanged(newValue)
                },
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                )
            )
        }
        // Time text
        item {
            Text(
                modifier = Modifier.padding(top = 28.dp),
                text = stringResource(R.string.time),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = greenDark
            )
        }
        // Time field
        item {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(greenLight)
                    .clickable {
                        onPickTimeClicked()
                    }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = pickedTime,
                    color = greenDark,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        // Done button
        item {
            Button(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    doneButtonClickListener()
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = greenDark
                ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Center
                ) {
                    Text(
                        text = stringResource(R.string.done),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}