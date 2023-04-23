package com.github.astat1cc.datebook.datedetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.astat1cc.datebook.R
import com.github.astat1cc.datebook.core.models.ui.UiState
import com.github.astat1cc.datebook.core.ui.colors.dateDetailsScreenBackground
import com.github.astat1cc.datebook.core.ui.colors.greenDark
import com.github.astat1cc.datebook.core.ui.colors.greenLight
import com.github.astat1cc.datebook.datedetails.presentation.views.ChameleonAnimation

@Composable
fun DateDetailsScreen(
    viewModel: DateDetailsViewModel,
    onNavigateUp: () -> Unit
) {

    val dateState = viewModel.date.collectAsState()

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dateDetailsScreenBackground)
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(greenDark)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onNavigateUp()
                }
                .padding(12.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = greenLight
        )
        when (val date = dateState.value) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = greenDark
                )
            }
            is UiState.Fail -> {}
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        // title
                        Text(
                            modifier = Modifier
                                .padding(top = 32.dp, start = 32.dp, end = 32.dp)
                                .fillMaxWidth(),
                            text = date.data.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = greenDark,
                            textAlign = TextAlign.Center
                        )
                    }
                    item {
                        // description
                        Text(
                            modifier = Modifier
                                .padding(top = 40.dp, start = 24.dp, end = 24.dp)
                                .fillMaxWidth(),
                            text = date.data.description,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = greenDark,
                            textAlign = TextAlign.Start
                        )
                    }
                    item {
                        // time
                        Text(
                            modifier = Modifier
                                .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                            text = date.data.dateStart,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = greenDark,
                            textAlign = TextAlign.Start
                        )
                    }
                    item {
                        ChameleonAnimation()
                    }
                }
            }
        }
    }
}